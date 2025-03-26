package com.farmer.be.web.rest;

import static com.farmer.be.domain.FieldVisitAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.FieldVisit;
import com.farmer.be.repository.FieldVisitRepository;
import com.farmer.be.service.dto.FieldVisitDTO;
import com.farmer.be.service.mapper.FieldVisitMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FieldVisitResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FieldVisitResourceIT {

    private static final LocalDate DEFAULT_FIELD_VISIT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FIELD_VISIT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_FIELD_VISIT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIELD_VISIT_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_LAT = 1F;
    private static final Float UPDATED_LAT = 2F;

    private static final Float DEFAULT_LON = 1F;
    private static final Float UPDATED_LON = 2F;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_CREATEDD_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATEDD_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/field-visits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FieldVisitRepository fieldVisitRepository;

    @Autowired
    private FieldVisitMapper fieldVisitMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFieldVisitMockMvc;

    private FieldVisit fieldVisit;

    private FieldVisit insertedFieldVisit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldVisit createEntity() {
        return new FieldVisit()
            .fieldVisitDate(DEFAULT_FIELD_VISIT_DATE)
            .fieldVisitTime(DEFAULT_FIELD_VISIT_TIME)
            .lat(DEFAULT_LAT)
            .lon(DEFAULT_LON)
            .isActive(DEFAULT_IS_ACTIVE)
            .createddBy(DEFAULT_CREATEDD_BY)
            .createdTime(DEFAULT_CREATED_TIME)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedTime(DEFAULT_UPDATED_TIME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FieldVisit createUpdatedEntity() {
        return new FieldVisit()
            .fieldVisitDate(UPDATED_FIELD_VISIT_DATE)
            .fieldVisitTime(UPDATED_FIELD_VISIT_TIME)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        fieldVisit = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedFieldVisit != null) {
            fieldVisitRepository.delete(insertedFieldVisit);
            insertedFieldVisit = null;
        }
    }

    @Test
    @Transactional
    void createFieldVisit() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FieldVisit
        FieldVisitDTO fieldVisitDTO = fieldVisitMapper.toDto(fieldVisit);
        var returnedFieldVisitDTO = om.readValue(
            restFieldVisitMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldVisitDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FieldVisitDTO.class
        );

        // Validate the FieldVisit in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFieldVisit = fieldVisitMapper.toEntity(returnedFieldVisitDTO);
        assertFieldVisitUpdatableFieldsEquals(returnedFieldVisit, getPersistedFieldVisit(returnedFieldVisit));

        insertedFieldVisit = returnedFieldVisit;
    }

    @Test
    @Transactional
    void createFieldVisitWithExistingId() throws Exception {
        // Create the FieldVisit with an existing ID
        fieldVisit.setId(1L);
        FieldVisitDTO fieldVisitDTO = fieldVisitMapper.toDto(fieldVisit);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldVisitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldVisitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FieldVisit in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        fieldVisit.setCreateddBy(null);

        // Create the FieldVisit, which fails.
        FieldVisitDTO fieldVisitDTO = fieldVisitMapper.toDto(fieldVisit);

        restFieldVisitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldVisitDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        fieldVisit.setCreatedTime(null);

        // Create the FieldVisit, which fails.
        FieldVisitDTO fieldVisitDTO = fieldVisitMapper.toDto(fieldVisit);

        restFieldVisitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldVisitDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        fieldVisit.setUpdatedBy(null);

        // Create the FieldVisit, which fails.
        FieldVisitDTO fieldVisitDTO = fieldVisitMapper.toDto(fieldVisit);

        restFieldVisitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldVisitDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        fieldVisit.setUpdatedTime(null);

        // Create the FieldVisit, which fails.
        FieldVisitDTO fieldVisitDTO = fieldVisitMapper.toDto(fieldVisit);

        restFieldVisitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldVisitDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFieldVisits() throws Exception {
        // Initialize the database
        insertedFieldVisit = fieldVisitRepository.saveAndFlush(fieldVisit);

        // Get all the fieldVisitList
        restFieldVisitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fieldVisit.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldVisitDate").value(hasItem(DEFAULT_FIELD_VISIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].fieldVisitTime").value(hasItem(DEFAULT_FIELD_VISIT_TIME.toString())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getFieldVisit() throws Exception {
        // Initialize the database
        insertedFieldVisit = fieldVisitRepository.saveAndFlush(fieldVisit);

        // Get the fieldVisit
        restFieldVisitMockMvc
            .perform(get(ENTITY_API_URL_ID, fieldVisit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fieldVisit.getId().intValue()))
            .andExpect(jsonPath("$.fieldVisitDate").value(DEFAULT_FIELD_VISIT_DATE.toString()))
            .andExpect(jsonPath("$.fieldVisitTime").value(DEFAULT_FIELD_VISIT_TIME.toString()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.lon").value(DEFAULT_LON.doubleValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFieldVisit() throws Exception {
        // Get the fieldVisit
        restFieldVisitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFieldVisit() throws Exception {
        // Initialize the database
        insertedFieldVisit = fieldVisitRepository.saveAndFlush(fieldVisit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fieldVisit
        FieldVisit updatedFieldVisit = fieldVisitRepository.findById(fieldVisit.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFieldVisit are not directly saved in db
        em.detach(updatedFieldVisit);
        updatedFieldVisit
            .fieldVisitDate(UPDATED_FIELD_VISIT_DATE)
            .fieldVisitTime(UPDATED_FIELD_VISIT_TIME)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        FieldVisitDTO fieldVisitDTO = fieldVisitMapper.toDto(updatedFieldVisit);

        restFieldVisitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldVisitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fieldVisitDTO))
            )
            .andExpect(status().isOk());

        // Validate the FieldVisit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFieldVisitToMatchAllProperties(updatedFieldVisit);
    }

    @Test
    @Transactional
    void putNonExistingFieldVisit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fieldVisit.setId(longCount.incrementAndGet());

        // Create the FieldVisit
        FieldVisitDTO fieldVisitDTO = fieldVisitMapper.toDto(fieldVisit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldVisitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldVisitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fieldVisitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldVisit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFieldVisit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fieldVisit.setId(longCount.incrementAndGet());

        // Create the FieldVisit
        FieldVisitDTO fieldVisitDTO = fieldVisitMapper.toDto(fieldVisit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldVisitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fieldVisitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldVisit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFieldVisit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fieldVisit.setId(longCount.incrementAndGet());

        // Create the FieldVisit
        FieldVisitDTO fieldVisitDTO = fieldVisitMapper.toDto(fieldVisit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldVisitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fieldVisitDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldVisit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFieldVisitWithPatch() throws Exception {
        // Initialize the database
        insertedFieldVisit = fieldVisitRepository.saveAndFlush(fieldVisit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fieldVisit using partial update
        FieldVisit partialUpdatedFieldVisit = new FieldVisit();
        partialUpdatedFieldVisit.setId(fieldVisit.getId());

        partialUpdatedFieldVisit
            .fieldVisitTime(UPDATED_FIELD_VISIT_TIME)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .isActive(UPDATED_IS_ACTIVE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFieldVisitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldVisit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFieldVisit))
            )
            .andExpect(status().isOk());

        // Validate the FieldVisit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFieldVisitUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFieldVisit, fieldVisit),
            getPersistedFieldVisit(fieldVisit)
        );
    }

    @Test
    @Transactional
    void fullUpdateFieldVisitWithPatch() throws Exception {
        // Initialize the database
        insertedFieldVisit = fieldVisitRepository.saveAndFlush(fieldVisit);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fieldVisit using partial update
        FieldVisit partialUpdatedFieldVisit = new FieldVisit();
        partialUpdatedFieldVisit.setId(fieldVisit.getId());

        partialUpdatedFieldVisit
            .fieldVisitDate(UPDATED_FIELD_VISIT_DATE)
            .fieldVisitTime(UPDATED_FIELD_VISIT_TIME)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFieldVisitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFieldVisit.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFieldVisit))
            )
            .andExpect(status().isOk());

        // Validate the FieldVisit in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFieldVisitUpdatableFieldsEquals(partialUpdatedFieldVisit, getPersistedFieldVisit(partialUpdatedFieldVisit));
    }

    @Test
    @Transactional
    void patchNonExistingFieldVisit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fieldVisit.setId(longCount.incrementAndGet());

        // Create the FieldVisit
        FieldVisitDTO fieldVisitDTO = fieldVisitMapper.toDto(fieldVisit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldVisitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fieldVisitDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fieldVisitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldVisit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFieldVisit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fieldVisit.setId(longCount.incrementAndGet());

        // Create the FieldVisit
        FieldVisitDTO fieldVisitDTO = fieldVisitMapper.toDto(fieldVisit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldVisitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fieldVisitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FieldVisit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFieldVisit() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fieldVisit.setId(longCount.incrementAndGet());

        // Create the FieldVisit
        FieldVisitDTO fieldVisitDTO = fieldVisitMapper.toDto(fieldVisit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldVisitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(fieldVisitDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FieldVisit in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFieldVisit() throws Exception {
        // Initialize the database
        insertedFieldVisit = fieldVisitRepository.saveAndFlush(fieldVisit);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the fieldVisit
        restFieldVisitMockMvc
            .perform(delete(ENTITY_API_URL_ID, fieldVisit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return fieldVisitRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected FieldVisit getPersistedFieldVisit(FieldVisit fieldVisit) {
        return fieldVisitRepository.findById(fieldVisit.getId()).orElseThrow();
    }

    protected void assertPersistedFieldVisitToMatchAllProperties(FieldVisit expectedFieldVisit) {
        assertFieldVisitAllPropertiesEquals(expectedFieldVisit, getPersistedFieldVisit(expectedFieldVisit));
    }

    protected void assertPersistedFieldVisitToMatchUpdatableProperties(FieldVisit expectedFieldVisit) {
        assertFieldVisitAllUpdatablePropertiesEquals(expectedFieldVisit, getPersistedFieldVisit(expectedFieldVisit));
    }
}
