package com.farmer.be.web.rest;

import static com.farmer.be.domain.PickUpConfirmationAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.PickUpConfirmation;
import com.farmer.be.repository.PickUpConfirmationRepository;
import com.farmer.be.service.dto.PickUpConfirmationDTO;
import com.farmer.be.service.mapper.PickUpConfirmationMapper;
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
 * Integration tests for the {@link PickUpConfirmationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PickUpConfirmationResourceIT {

    private static final LocalDate DEFAULT_CONFIRM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CONFIRM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_CONFIRM_VALUE = 1F;
    private static final Float UPDATED_CONFIRM_VALUE = 2F;

    private static final String DEFAULT_PICKUP_BY = "AAAAAAAAAA";
    private static final String UPDATED_PICKUP_BY = "BBBBBBBBBB";

    private static final String DEFAULT_PICKUP_TIME = "AAAAAAAAAA";
    private static final String UPDATED_PICKUP_TIME = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/pick-up-confirmations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PickUpConfirmationRepository pickUpConfirmationRepository;

    @Autowired
    private PickUpConfirmationMapper pickUpConfirmationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPickUpConfirmationMockMvc;

    private PickUpConfirmation pickUpConfirmation;

    private PickUpConfirmation insertedPickUpConfirmation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PickUpConfirmation createEntity() {
        return new PickUpConfirmation()
            .confirmDate(DEFAULT_CONFIRM_DATE)
            .confirmValue(DEFAULT_CONFIRM_VALUE)
            .pickupBy(DEFAULT_PICKUP_BY)
            .pickupTime(DEFAULT_PICKUP_TIME)
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
    public static PickUpConfirmation createUpdatedEntity() {
        return new PickUpConfirmation()
            .confirmDate(UPDATED_CONFIRM_DATE)
            .confirmValue(UPDATED_CONFIRM_VALUE)
            .pickupBy(UPDATED_PICKUP_BY)
            .pickupTime(UPDATED_PICKUP_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        pickUpConfirmation = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedPickUpConfirmation != null) {
            pickUpConfirmationRepository.delete(insertedPickUpConfirmation);
            insertedPickUpConfirmation = null;
        }
    }

    @Test
    @Transactional
    void createPickUpConfirmation() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PickUpConfirmation
        PickUpConfirmationDTO pickUpConfirmationDTO = pickUpConfirmationMapper.toDto(pickUpConfirmation);
        var returnedPickUpConfirmationDTO = om.readValue(
            restPickUpConfirmationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pickUpConfirmationDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PickUpConfirmationDTO.class
        );

        // Validate the PickUpConfirmation in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPickUpConfirmation = pickUpConfirmationMapper.toEntity(returnedPickUpConfirmationDTO);
        assertPickUpConfirmationUpdatableFieldsEquals(
            returnedPickUpConfirmation,
            getPersistedPickUpConfirmation(returnedPickUpConfirmation)
        );

        insertedPickUpConfirmation = returnedPickUpConfirmation;
    }

    @Test
    @Transactional
    void createPickUpConfirmationWithExistingId() throws Exception {
        // Create the PickUpConfirmation with an existing ID
        pickUpConfirmation.setId(1L);
        PickUpConfirmationDTO pickUpConfirmationDTO = pickUpConfirmationMapper.toDto(pickUpConfirmation);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPickUpConfirmationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pickUpConfirmationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PickUpConfirmation in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pickUpConfirmation.setCreateddBy(null);

        // Create the PickUpConfirmation, which fails.
        PickUpConfirmationDTO pickUpConfirmationDTO = pickUpConfirmationMapper.toDto(pickUpConfirmation);

        restPickUpConfirmationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pickUpConfirmationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pickUpConfirmation.setCreatedTime(null);

        // Create the PickUpConfirmation, which fails.
        PickUpConfirmationDTO pickUpConfirmationDTO = pickUpConfirmationMapper.toDto(pickUpConfirmation);

        restPickUpConfirmationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pickUpConfirmationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pickUpConfirmation.setUpdatedBy(null);

        // Create the PickUpConfirmation, which fails.
        PickUpConfirmationDTO pickUpConfirmationDTO = pickUpConfirmationMapper.toDto(pickUpConfirmation);

        restPickUpConfirmationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pickUpConfirmationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pickUpConfirmation.setUpdatedTime(null);

        // Create the PickUpConfirmation, which fails.
        PickUpConfirmationDTO pickUpConfirmationDTO = pickUpConfirmationMapper.toDto(pickUpConfirmation);

        restPickUpConfirmationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pickUpConfirmationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPickUpConfirmations() throws Exception {
        // Initialize the database
        insertedPickUpConfirmation = pickUpConfirmationRepository.saveAndFlush(pickUpConfirmation);

        // Get all the pickUpConfirmationList
        restPickUpConfirmationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pickUpConfirmation.getId().intValue())))
            .andExpect(jsonPath("$.[*].confirmDate").value(hasItem(DEFAULT_CONFIRM_DATE.toString())))
            .andExpect(jsonPath("$.[*].confirmValue").value(hasItem(DEFAULT_CONFIRM_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].pickupBy").value(hasItem(DEFAULT_PICKUP_BY)))
            .andExpect(jsonPath("$.[*].pickupTime").value(hasItem(DEFAULT_PICKUP_TIME)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getPickUpConfirmation() throws Exception {
        // Initialize the database
        insertedPickUpConfirmation = pickUpConfirmationRepository.saveAndFlush(pickUpConfirmation);

        // Get the pickUpConfirmation
        restPickUpConfirmationMockMvc
            .perform(get(ENTITY_API_URL_ID, pickUpConfirmation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pickUpConfirmation.getId().intValue()))
            .andExpect(jsonPath("$.confirmDate").value(DEFAULT_CONFIRM_DATE.toString()))
            .andExpect(jsonPath("$.confirmValue").value(DEFAULT_CONFIRM_VALUE.doubleValue()))
            .andExpect(jsonPath("$.pickupBy").value(DEFAULT_PICKUP_BY))
            .andExpect(jsonPath("$.pickupTime").value(DEFAULT_PICKUP_TIME))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPickUpConfirmation() throws Exception {
        // Get the pickUpConfirmation
        restPickUpConfirmationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPickUpConfirmation() throws Exception {
        // Initialize the database
        insertedPickUpConfirmation = pickUpConfirmationRepository.saveAndFlush(pickUpConfirmation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pickUpConfirmation
        PickUpConfirmation updatedPickUpConfirmation = pickUpConfirmationRepository.findById(pickUpConfirmation.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPickUpConfirmation are not directly saved in db
        em.detach(updatedPickUpConfirmation);
        updatedPickUpConfirmation
            .confirmDate(UPDATED_CONFIRM_DATE)
            .confirmValue(UPDATED_CONFIRM_VALUE)
            .pickupBy(UPDATED_PICKUP_BY)
            .pickupTime(UPDATED_PICKUP_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        PickUpConfirmationDTO pickUpConfirmationDTO = pickUpConfirmationMapper.toDto(updatedPickUpConfirmation);

        restPickUpConfirmationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pickUpConfirmationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pickUpConfirmationDTO))
            )
            .andExpect(status().isOk());

        // Validate the PickUpConfirmation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPickUpConfirmationToMatchAllProperties(updatedPickUpConfirmation);
    }

    @Test
    @Transactional
    void putNonExistingPickUpConfirmation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pickUpConfirmation.setId(longCount.incrementAndGet());

        // Create the PickUpConfirmation
        PickUpConfirmationDTO pickUpConfirmationDTO = pickUpConfirmationMapper.toDto(pickUpConfirmation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPickUpConfirmationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pickUpConfirmationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pickUpConfirmationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PickUpConfirmation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPickUpConfirmation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pickUpConfirmation.setId(longCount.incrementAndGet());

        // Create the PickUpConfirmation
        PickUpConfirmationDTO pickUpConfirmationDTO = pickUpConfirmationMapper.toDto(pickUpConfirmation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPickUpConfirmationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pickUpConfirmationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PickUpConfirmation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPickUpConfirmation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pickUpConfirmation.setId(longCount.incrementAndGet());

        // Create the PickUpConfirmation
        PickUpConfirmationDTO pickUpConfirmationDTO = pickUpConfirmationMapper.toDto(pickUpConfirmation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPickUpConfirmationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pickUpConfirmationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PickUpConfirmation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePickUpConfirmationWithPatch() throws Exception {
        // Initialize the database
        insertedPickUpConfirmation = pickUpConfirmationRepository.saveAndFlush(pickUpConfirmation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pickUpConfirmation using partial update
        PickUpConfirmation partialUpdatedPickUpConfirmation = new PickUpConfirmation();
        partialUpdatedPickUpConfirmation.setId(pickUpConfirmation.getId());

        partialUpdatedPickUpConfirmation.confirmValue(UPDATED_CONFIRM_VALUE).pickupBy(UPDATED_PICKUP_BY).updatedBy(UPDATED_UPDATED_BY);

        restPickUpConfirmationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPickUpConfirmation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPickUpConfirmation))
            )
            .andExpect(status().isOk());

        // Validate the PickUpConfirmation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPickUpConfirmationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPickUpConfirmation, pickUpConfirmation),
            getPersistedPickUpConfirmation(pickUpConfirmation)
        );
    }

    @Test
    @Transactional
    void fullUpdatePickUpConfirmationWithPatch() throws Exception {
        // Initialize the database
        insertedPickUpConfirmation = pickUpConfirmationRepository.saveAndFlush(pickUpConfirmation);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pickUpConfirmation using partial update
        PickUpConfirmation partialUpdatedPickUpConfirmation = new PickUpConfirmation();
        partialUpdatedPickUpConfirmation.setId(pickUpConfirmation.getId());

        partialUpdatedPickUpConfirmation
            .confirmDate(UPDATED_CONFIRM_DATE)
            .confirmValue(UPDATED_CONFIRM_VALUE)
            .pickupBy(UPDATED_PICKUP_BY)
            .pickupTime(UPDATED_PICKUP_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restPickUpConfirmationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPickUpConfirmation.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPickUpConfirmation))
            )
            .andExpect(status().isOk());

        // Validate the PickUpConfirmation in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPickUpConfirmationUpdatableFieldsEquals(
            partialUpdatedPickUpConfirmation,
            getPersistedPickUpConfirmation(partialUpdatedPickUpConfirmation)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPickUpConfirmation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pickUpConfirmation.setId(longCount.incrementAndGet());

        // Create the PickUpConfirmation
        PickUpConfirmationDTO pickUpConfirmationDTO = pickUpConfirmationMapper.toDto(pickUpConfirmation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPickUpConfirmationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pickUpConfirmationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pickUpConfirmationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PickUpConfirmation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPickUpConfirmation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pickUpConfirmation.setId(longCount.incrementAndGet());

        // Create the PickUpConfirmation
        PickUpConfirmationDTO pickUpConfirmationDTO = pickUpConfirmationMapper.toDto(pickUpConfirmation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPickUpConfirmationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pickUpConfirmationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PickUpConfirmation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPickUpConfirmation() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pickUpConfirmation.setId(longCount.incrementAndGet());

        // Create the PickUpConfirmation
        PickUpConfirmationDTO pickUpConfirmationDTO = pickUpConfirmationMapper.toDto(pickUpConfirmation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPickUpConfirmationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pickUpConfirmationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PickUpConfirmation in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePickUpConfirmation() throws Exception {
        // Initialize the database
        insertedPickUpConfirmation = pickUpConfirmationRepository.saveAndFlush(pickUpConfirmation);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pickUpConfirmation
        restPickUpConfirmationMockMvc
            .perform(delete(ENTITY_API_URL_ID, pickUpConfirmation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pickUpConfirmationRepository.count();
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

    protected PickUpConfirmation getPersistedPickUpConfirmation(PickUpConfirmation pickUpConfirmation) {
        return pickUpConfirmationRepository.findById(pickUpConfirmation.getId()).orElseThrow();
    }

    protected void assertPersistedPickUpConfirmationToMatchAllProperties(PickUpConfirmation expectedPickUpConfirmation) {
        assertPickUpConfirmationAllPropertiesEquals(expectedPickUpConfirmation, getPersistedPickUpConfirmation(expectedPickUpConfirmation));
    }

    protected void assertPersistedPickUpConfirmationToMatchUpdatableProperties(PickUpConfirmation expectedPickUpConfirmation) {
        assertPickUpConfirmationAllUpdatablePropertiesEquals(
            expectedPickUpConfirmation,
            getPersistedPickUpConfirmation(expectedPickUpConfirmation)
        );
    }
}
