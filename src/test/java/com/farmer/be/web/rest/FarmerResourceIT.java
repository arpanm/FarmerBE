package com.farmer.be.web.rest;

import static com.farmer.be.domain.FarmerAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.Farmer;
import com.farmer.be.domain.enumeration.FarmerType;
import com.farmer.be.domain.enumeration.Language;
import com.farmer.be.repository.FarmerRepository;
import com.farmer.be.service.dto.FarmerDTO;
import com.farmer.be.service.mapper.FarmerMapper;
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
 * Integration tests for the {@link FarmerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FarmerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "`K@yoJ.'#mdO_";
    private static final String UPDATED_EMAIL = ":6s-0G@[zTE.m\\/!r";

    private static final Long DEFAULT_PHONE = 1000000000L;
    private static final Long UPDATED_PHONE = 1000000001L;

    private static final Boolean DEFAULT_IS_WHATS_APP_ENABLED = false;
    private static final Boolean UPDATED_IS_WHATS_APP_ENABLED = true;

    private static final FarmerType DEFAULT_FARMER_TYPE = FarmerType.Farmer;
    private static final FarmerType UPDATED_FARMER_TYPE = FarmerType.Trader;

    private static final Language DEFAULT_PREFERED_LANGUAGE = Language.English;
    private static final Language UPDATED_PREFERED_LANGUAGE = Language.Hindi;

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

    private static final String ENTITY_API_URL = "/api/farmers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FarmerRepository farmerRepository;

    @Autowired
    private FarmerMapper farmerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFarmerMockMvc;

    private Farmer farmer;

    private Farmer insertedFarmer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Farmer createEntity() {
        return new Farmer()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .isWhatsAppEnabled(DEFAULT_IS_WHATS_APP_ENABLED)
            .farmerType(DEFAULT_FARMER_TYPE)
            .preferedLanguage(DEFAULT_PREFERED_LANGUAGE)
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
    public static Farmer createUpdatedEntity() {
        return new Farmer()
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .isWhatsAppEnabled(UPDATED_IS_WHATS_APP_ENABLED)
            .farmerType(UPDATED_FARMER_TYPE)
            .preferedLanguage(UPDATED_PREFERED_LANGUAGE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        farmer = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedFarmer != null) {
            farmerRepository.delete(insertedFarmer);
            insertedFarmer = null;
        }
    }

    @Test
    @Transactional
    void createFarmer() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Farmer
        FarmerDTO farmerDTO = farmerMapper.toDto(farmer);
        var returnedFarmerDTO = om.readValue(
            restFarmerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(farmerDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FarmerDTO.class
        );

        // Validate the Farmer in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFarmer = farmerMapper.toEntity(returnedFarmerDTO);
        assertFarmerUpdatableFieldsEquals(returnedFarmer, getPersistedFarmer(returnedFarmer));

        insertedFarmer = returnedFarmer;
    }

    @Test
    @Transactional
    void createFarmerWithExistingId() throws Exception {
        // Create the Farmer with an existing ID
        farmer.setId(1L);
        FarmerDTO farmerDTO = farmerMapper.toDto(farmer);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFarmerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(farmerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Farmer in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPhoneIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        farmer.setPhone(null);

        // Create the Farmer, which fails.
        FarmerDTO farmerDTO = farmerMapper.toDto(farmer);

        restFarmerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(farmerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        farmer.setCreateddBy(null);

        // Create the Farmer, which fails.
        FarmerDTO farmerDTO = farmerMapper.toDto(farmer);

        restFarmerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(farmerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        farmer.setCreatedTime(null);

        // Create the Farmer, which fails.
        FarmerDTO farmerDTO = farmerMapper.toDto(farmer);

        restFarmerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(farmerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        farmer.setUpdatedBy(null);

        // Create the Farmer, which fails.
        FarmerDTO farmerDTO = farmerMapper.toDto(farmer);

        restFarmerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(farmerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        farmer.setUpdatedTime(null);

        // Create the Farmer, which fails.
        FarmerDTO farmerDTO = farmerMapper.toDto(farmer);

        restFarmerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(farmerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFarmers() throws Exception {
        // Initialize the database
        insertedFarmer = farmerRepository.saveAndFlush(farmer);

        // Get all the farmerList
        restFarmerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(farmer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].isWhatsAppEnabled").value(hasItem(DEFAULT_IS_WHATS_APP_ENABLED)))
            .andExpect(jsonPath("$.[*].farmerType").value(hasItem(DEFAULT_FARMER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].preferedLanguage").value(hasItem(DEFAULT_PREFERED_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getFarmer() throws Exception {
        // Initialize the database
        insertedFarmer = farmerRepository.saveAndFlush(farmer);

        // Get the farmer
        restFarmerMockMvc
            .perform(get(ENTITY_API_URL_ID, farmer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(farmer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.intValue()))
            .andExpect(jsonPath("$.isWhatsAppEnabled").value(DEFAULT_IS_WHATS_APP_ENABLED))
            .andExpect(jsonPath("$.farmerType").value(DEFAULT_FARMER_TYPE.toString()))
            .andExpect(jsonPath("$.preferedLanguage").value(DEFAULT_PREFERED_LANGUAGE.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFarmer() throws Exception {
        // Get the farmer
        restFarmerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFarmer() throws Exception {
        // Initialize the database
        insertedFarmer = farmerRepository.saveAndFlush(farmer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the farmer
        Farmer updatedFarmer = farmerRepository.findById(farmer.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFarmer are not directly saved in db
        em.detach(updatedFarmer);
        updatedFarmer
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .isWhatsAppEnabled(UPDATED_IS_WHATS_APP_ENABLED)
            .farmerType(UPDATED_FARMER_TYPE)
            .preferedLanguage(UPDATED_PREFERED_LANGUAGE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        FarmerDTO farmerDTO = farmerMapper.toDto(updatedFarmer);

        restFarmerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, farmerDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(farmerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Farmer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFarmerToMatchAllProperties(updatedFarmer);
    }

    @Test
    @Transactional
    void putNonExistingFarmer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        farmer.setId(longCount.incrementAndGet());

        // Create the Farmer
        FarmerDTO farmerDTO = farmerMapper.toDto(farmer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFarmerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, farmerDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(farmerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Farmer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFarmer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        farmer.setId(longCount.incrementAndGet());

        // Create the Farmer
        FarmerDTO farmerDTO = farmerMapper.toDto(farmer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFarmerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(farmerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Farmer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFarmer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        farmer.setId(longCount.incrementAndGet());

        // Create the Farmer
        FarmerDTO farmerDTO = farmerMapper.toDto(farmer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFarmerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(farmerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Farmer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFarmerWithPatch() throws Exception {
        // Initialize the database
        insertedFarmer = farmerRepository.saveAndFlush(farmer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the farmer using partial update
        Farmer partialUpdatedFarmer = new Farmer();
        partialUpdatedFarmer.setId(farmer.getId());

        partialUpdatedFarmer
            .phone(UPDATED_PHONE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFarmerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFarmer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFarmer))
            )
            .andExpect(status().isOk());

        // Validate the Farmer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFarmerUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedFarmer, farmer), getPersistedFarmer(farmer));
    }

    @Test
    @Transactional
    void fullUpdateFarmerWithPatch() throws Exception {
        // Initialize the database
        insertedFarmer = farmerRepository.saveAndFlush(farmer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the farmer using partial update
        Farmer partialUpdatedFarmer = new Farmer();
        partialUpdatedFarmer.setId(farmer.getId());

        partialUpdatedFarmer
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .isWhatsAppEnabled(UPDATED_IS_WHATS_APP_ENABLED)
            .farmerType(UPDATED_FARMER_TYPE)
            .preferedLanguage(UPDATED_PREFERED_LANGUAGE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFarmerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFarmer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFarmer))
            )
            .andExpect(status().isOk());

        // Validate the Farmer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFarmerUpdatableFieldsEquals(partialUpdatedFarmer, getPersistedFarmer(partialUpdatedFarmer));
    }

    @Test
    @Transactional
    void patchNonExistingFarmer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        farmer.setId(longCount.incrementAndGet());

        // Create the Farmer
        FarmerDTO farmerDTO = farmerMapper.toDto(farmer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFarmerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, farmerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(farmerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Farmer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFarmer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        farmer.setId(longCount.incrementAndGet());

        // Create the Farmer
        FarmerDTO farmerDTO = farmerMapper.toDto(farmer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFarmerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(farmerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Farmer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFarmer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        farmer.setId(longCount.incrementAndGet());

        // Create the Farmer
        FarmerDTO farmerDTO = farmerMapper.toDto(farmer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFarmerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(farmerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Farmer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFarmer() throws Exception {
        // Initialize the database
        insertedFarmer = farmerRepository.saveAndFlush(farmer);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the farmer
        restFarmerMockMvc
            .perform(delete(ENTITY_API_URL_ID, farmer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return farmerRepository.count();
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

    protected Farmer getPersistedFarmer(Farmer farmer) {
        return farmerRepository.findById(farmer.getId()).orElseThrow();
    }

    protected void assertPersistedFarmerToMatchAllProperties(Farmer expectedFarmer) {
        assertFarmerAllPropertiesEquals(expectedFarmer, getPersistedFarmer(expectedFarmer));
    }

    protected void assertPersistedFarmerToMatchUpdatableProperties(Farmer expectedFarmer) {
        assertFarmerAllUpdatablePropertiesEquals(expectedFarmer, getPersistedFarmer(expectedFarmer));
    }
}
