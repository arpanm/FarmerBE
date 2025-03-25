package com.farmer.be.web.rest;

import static com.farmer.be.domain.BankDetailsAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.BankDetails;
import com.farmer.be.repository.BankDetailsRepository;
import com.farmer.be.service.dto.BankDetailsDTO;
import com.farmer.be.service.mapper.BankDetailsMapper;
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
 * Integration tests for the {@link BankDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankDetailsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_IFS_C = "AAAAAAAAAA";
    private static final String UPDATED_IFS_C = "BBBBBBBBBB";

    private static final String DEFAULT_BANK_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BANK_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_NAME = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/bank-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BankDetailsRepository bankDetailsRepository;

    @Autowired
    private BankDetailsMapper bankDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankDetailsMockMvc;

    private BankDetails bankDetails;

    private BankDetails insertedBankDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BankDetails createEntity() {
        return new BankDetails()
            .name(DEFAULT_NAME)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .ifsC(DEFAULT_IFS_C)
            .bankName(DEFAULT_BANK_NAME)
            .branchName(DEFAULT_BRANCH_NAME)
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
    public static BankDetails createUpdatedEntity() {
        return new BankDetails()
            .name(UPDATED_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .ifsC(UPDATED_IFS_C)
            .bankName(UPDATED_BANK_NAME)
            .branchName(UPDATED_BRANCH_NAME)
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
        bankDetails = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedBankDetails != null) {
            bankDetailsRepository.delete(insertedBankDetails);
            insertedBankDetails = null;
        }
    }

    @Test
    @Transactional
    void createBankDetails() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BankDetails
        BankDetailsDTO bankDetailsDTO = bankDetailsMapper.toDto(bankDetails);
        var returnedBankDetailsDTO = om.readValue(
            restBankDetailsMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankDetailsDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BankDetailsDTO.class
        );

        // Validate the BankDetails in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBankDetails = bankDetailsMapper.toEntity(returnedBankDetailsDTO);
        assertBankDetailsUpdatableFieldsEquals(returnedBankDetails, getPersistedBankDetails(returnedBankDetails));

        insertedBankDetails = returnedBankDetails;
    }

    @Test
    @Transactional
    void createBankDetailsWithExistingId() throws Exception {
        // Create the BankDetails with an existing ID
        bankDetails.setId(1L);
        BankDetailsDTO bankDetailsDTO = bankDetailsMapper.toDto(bankDetails);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BankDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bankDetails.setCreateddBy(null);

        // Create the BankDetails, which fails.
        BankDetailsDTO bankDetailsDTO = bankDetailsMapper.toDto(bankDetails);

        restBankDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankDetailsDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bankDetails.setCreatedTime(null);

        // Create the BankDetails, which fails.
        BankDetailsDTO bankDetailsDTO = bankDetailsMapper.toDto(bankDetails);

        restBankDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankDetailsDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bankDetails.setUpdatedBy(null);

        // Create the BankDetails, which fails.
        BankDetailsDTO bankDetailsDTO = bankDetailsMapper.toDto(bankDetails);

        restBankDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankDetailsDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bankDetails.setUpdatedTime(null);

        // Create the BankDetails, which fails.
        BankDetailsDTO bankDetailsDTO = bankDetailsMapper.toDto(bankDetails);

        restBankDetailsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankDetailsDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBankDetails() throws Exception {
        // Initialize the database
        insertedBankDetails = bankDetailsRepository.saveAndFlush(bankDetails);

        // Get all the bankDetailsList
        restBankDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].ifsC").value(hasItem(DEFAULT_IFS_C)))
            .andExpect(jsonPath("$.[*].bankName").value(hasItem(DEFAULT_BANK_NAME)))
            .andExpect(jsonPath("$.[*].branchName").value(hasItem(DEFAULT_BRANCH_NAME)))
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
    void getBankDetails() throws Exception {
        // Initialize the database
        insertedBankDetails = bankDetailsRepository.saveAndFlush(bankDetails);

        // Get the bankDetails
        restBankDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, bankDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankDetails.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.ifsC").value(DEFAULT_IFS_C))
            .andExpect(jsonPath("$.bankName").value(DEFAULT_BANK_NAME))
            .andExpect(jsonPath("$.branchName").value(DEFAULT_BRANCH_NAME))
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
    void getNonExistingBankDetails() throws Exception {
        // Get the bankDetails
        restBankDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBankDetails() throws Exception {
        // Initialize the database
        insertedBankDetails = bankDetailsRepository.saveAndFlush(bankDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankDetails
        BankDetails updatedBankDetails = bankDetailsRepository.findById(bankDetails.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBankDetails are not directly saved in db
        em.detach(updatedBankDetails);
        updatedBankDetails
            .name(UPDATED_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .ifsC(UPDATED_IFS_C)
            .bankName(UPDATED_BANK_NAME)
            .branchName(UPDATED_BRANCH_NAME)
            .isVerified(UPDATED_IS_VERIFIED)
            .verificationTime(UPDATED_VERIFICATION_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        BankDetailsDTO bankDetailsDTO = bankDetailsMapper.toDto(updatedBankDetails);

        restBankDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bankDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the BankDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBankDetailsToMatchAllProperties(updatedBankDetails);
    }

    @Test
    @Transactional
    void putNonExistingBankDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankDetails.setId(longCount.incrementAndGet());

        // Create the BankDetails
        BankDetailsDTO bankDetailsDTO = bankDetailsMapper.toDto(bankDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bankDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankDetails.setId(longCount.incrementAndGet());

        // Create the BankDetails
        BankDetailsDTO bankDetailsDTO = bankDetailsMapper.toDto(bankDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bankDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankDetails.setId(longCount.incrementAndGet());

        // Create the BankDetails
        BankDetailsDTO bankDetailsDTO = bankDetailsMapper.toDto(bankDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankDetailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bankDetailsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankDetailsWithPatch() throws Exception {
        // Initialize the database
        insertedBankDetails = bankDetailsRepository.saveAndFlush(bankDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankDetails using partial update
        BankDetails partialUpdatedBankDetails = new BankDetails();
        partialUpdatedBankDetails.setId(bankDetails.getId());

        partialUpdatedBankDetails
            .name(UPDATED_NAME)
            .isVerified(UPDATED_IS_VERIFIED)
            .verificationTime(UPDATED_VERIFICATION_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restBankDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBankDetails))
            )
            .andExpect(status().isOk());

        // Validate the BankDetails in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBankDetailsUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBankDetails, bankDetails),
            getPersistedBankDetails(bankDetails)
        );
    }

    @Test
    @Transactional
    void fullUpdateBankDetailsWithPatch() throws Exception {
        // Initialize the database
        insertedBankDetails = bankDetailsRepository.saveAndFlush(bankDetails);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bankDetails using partial update
        BankDetails partialUpdatedBankDetails = new BankDetails();
        partialUpdatedBankDetails.setId(bankDetails.getId());

        partialUpdatedBankDetails
            .name(UPDATED_NAME)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .ifsC(UPDATED_IFS_C)
            .bankName(UPDATED_BANK_NAME)
            .branchName(UPDATED_BRANCH_NAME)
            .isVerified(UPDATED_IS_VERIFIED)
            .verificationTime(UPDATED_VERIFICATION_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restBankDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBankDetails))
            )
            .andExpect(status().isOk());

        // Validate the BankDetails in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBankDetailsUpdatableFieldsEquals(partialUpdatedBankDetails, getPersistedBankDetails(partialUpdatedBankDetails));
    }

    @Test
    @Transactional
    void patchNonExistingBankDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankDetails.setId(longCount.incrementAndGet());

        // Create the BankDetails
        BankDetailsDTO bankDetailsDTO = bankDetailsMapper.toDto(bankDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bankDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankDetails.setId(longCount.incrementAndGet());

        // Create the BankDetails
        BankDetailsDTO bankDetailsDTO = bankDetailsMapper.toDto(bankDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bankDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BankDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankDetails() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bankDetails.setId(longCount.incrementAndGet());

        // Create the BankDetails
        BankDetailsDTO bankDetailsDTO = bankDetailsMapper.toDto(bankDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankDetailsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bankDetailsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BankDetails in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankDetails() throws Exception {
        // Initialize the database
        insertedBankDetails = bankDetailsRepository.saveAndFlush(bankDetails);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bankDetails
        restBankDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bankDetailsRepository.count();
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

    protected BankDetails getPersistedBankDetails(BankDetails bankDetails) {
        return bankDetailsRepository.findById(bankDetails.getId()).orElseThrow();
    }

    protected void assertPersistedBankDetailsToMatchAllProperties(BankDetails expectedBankDetails) {
        assertBankDetailsAllPropertiesEquals(expectedBankDetails, getPersistedBankDetails(expectedBankDetails));
    }

    protected void assertPersistedBankDetailsToMatchUpdatableProperties(BankDetails expectedBankDetails) {
        assertBankDetailsAllUpdatablePropertiesEquals(expectedBankDetails, getPersistedBankDetails(expectedBankDetails));
    }
}
