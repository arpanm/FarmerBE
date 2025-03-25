package com.farmer.be.web.rest;

import static com.farmer.be.domain.PickupGradationAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.PickupGradation;
import com.farmer.be.domain.enumeration.ItemGrade;
import com.farmer.be.repository.PickupGradationRepository;
import com.farmer.be.service.dto.PickupGradationDTO;
import com.farmer.be.service.mapper.PickupGradationMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
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
 * Integration tests for the {@link PickupGradationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PickupGradationResourceIT {

    private static final ItemGrade DEFAULT_ITEM_GRADE = ItemGrade.GoodQuality;
    private static final ItemGrade UPDATED_ITEM_GRADE = ItemGrade.ModerateQuality;

    private static final String DEFAULT_GRADED_BY = "AAAAAAAAAA";
    private static final String UPDATED_GRADED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_GRADED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_GRADED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/pickup-gradations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PickupGradationRepository pickupGradationRepository;

    @Autowired
    private PickupGradationMapper pickupGradationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPickupGradationMockMvc;

    private PickupGradation pickupGradation;

    private PickupGradation insertedPickupGradation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PickupGradation createEntity() {
        return new PickupGradation()
            .itemGrade(DEFAULT_ITEM_GRADE)
            .gradedBy(DEFAULT_GRADED_BY)
            .gradedTime(DEFAULT_GRADED_TIME)
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
    public static PickupGradation createUpdatedEntity() {
        return new PickupGradation()
            .itemGrade(UPDATED_ITEM_GRADE)
            .gradedBy(UPDATED_GRADED_BY)
            .gradedTime(UPDATED_GRADED_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        pickupGradation = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedPickupGradation != null) {
            pickupGradationRepository.delete(insertedPickupGradation);
            insertedPickupGradation = null;
        }
    }

    @Test
    @Transactional
    void createPickupGradation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PickupGradation
        PickupGradationDTO pickupGradationDTO = pickupGradationMapper.toDto(pickupGradation);
        var returnedPickupGradationDTO = om.readValue(
            restPickupGradationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pickupGradationDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PickupGradationDTO.class
        );

        // Validate the PickupGradation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPickupGradation = pickupGradationMapper.toEntity(returnedPickupGradationDTO);
        assertPickupGradationUpdatableFieldsEquals(returnedPickupGradation, getPersistedPickupGradation(returnedPickupGradation));

        insertedPickupGradation = returnedPickupGradation;
    }

    @Test
    @Transactional
    void createPickupGradationWithExistingId() throws Exception {
        // Create the PickupGradation with an existing ID
        pickupGradation.setId(1L);
        PickupGradationDTO pickupGradationDTO = pickupGradationMapper.toDto(pickupGradation);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPickupGradationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pickupGradationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PickupGradation in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pickupGradation.setCreateddBy(null);

        // Create the PickupGradation, which fails.
        PickupGradationDTO pickupGradationDTO = pickupGradationMapper.toDto(pickupGradation);

        restPickupGradationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pickupGradationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pickupGradation.setCreatedTime(null);

        // Create the PickupGradation, which fails.
        PickupGradationDTO pickupGradationDTO = pickupGradationMapper.toDto(pickupGradation);

        restPickupGradationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pickupGradationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pickupGradation.setUpdatedBy(null);

        // Create the PickupGradation, which fails.
        PickupGradationDTO pickupGradationDTO = pickupGradationMapper.toDto(pickupGradation);

        restPickupGradationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pickupGradationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pickupGradation.setUpdatedTime(null);

        // Create the PickupGradation, which fails.
        PickupGradationDTO pickupGradationDTO = pickupGradationMapper.toDto(pickupGradation);

        restPickupGradationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pickupGradationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPickupGradations() throws Exception {
        // Initialize the database
        insertedPickupGradation = pickupGradationRepository.saveAndFlush(pickupGradation);

        // Get all the pickupGradationList
        restPickupGradationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pickupGradation.getId().intValue())))
            .andExpect(jsonPath("$.[*].itemGrade").value(hasItem(DEFAULT_ITEM_GRADE.toString())))
            .andExpect(jsonPath("$.[*].gradedBy").value(hasItem(DEFAULT_GRADED_BY)))
            .andExpect(jsonPath("$.[*].gradedTime").value(hasItem(DEFAULT_GRADED_TIME.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getPickupGradation() throws Exception {
        // Initialize the database
        insertedPickupGradation = pickupGradationRepository.saveAndFlush(pickupGradation);

        // Get the pickupGradation
        restPickupGradationMockMvc
            .perform(get(ENTITY_API_URL_ID, pickupGradation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pickupGradation.getId().intValue()))
            .andExpect(jsonPath("$.itemGrade").value(DEFAULT_ITEM_GRADE.toString()))
            .andExpect(jsonPath("$.gradedBy").value(DEFAULT_GRADED_BY))
            .andExpect(jsonPath("$.gradedTime").value(DEFAULT_GRADED_TIME.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPickupGradation() throws Exception {
        // Get the pickupGradation
        restPickupGradationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPickupGradation() throws Exception {
        // Initialize the database
        insertedPickupGradation = pickupGradationRepository.saveAndFlush(pickupGradation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pickupGradation
        PickupGradation updatedPickupGradation = pickupGradationRepository.findById(pickupGradation.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPickupGradation are not directly saved in db
        em.detach(updatedPickupGradation);
        updatedPickupGradation
            .itemGrade(UPDATED_ITEM_GRADE)
            .gradedBy(UPDATED_GRADED_BY)
            .gradedTime(UPDATED_GRADED_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        PickupGradationDTO pickupGradationDTO = pickupGradationMapper.toDto(updatedPickupGradation);

        restPickupGradationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pickupGradationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pickupGradationDTO))
            )
            .andExpect(status().isOk());

        // Validate the PickupGradation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPickupGradationToMatchAllProperties(updatedPickupGradation);
    }

    @Test
    @Transactional
    void putNonExistingPickupGradation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pickupGradation.setId(longCount.incrementAndGet());

        // Create the PickupGradation
        PickupGradationDTO pickupGradationDTO = pickupGradationMapper.toDto(pickupGradation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPickupGradationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pickupGradationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pickupGradationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PickupGradation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPickupGradation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pickupGradation.setId(longCount.incrementAndGet());

        // Create the PickupGradation
        PickupGradationDTO pickupGradationDTO = pickupGradationMapper.toDto(pickupGradation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPickupGradationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pickupGradationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PickupGradation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPickupGradation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pickupGradation.setId(longCount.incrementAndGet());

        // Create the PickupGradation
        PickupGradationDTO pickupGradationDTO = pickupGradationMapper.toDto(pickupGradation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPickupGradationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pickupGradationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PickupGradation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePickupGradationWithPatch() throws Exception {
        // Initialize the database
        insertedPickupGradation = pickupGradationRepository.saveAndFlush(pickupGradation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pickupGradation using partial update
        PickupGradation partialUpdatedPickupGradation = new PickupGradation();
        partialUpdatedPickupGradation.setId(pickupGradation.getId());

        partialUpdatedPickupGradation.itemGrade(UPDATED_ITEM_GRADE);

        restPickupGradationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPickupGradation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPickupGradation))
            )
            .andExpect(status().isOk());

        // Validate the PickupGradation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPickupGradationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPickupGradation, pickupGradation),
            getPersistedPickupGradation(pickupGradation)
        );
    }

    @Test
    @Transactional
    void fullUpdatePickupGradationWithPatch() throws Exception {
        // Initialize the database
        insertedPickupGradation = pickupGradationRepository.saveAndFlush(pickupGradation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pickupGradation using partial update
        PickupGradation partialUpdatedPickupGradation = new PickupGradation();
        partialUpdatedPickupGradation.setId(pickupGradation.getId());

        partialUpdatedPickupGradation
            .itemGrade(UPDATED_ITEM_GRADE)
            .gradedBy(UPDATED_GRADED_BY)
            .gradedTime(UPDATED_GRADED_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restPickupGradationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPickupGradation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPickupGradation))
            )
            .andExpect(status().isOk());

        // Validate the PickupGradation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPickupGradationUpdatableFieldsEquals(
            partialUpdatedPickupGradation,
            getPersistedPickupGradation(partialUpdatedPickupGradation)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPickupGradation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pickupGradation.setId(longCount.incrementAndGet());

        // Create the PickupGradation
        PickupGradationDTO pickupGradationDTO = pickupGradationMapper.toDto(pickupGradation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPickupGradationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pickupGradationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pickupGradationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PickupGradation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPickupGradation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pickupGradation.setId(longCount.incrementAndGet());

        // Create the PickupGradation
        PickupGradationDTO pickupGradationDTO = pickupGradationMapper.toDto(pickupGradation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPickupGradationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pickupGradationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PickupGradation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPickupGradation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pickupGradation.setId(longCount.incrementAndGet());

        // Create the PickupGradation
        PickupGradationDTO pickupGradationDTO = pickupGradationMapper.toDto(pickupGradation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPickupGradationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pickupGradationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PickupGradation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePickupGradation() throws Exception {
        // Initialize the database
        insertedPickupGradation = pickupGradationRepository.saveAndFlush(pickupGradation);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pickupGradation
        restPickupGradationMockMvc
            .perform(delete(ENTITY_API_URL_ID, pickupGradation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pickupGradationRepository.count();
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

    protected PickupGradation getPersistedPickupGradation(PickupGradation pickupGradation) {
        return pickupGradationRepository.findById(pickupGradation.getId()).orElseThrow();
    }

    protected void assertPersistedPickupGradationToMatchAllProperties(PickupGradation expectedPickupGradation) {
        assertPickupGradationAllPropertiesEquals(expectedPickupGradation, getPersistedPickupGradation(expectedPickupGradation));
    }

    protected void assertPersistedPickupGradationToMatchUpdatableProperties(PickupGradation expectedPickupGradation) {
        assertPickupGradationAllUpdatablePropertiesEquals(expectedPickupGradation, getPersistedPickupGradation(expectedPickupGradation));
    }
}
