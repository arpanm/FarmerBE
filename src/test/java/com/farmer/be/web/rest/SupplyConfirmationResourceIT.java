package com.farmer.be.web.rest;

import static com.farmer.be.domain.SupplyConfirmationAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.SupplyConfirmation;
import com.farmer.be.repository.SupplyConfirmationRepository;
import com.farmer.be.service.dto.SupplyConfirmationDTO;
import com.farmer.be.service.mapper.SupplyConfirmationMapper;
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
 * Integration tests for the {@link SupplyConfirmationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SupplyConfirmationResourceIT {

    private static final LocalDate DEFAULT_CONFIRM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CONFIRM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_CONFIRM_VALUE = 1F;
    private static final Float UPDATED_CONFIRM_VALUE = 2F;

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

    private static final String ENTITY_API_URL = "/api/supply-confirmations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SupplyConfirmationRepository supplyConfirmationRepository;

    @Autowired
    private SupplyConfirmationMapper supplyConfirmationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSupplyConfirmationMockMvc;

    private SupplyConfirmation supplyConfirmation;

    private SupplyConfirmation insertedSupplyConfirmation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupplyConfirmation createEntity() {
        return new SupplyConfirmation()
            .confirmDate(DEFAULT_CONFIRM_DATE)
            .confirmValue(DEFAULT_CONFIRM_VALUE)
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
    public static SupplyConfirmation createUpdatedEntity() {
        return new SupplyConfirmation()
            .confirmDate(UPDATED_CONFIRM_DATE)
            .confirmValue(UPDATED_CONFIRM_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        supplyConfirmation = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedSupplyConfirmation != null) {
            supplyConfirmationRepository.delete(insertedSupplyConfirmation);
            insertedSupplyConfirmation = null;
        }
    }

    @Test
    @Transactional
    void createSupplyConfirmation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SupplyConfirmation
        SupplyConfirmationDTO supplyConfirmationDTO = supplyConfirmationMapper.toDto(supplyConfirmation);
        var returnedSupplyConfirmationDTO = om.readValue(
            restSupplyConfirmationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supplyConfirmationDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SupplyConfirmationDTO.class
        );

        // Validate the SupplyConfirmation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSupplyConfirmation = supplyConfirmationMapper.toEntity(returnedSupplyConfirmationDTO);
        assertSupplyConfirmationUpdatableFieldsEquals(
            returnedSupplyConfirmation,
            getPersistedSupplyConfirmation(returnedSupplyConfirmation)
        );

        insertedSupplyConfirmation = returnedSupplyConfirmation;
    }

    @Test
    @Transactional
    void createSupplyConfirmationWithExistingId() throws Exception {
        // Create the SupplyConfirmation with an existing ID
        supplyConfirmation.setId(1L);
        SupplyConfirmationDTO supplyConfirmationDTO = supplyConfirmationMapper.toDto(supplyConfirmation);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplyConfirmationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supplyConfirmationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SupplyConfirmation in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        supplyConfirmation.setCreateddBy(null);

        // Create the SupplyConfirmation, which fails.
        SupplyConfirmationDTO supplyConfirmationDTO = supplyConfirmationMapper.toDto(supplyConfirmation);

        restSupplyConfirmationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supplyConfirmationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        supplyConfirmation.setCreatedTime(null);

        // Create the SupplyConfirmation, which fails.
        SupplyConfirmationDTO supplyConfirmationDTO = supplyConfirmationMapper.toDto(supplyConfirmation);

        restSupplyConfirmationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supplyConfirmationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        supplyConfirmation.setUpdatedBy(null);

        // Create the SupplyConfirmation, which fails.
        SupplyConfirmationDTO supplyConfirmationDTO = supplyConfirmationMapper.toDto(supplyConfirmation);

        restSupplyConfirmationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supplyConfirmationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        supplyConfirmation.setUpdatedTime(null);

        // Create the SupplyConfirmation, which fails.
        SupplyConfirmationDTO supplyConfirmationDTO = supplyConfirmationMapper.toDto(supplyConfirmation);

        restSupplyConfirmationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supplyConfirmationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSupplyConfirmations() throws Exception {
        // Initialize the database
        insertedSupplyConfirmation = supplyConfirmationRepository.saveAndFlush(supplyConfirmation);

        // Get all the supplyConfirmationList
        restSupplyConfirmationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplyConfirmation.getId().intValue())))
            .andExpect(jsonPath("$.[*].confirmDate").value(hasItem(DEFAULT_CONFIRM_DATE.toString())))
            .andExpect(jsonPath("$.[*].confirmValue").value(hasItem(DEFAULT_CONFIRM_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getSupplyConfirmation() throws Exception {
        // Initialize the database
        insertedSupplyConfirmation = supplyConfirmationRepository.saveAndFlush(supplyConfirmation);

        // Get the supplyConfirmation
        restSupplyConfirmationMockMvc
            .perform(get(ENTITY_API_URL_ID, supplyConfirmation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supplyConfirmation.getId().intValue()))
            .andExpect(jsonPath("$.confirmDate").value(DEFAULT_CONFIRM_DATE.toString()))
            .andExpect(jsonPath("$.confirmValue").value(DEFAULT_CONFIRM_VALUE.doubleValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSupplyConfirmation() throws Exception {
        // Get the supplyConfirmation
        restSupplyConfirmationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSupplyConfirmation() throws Exception {
        // Initialize the database
        insertedSupplyConfirmation = supplyConfirmationRepository.saveAndFlush(supplyConfirmation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the supplyConfirmation
        SupplyConfirmation updatedSupplyConfirmation = supplyConfirmationRepository.findById(supplyConfirmation.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSupplyConfirmation are not directly saved in db
        em.detach(updatedSupplyConfirmation);
        updatedSupplyConfirmation
            .confirmDate(UPDATED_CONFIRM_DATE)
            .confirmValue(UPDATED_CONFIRM_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        SupplyConfirmationDTO supplyConfirmationDTO = supplyConfirmationMapper.toDto(updatedSupplyConfirmation);

        restSupplyConfirmationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, supplyConfirmationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(supplyConfirmationDTO))
            )
            .andExpect(status().isOk());

        // Validate the SupplyConfirmation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSupplyConfirmationToMatchAllProperties(updatedSupplyConfirmation);
    }

    @Test
    @Transactional
    void putNonExistingSupplyConfirmation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        supplyConfirmation.setId(longCount.incrementAndGet());

        // Create the SupplyConfirmation
        SupplyConfirmationDTO supplyConfirmationDTO = supplyConfirmationMapper.toDto(supplyConfirmation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplyConfirmationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, supplyConfirmationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(supplyConfirmationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupplyConfirmation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSupplyConfirmation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        supplyConfirmation.setId(longCount.incrementAndGet());

        // Create the SupplyConfirmation
        SupplyConfirmationDTO supplyConfirmationDTO = supplyConfirmationMapper.toDto(supplyConfirmation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplyConfirmationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(supplyConfirmationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupplyConfirmation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSupplyConfirmation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        supplyConfirmation.setId(longCount.incrementAndGet());

        // Create the SupplyConfirmation
        SupplyConfirmationDTO supplyConfirmationDTO = supplyConfirmationMapper.toDto(supplyConfirmation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplyConfirmationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(supplyConfirmationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SupplyConfirmation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSupplyConfirmationWithPatch() throws Exception {
        // Initialize the database
        insertedSupplyConfirmation = supplyConfirmationRepository.saveAndFlush(supplyConfirmation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the supplyConfirmation using partial update
        SupplyConfirmation partialUpdatedSupplyConfirmation = new SupplyConfirmation();
        partialUpdatedSupplyConfirmation.setId(supplyConfirmation.getId());

        partialUpdatedSupplyConfirmation.confirmDate(UPDATED_CONFIRM_DATE).isActive(UPDATED_IS_ACTIVE).updatedBy(UPDATED_UPDATED_BY);

        restSupplyConfirmationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSupplyConfirmation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSupplyConfirmation))
            )
            .andExpect(status().isOk());

        // Validate the SupplyConfirmation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSupplyConfirmationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSupplyConfirmation, supplyConfirmation),
            getPersistedSupplyConfirmation(supplyConfirmation)
        );
    }

    @Test
    @Transactional
    void fullUpdateSupplyConfirmationWithPatch() throws Exception {
        // Initialize the database
        insertedSupplyConfirmation = supplyConfirmationRepository.saveAndFlush(supplyConfirmation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the supplyConfirmation using partial update
        SupplyConfirmation partialUpdatedSupplyConfirmation = new SupplyConfirmation();
        partialUpdatedSupplyConfirmation.setId(supplyConfirmation.getId());

        partialUpdatedSupplyConfirmation
            .confirmDate(UPDATED_CONFIRM_DATE)
            .confirmValue(UPDATED_CONFIRM_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restSupplyConfirmationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSupplyConfirmation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSupplyConfirmation))
            )
            .andExpect(status().isOk());

        // Validate the SupplyConfirmation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSupplyConfirmationUpdatableFieldsEquals(
            partialUpdatedSupplyConfirmation,
            getPersistedSupplyConfirmation(partialUpdatedSupplyConfirmation)
        );
    }

    @Test
    @Transactional
    void patchNonExistingSupplyConfirmation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        supplyConfirmation.setId(longCount.incrementAndGet());

        // Create the SupplyConfirmation
        SupplyConfirmationDTO supplyConfirmationDTO = supplyConfirmationMapper.toDto(supplyConfirmation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplyConfirmationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, supplyConfirmationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(supplyConfirmationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupplyConfirmation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSupplyConfirmation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        supplyConfirmation.setId(longCount.incrementAndGet());

        // Create the SupplyConfirmation
        SupplyConfirmationDTO supplyConfirmationDTO = supplyConfirmationMapper.toDto(supplyConfirmation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplyConfirmationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(supplyConfirmationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupplyConfirmation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSupplyConfirmation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        supplyConfirmation.setId(longCount.incrementAndGet());

        // Create the SupplyConfirmation
        SupplyConfirmationDTO supplyConfirmationDTO = supplyConfirmationMapper.toDto(supplyConfirmation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplyConfirmationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(supplyConfirmationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SupplyConfirmation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSupplyConfirmation() throws Exception {
        // Initialize the database
        insertedSupplyConfirmation = supplyConfirmationRepository.saveAndFlush(supplyConfirmation);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the supplyConfirmation
        restSupplyConfirmationMockMvc
            .perform(delete(ENTITY_API_URL_ID, supplyConfirmation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return supplyConfirmationRepository.count();
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

    protected SupplyConfirmation getPersistedSupplyConfirmation(SupplyConfirmation supplyConfirmation) {
        return supplyConfirmationRepository.findById(supplyConfirmation.getId()).orElseThrow();
    }

    protected void assertPersistedSupplyConfirmationToMatchAllProperties(SupplyConfirmation expectedSupplyConfirmation) {
        assertSupplyConfirmationAllPropertiesEquals(expectedSupplyConfirmation, getPersistedSupplyConfirmation(expectedSupplyConfirmation));
    }

    protected void assertPersistedSupplyConfirmationToMatchUpdatableProperties(SupplyConfirmation expectedSupplyConfirmation) {
        assertSupplyConfirmationAllUpdatablePropertiesEquals(
            expectedSupplyConfirmation,
            getPersistedSupplyConfirmation(expectedSupplyConfirmation)
        );
    }
}
