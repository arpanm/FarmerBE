package com.farmer.be.web.rest;

import static com.farmer.be.domain.OtpAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.Otp;
import com.farmer.be.repository.OtpRepository;
import com.farmer.be.service.dto.OtpDTO;
import com.farmer.be.service.mapper.OtpMapper;
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
 * Integration tests for the {@link OtpResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OtpResourceIT {

    private static final String DEFAULT_EMAIL = "g@>O}I[.NN";
    private static final String UPDATED_EMAIL = "d\"5q&A@_-:.:6^";

    private static final Integer DEFAULT_EMAIL_OTP = 1;
    private static final Integer UPDATED_EMAIL_OTP = 2;

    private static final Long DEFAULT_PHONE = 1L;
    private static final Long UPDATED_PHONE = 2L;

    private static final Integer DEFAULT_PHONE_OTP = 1;
    private static final Integer UPDATED_PHONE_OTP = 2;

    private static final Boolean DEFAULT_IS_VALIDATED = false;
    private static final Boolean UPDATED_IS_VALIDATED = true;

    private static final Instant DEFAULT_EXPIRY_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRY_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/otps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private OtpMapper otpMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOtpMockMvc;

    private Otp otp;

    private Otp insertedOtp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Otp createEntity() {
        return new Otp()
            .email(DEFAULT_EMAIL)
            .emailOtp(DEFAULT_EMAIL_OTP)
            .phone(DEFAULT_PHONE)
            .phoneOtp(DEFAULT_PHONE_OTP)
            .isValidated(DEFAULT_IS_VALIDATED)
            .expiryTime(DEFAULT_EXPIRY_TIME)
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
    public static Otp createUpdatedEntity() {
        return new Otp()
            .email(UPDATED_EMAIL)
            .emailOtp(UPDATED_EMAIL_OTP)
            .phone(UPDATED_PHONE)
            .phoneOtp(UPDATED_PHONE_OTP)
            .isValidated(UPDATED_IS_VALIDATED)
            .expiryTime(UPDATED_EXPIRY_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        otp = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedOtp != null) {
            otpRepository.delete(insertedOtp);
            insertedOtp = null;
        }
    }

    @Test
    @Transactional
    void createOtp() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Otp
        OtpDTO otpDTO = otpMapper.toDto(otp);
        var returnedOtpDTO = om.readValue(
            restOtpMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(otpDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OtpDTO.class
        );

        // Validate the Otp in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedOtp = otpMapper.toEntity(returnedOtpDTO);
        assertOtpUpdatableFieldsEquals(returnedOtp, getPersistedOtp(returnedOtp));

        insertedOtp = returnedOtp;
    }

    @Test
    @Transactional
    void createOtpWithExistingId() throws Exception {
        // Create the Otp with an existing ID
        otp.setId(1L);
        OtpDTO otpDTO = otpMapper.toDto(otp);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOtpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(otpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Otp in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        otp.setCreateddBy(null);

        // Create the Otp, which fails.
        OtpDTO otpDTO = otpMapper.toDto(otp);

        restOtpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(otpDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        otp.setCreatedTime(null);

        // Create the Otp, which fails.
        OtpDTO otpDTO = otpMapper.toDto(otp);

        restOtpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(otpDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        otp.setUpdatedBy(null);

        // Create the Otp, which fails.
        OtpDTO otpDTO = otpMapper.toDto(otp);

        restOtpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(otpDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        otp.setUpdatedTime(null);

        // Create the Otp, which fails.
        OtpDTO otpDTO = otpMapper.toDto(otp);

        restOtpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(otpDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOtps() throws Exception {
        // Initialize the database
        insertedOtp = otpRepository.saveAndFlush(otp);

        // Get all the otpList
        restOtpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(otp.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].emailOtp").value(hasItem(DEFAULT_EMAIL_OTP)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].phoneOtp").value(hasItem(DEFAULT_PHONE_OTP)))
            .andExpect(jsonPath("$.[*].isValidated").value(hasItem(DEFAULT_IS_VALIDATED)))
            .andExpect(jsonPath("$.[*].expiryTime").value(hasItem(DEFAULT_EXPIRY_TIME.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getOtp() throws Exception {
        // Initialize the database
        insertedOtp = otpRepository.saveAndFlush(otp);

        // Get the otp
        restOtpMockMvc
            .perform(get(ENTITY_API_URL_ID, otp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(otp.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.emailOtp").value(DEFAULT_EMAIL_OTP))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.intValue()))
            .andExpect(jsonPath("$.phoneOtp").value(DEFAULT_PHONE_OTP))
            .andExpect(jsonPath("$.isValidated").value(DEFAULT_IS_VALIDATED))
            .andExpect(jsonPath("$.expiryTime").value(DEFAULT_EXPIRY_TIME.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingOtp() throws Exception {
        // Get the otp
        restOtpMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOtp() throws Exception {
        // Initialize the database
        insertedOtp = otpRepository.saveAndFlush(otp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the otp
        Otp updatedOtp = otpRepository.findById(otp.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedOtp are not directly saved in db
        em.detach(updatedOtp);
        updatedOtp
            .email(UPDATED_EMAIL)
            .emailOtp(UPDATED_EMAIL_OTP)
            .phone(UPDATED_PHONE)
            .phoneOtp(UPDATED_PHONE_OTP)
            .isValidated(UPDATED_IS_VALIDATED)
            .expiryTime(UPDATED_EXPIRY_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        OtpDTO otpDTO = otpMapper.toDto(updatedOtp);

        restOtpMockMvc
            .perform(put(ENTITY_API_URL_ID, otpDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(otpDTO)))
            .andExpect(status().isOk());

        // Validate the Otp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOtpToMatchAllProperties(updatedOtp);
    }

    @Test
    @Transactional
    void putNonExistingOtp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        otp.setId(longCount.incrementAndGet());

        // Create the Otp
        OtpDTO otpDTO = otpMapper.toDto(otp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOtpMockMvc
            .perform(put(ENTITY_API_URL_ID, otpDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(otpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Otp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOtp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        otp.setId(longCount.incrementAndGet());

        // Create the Otp
        OtpDTO otpDTO = otpMapper.toDto(otp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(otpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Otp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOtp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        otp.setId(longCount.incrementAndGet());

        // Create the Otp
        OtpDTO otpDTO = otpMapper.toDto(otp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtpMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(otpDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Otp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOtpWithPatch() throws Exception {
        // Initialize the database
        insertedOtp = otpRepository.saveAndFlush(otp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the otp using partial update
        Otp partialUpdatedOtp = new Otp();
        partialUpdatedOtp.setId(otp.getId());

        partialUpdatedOtp.isValidated(UPDATED_IS_VALIDATED).expiryTime(UPDATED_EXPIRY_TIME).updatedBy(UPDATED_UPDATED_BY);

        restOtpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOtp.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOtp))
            )
            .andExpect(status().isOk());

        // Validate the Otp in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOtpUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedOtp, otp), getPersistedOtp(otp));
    }

    @Test
    @Transactional
    void fullUpdateOtpWithPatch() throws Exception {
        // Initialize the database
        insertedOtp = otpRepository.saveAndFlush(otp);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the otp using partial update
        Otp partialUpdatedOtp = new Otp();
        partialUpdatedOtp.setId(otp.getId());

        partialUpdatedOtp
            .email(UPDATED_EMAIL)
            .emailOtp(UPDATED_EMAIL_OTP)
            .phone(UPDATED_PHONE)
            .phoneOtp(UPDATED_PHONE_OTP)
            .isValidated(UPDATED_IS_VALIDATED)
            .expiryTime(UPDATED_EXPIRY_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restOtpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOtp.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOtp))
            )
            .andExpect(status().isOk());

        // Validate the Otp in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOtpUpdatableFieldsEquals(partialUpdatedOtp, getPersistedOtp(partialUpdatedOtp));
    }

    @Test
    @Transactional
    void patchNonExistingOtp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        otp.setId(longCount.incrementAndGet());

        // Create the Otp
        OtpDTO otpDTO = otpMapper.toDto(otp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOtpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, otpDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(otpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Otp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOtp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        otp.setId(longCount.incrementAndGet());

        // Create the Otp
        OtpDTO otpDTO = otpMapper.toDto(otp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(otpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Otp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOtp() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        otp.setId(longCount.incrementAndGet());

        // Create the Otp
        OtpDTO otpDTO = otpMapper.toDto(otp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtpMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(otpDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Otp in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOtp() throws Exception {
        // Initialize the database
        insertedOtp = otpRepository.saveAndFlush(otp);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the otp
        restOtpMockMvc.perform(delete(ENTITY_API_URL_ID, otp.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return otpRepository.count();
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

    protected Otp getPersistedOtp(Otp otp) {
        return otpRepository.findById(otp.getId()).orElseThrow();
    }

    protected void assertPersistedOtpToMatchAllProperties(Otp expectedOtp) {
        assertOtpAllPropertiesEquals(expectedOtp, getPersistedOtp(expectedOtp));
    }

    protected void assertPersistedOtpToMatchUpdatableProperties(Otp expectedOtp) {
        assertOtpAllUpdatablePropertiesEquals(expectedOtp, getPersistedOtp(expectedOtp));
    }
}
