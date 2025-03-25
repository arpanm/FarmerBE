package com.farmer.be.web.rest;

import static com.farmer.be.domain.PanDetailsAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.PanDetails;
import com.farmer.be.domain.enumeration.Gender;
import com.farmer.be.repository.PanDetailsRepository;
import com.farmer.be.service.dto.PanDetailsDTO;
import com.farmer.be.service.mapper.PanDetailsMapper;
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
 * Integration tests for the {@link PanDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PanDetailsResourceIT {

    private static final String DEFAULT_PAN = "AAAAAAAAAA";
    private static final String UPDATED_PAN = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final Gender DEFAULT_GENDER = Gender.Male;
    private static final Gender UPDATED_GENDER = Gender.Female;

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_VERIFIED = false;
    private static final Boolean UPDATED_IS_VERIFIED = true;

    private static final Instant DEFAULT_VERIFICATION_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VERIFICATION_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/pan-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PanDetailsRepository panDetailsRepository;

    @Autowired
    private PanDetailsMapper panDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPanDetailsMockMvc;

    private PanDetails panDetails;

    private PanDetails insertedPanDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PanDetails createEntity() {
        return new PanDetails()
            .pan(DEFAULT_PAN)
            .name(DEFAULT_NAME)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .gender(DEFAULT_GENDER)
            .country(DEFAULT_COUNTRY)
            .isVerified(DEFAULT_IS_VERIFIED)
            .verificationTime(DEFAULT_VERIFICATION_TIME)
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
    public static PanDetails createUpdatedEntity() {
        return new PanDetails()
            .pan(UPDATED_PAN)
            .name(UPDATED_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .gender(UPDATED_GENDER)
            .country(UPDATED_COUNTRY)
            .isVerified(UPDATED_IS_VERIFIED)
            .verificationTime(UPDATED_VERIFICATION_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        panDetails = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedPanDetails != null) {
            panDetailsRepository.delete(insertedPanDetails);
            insertedPanDetails = null;
        }
    }

    @Test
    @Transactional
    void createPanDetails() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PanDetails
        PanDetailsDTO panDetailsDTO = panDetailsMapper.toDto(panDetails);
        var returnedPanDetailsDTO = om.readValue(
            restPanDetailsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(panDetailsDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PanDetailsDTO.class
        );

        // Validate the PanDetails in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPanDetails = panDetailsMapper.toEntity(returnedPanDetailsDTO);
        assertPanDetailsUpdatableFieldsEquals(returnedPanDetails, getPersistedPanDetails(returnedPanDetails));

        insertedPanDetails = returnedPanDetails;
    }

    @Test
    @Transactional
    void createPanDetailsWithExistingId() throws Exception {
        // Create the PanDetails with an existing ID
        panDetails.setId(1L);
        PanDetailsDTO panDetailsDTO = panDetailsMapper.toDto(panDetails);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPanDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(panDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PanDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPanIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        panDetails.setPan(null);

        // Create the PanDetails, which fails.
        PanDetailsDTO panDetailsDTO = panDetailsMapper.toDto(panDetails);

        restPanDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(panDetailsDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        panDetails.setCreateddBy(null);

        // Create the PanDetails, which fails.
        PanDetailsDTO panDetailsDTO = panDetailsMapper.toDto(panDetails);

        restPanDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(panDetailsDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        panDetails.setCreatedTime(null);

        // Create the PanDetails, which fails.
        PanDetailsDTO panDetailsDTO = panDetailsMapper.toDto(panDetails);

        restPanDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(panDetailsDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        panDetails.setUpdatedBy(null);

        // Create the PanDetails, which fails.
        PanDetailsDTO panDetailsDTO = panDetailsMapper.toDto(panDetails);

        restPanDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(panDetailsDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        panDetails.setUpdatedTime(null);

        // Create the PanDetails, which fails.
        PanDetailsDTO panDetailsDTO = panDetailsMapper.toDto(panDetails);

        restPanDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(panDetailsDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPanDetails() throws Exception {
        // Initialize the database
        insertedPanDetails = panDetailsRepository.saveAndFlush(panDetails);

        // Get all the panDetailsList
        restPanDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(panDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].pan").value(hasItem(DEFAULT_PAN)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].isVerified").value(hasItem(DEFAULT_IS_VERIFIED)))
            .andExpect(jsonPath("$.[*].verificationTime").value(hasItem(DEFAULT_VERIFICATION_TIME.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getPanDetails() throws Exception {
        // Initialize the database
        insertedPanDetails = panDetailsRepository.saveAndFlush(panDetails);

        // Get the panDetails
        restPanDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, panDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(panDetails.getId().intValue()))
            .andExpect(jsonPath("$.pan").value(DEFAULT_PAN))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.isVerified").value(DEFAULT_IS_VERIFIED))
            .andExpect(jsonPath("$.verificationTime").value(DEFAULT_VERIFICATION_TIME.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPanDetails() throws Exception {
        // Get the panDetails
        restPanDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPanDetails() throws Exception {
        // Initialize the database
        insertedPanDetails = panDetailsRepository.saveAndFlush(panDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the panDetails
        PanDetails updatedPanDetails = panDetailsRepository.findById(panDetails.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPanDetails are not directly saved in db
        em.detach(updatedPanDetails);
        updatedPanDetails
            .pan(UPDATED_PAN)
            .name(UPDATED_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .gender(UPDATED_GENDER)
            .country(UPDATED_COUNTRY)
            .isVerified(UPDATED_IS_VERIFIED)
            .verificationTime(UPDATED_VERIFICATION_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        PanDetailsDTO panDetailsDTO = panDetailsMapper.toDto(updatedPanDetails);

        restPanDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, panDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(panDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the PanDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPanDetailsToMatchAllProperties(updatedPanDetails);
    }

    @Test
    @Transactional
    void putNonExistingPanDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        panDetails.setId(longCount.incrementAndGet());

        // Create the PanDetails
        PanDetailsDTO panDetailsDTO = panDetailsMapper.toDto(panDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPanDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, panDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(panDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PanDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPanDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        panDetails.setId(longCount.incrementAndGet());

        // Create the PanDetails
        PanDetailsDTO panDetailsDTO = panDetailsMapper.toDto(panDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPanDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(panDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PanDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPanDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        panDetails.setId(longCount.incrementAndGet());

        // Create the PanDetails
        PanDetailsDTO panDetailsDTO = panDetailsMapper.toDto(panDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPanDetailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(panDetailsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PanDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePanDetailsWithPatch() throws Exception {
        // Initialize the database
        insertedPanDetails = panDetailsRepository.saveAndFlush(panDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the panDetails using partial update
        PanDetails partialUpdatedPanDetails = new PanDetails();
        partialUpdatedPanDetails.setId(panDetails.getId());

        partialUpdatedPanDetails
            .pan(UPDATED_PAN)
            .name(UPDATED_NAME)
            .country(UPDATED_COUNTRY)
            .verificationTime(UPDATED_VERIFICATION_TIME)
            .createddBy(UPDATED_CREATEDD_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restPanDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPanDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPanDetails))
            )
            .andExpect(status().isOk());

        // Validate the PanDetails in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPanDetailsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPanDetails, panDetails),
            getPersistedPanDetails(panDetails)
        );
    }

    @Test
    @Transactional
    void fullUpdatePanDetailsWithPatch() throws Exception {
        // Initialize the database
        insertedPanDetails = panDetailsRepository.saveAndFlush(panDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the panDetails using partial update
        PanDetails partialUpdatedPanDetails = new PanDetails();
        partialUpdatedPanDetails.setId(panDetails.getId());

        partialUpdatedPanDetails
            .pan(UPDATED_PAN)
            .name(UPDATED_NAME)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .gender(UPDATED_GENDER)
            .country(UPDATED_COUNTRY)
            .isVerified(UPDATED_IS_VERIFIED)
            .verificationTime(UPDATED_VERIFICATION_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restPanDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPanDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPanDetails))
            )
            .andExpect(status().isOk());

        // Validate the PanDetails in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPanDetailsUpdatableFieldsEquals(partialUpdatedPanDetails, getPersistedPanDetails(partialUpdatedPanDetails));
    }

    @Test
    @Transactional
    void patchNonExistingPanDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        panDetails.setId(longCount.incrementAndGet());

        // Create the PanDetails
        PanDetailsDTO panDetailsDTO = panDetailsMapper.toDto(panDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPanDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, panDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(panDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PanDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPanDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        panDetails.setId(longCount.incrementAndGet());

        // Create the PanDetails
        PanDetailsDTO panDetailsDTO = panDetailsMapper.toDto(panDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPanDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(panDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PanDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPanDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        panDetails.setId(longCount.incrementAndGet());

        // Create the PanDetails
        PanDetailsDTO panDetailsDTO = panDetailsMapper.toDto(panDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPanDetailsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(panDetailsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PanDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePanDetails() throws Exception {
        // Initialize the database
        insertedPanDetails = panDetailsRepository.saveAndFlush(panDetails);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the panDetails
        restPanDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, panDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return panDetailsRepository.count();
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

    protected PanDetails getPersistedPanDetails(PanDetails panDetails) {
        return panDetailsRepository.findById(panDetails.getId()).orElseThrow();
    }

    protected void assertPersistedPanDetailsToMatchAllProperties(PanDetails expectedPanDetails) {
        assertPanDetailsAllPropertiesEquals(expectedPanDetails, getPersistedPanDetails(expectedPanDetails));
    }

    protected void assertPersistedPanDetailsToMatchUpdatableProperties(PanDetails expectedPanDetails) {
        assertPanDetailsAllUpdatablePropertiesEquals(expectedPanDetails, getPersistedPanDetails(expectedPanDetails));
    }
}
