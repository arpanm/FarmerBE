package com.farmer.be.web.rest;

import static com.farmer.be.domain.PickupPaymentAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.PickupPayment;
import com.farmer.be.domain.enumeration.PaymentStatus;
import com.farmer.be.repository.PickupPaymentRepository;
import com.farmer.be.service.dto.PickupPaymentDTO;
import com.farmer.be.service.mapper.PickupPaymentMapper;
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
 * Integration tests for the {@link PickupPaymentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PickupPaymentResourceIT {

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.Pending;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.Initiated;

    private static final String DEFAULT_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PAYMENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PAYMENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_PAYMENT_UPDATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PAYMENT_UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/pickup-payments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PickupPaymentRepository pickupPaymentRepository;

    @Autowired
    private PickupPaymentMapper pickupPaymentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPickupPaymentMockMvc;

    private PickupPayment pickupPayment;

    private PickupPayment insertedPickupPayment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PickupPayment createEntity() {
        return new PickupPayment()
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .transactionId(DEFAULT_TRANSACTION_ID)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .details(DEFAULT_DETAILS)
            .paymentUpdatedBy(DEFAULT_PAYMENT_UPDATED_BY)
            .paymentUpdatedTime(DEFAULT_PAYMENT_UPDATED_TIME)
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
    public static PickupPayment createUpdatedEntity() {
        return new PickupPayment()
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .transactionId(UPDATED_TRANSACTION_ID)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .details(UPDATED_DETAILS)
            .paymentUpdatedBy(UPDATED_PAYMENT_UPDATED_BY)
            .paymentUpdatedTime(UPDATED_PAYMENT_UPDATED_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        pickupPayment = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedPickupPayment != null) {
            pickupPaymentRepository.delete(insertedPickupPayment);
            insertedPickupPayment = null;
        }
    }

    @Test
    @Transactional
    void createPickupPayment() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PickupPayment
        PickupPaymentDTO pickupPaymentDTO = pickupPaymentMapper.toDto(pickupPayment);
        var returnedPickupPaymentDTO = om.readValue(
            restPickupPaymentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pickupPaymentDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PickupPaymentDTO.class
        );

        // Validate the PickupPayment in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPickupPayment = pickupPaymentMapper.toEntity(returnedPickupPaymentDTO);
        assertPickupPaymentUpdatableFieldsEquals(returnedPickupPayment, getPersistedPickupPayment(returnedPickupPayment));

        insertedPickupPayment = returnedPickupPayment;
    }

    @Test
    @Transactional
    void createPickupPaymentWithExistingId() throws Exception {
        // Create the PickupPayment with an existing ID
        pickupPayment.setId(1L);
        PickupPaymentDTO pickupPaymentDTO = pickupPaymentMapper.toDto(pickupPayment);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPickupPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pickupPaymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PickupPayment in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pickupPayment.setCreateddBy(null);

        // Create the PickupPayment, which fails.
        PickupPaymentDTO pickupPaymentDTO = pickupPaymentMapper.toDto(pickupPayment);

        restPickupPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pickupPaymentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pickupPayment.setCreatedTime(null);

        // Create the PickupPayment, which fails.
        PickupPaymentDTO pickupPaymentDTO = pickupPaymentMapper.toDto(pickupPayment);

        restPickupPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pickupPaymentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pickupPayment.setUpdatedBy(null);

        // Create the PickupPayment, which fails.
        PickupPaymentDTO pickupPaymentDTO = pickupPaymentMapper.toDto(pickupPayment);

        restPickupPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pickupPaymentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        pickupPayment.setUpdatedTime(null);

        // Create the PickupPayment, which fails.
        PickupPaymentDTO pickupPaymentDTO = pickupPaymentMapper.toDto(pickupPayment);

        restPickupPaymentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pickupPaymentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPickupPayments() throws Exception {
        // Initialize the database
        insertedPickupPayment = pickupPaymentRepository.saveAndFlush(pickupPayment);

        // Get all the pickupPaymentList
        restPickupPaymentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pickupPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].paymentUpdatedBy").value(hasItem(DEFAULT_PAYMENT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].paymentUpdatedTime").value(hasItem(DEFAULT_PAYMENT_UPDATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getPickupPayment() throws Exception {
        // Initialize the database
        insertedPickupPayment = pickupPaymentRepository.saveAndFlush(pickupPayment);

        // Get the pickupPayment
        restPickupPaymentMockMvc
            .perform(get(ENTITY_API_URL_ID, pickupPayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pickupPayment.getId().intValue()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS))
            .andExpect(jsonPath("$.paymentUpdatedBy").value(DEFAULT_PAYMENT_UPDATED_BY))
            .andExpect(jsonPath("$.paymentUpdatedTime").value(DEFAULT_PAYMENT_UPDATED_TIME.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPickupPayment() throws Exception {
        // Get the pickupPayment
        restPickupPaymentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPickupPayment() throws Exception {
        // Initialize the database
        insertedPickupPayment = pickupPaymentRepository.saveAndFlush(pickupPayment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pickupPayment
        PickupPayment updatedPickupPayment = pickupPaymentRepository.findById(pickupPayment.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPickupPayment are not directly saved in db
        em.detach(updatedPickupPayment);
        updatedPickupPayment
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .transactionId(UPDATED_TRANSACTION_ID)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .details(UPDATED_DETAILS)
            .paymentUpdatedBy(UPDATED_PAYMENT_UPDATED_BY)
            .paymentUpdatedTime(UPDATED_PAYMENT_UPDATED_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        PickupPaymentDTO pickupPaymentDTO = pickupPaymentMapper.toDto(updatedPickupPayment);

        restPickupPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pickupPaymentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pickupPaymentDTO))
            )
            .andExpect(status().isOk());

        // Validate the PickupPayment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPickupPaymentToMatchAllProperties(updatedPickupPayment);
    }

    @Test
    @Transactional
    void putNonExistingPickupPayment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pickupPayment.setId(longCount.incrementAndGet());

        // Create the PickupPayment
        PickupPaymentDTO pickupPaymentDTO = pickupPaymentMapper.toDto(pickupPayment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPickupPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pickupPaymentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pickupPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PickupPayment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPickupPayment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pickupPayment.setId(longCount.incrementAndGet());

        // Create the PickupPayment
        PickupPaymentDTO pickupPaymentDTO = pickupPaymentMapper.toDto(pickupPayment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPickupPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(pickupPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PickupPayment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPickupPayment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pickupPayment.setId(longCount.incrementAndGet());

        // Create the PickupPayment
        PickupPaymentDTO pickupPaymentDTO = pickupPaymentMapper.toDto(pickupPayment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPickupPaymentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(pickupPaymentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PickupPayment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePickupPaymentWithPatch() throws Exception {
        // Initialize the database
        insertedPickupPayment = pickupPaymentRepository.saveAndFlush(pickupPayment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pickupPayment using partial update
        PickupPayment partialUpdatedPickupPayment = new PickupPayment();
        partialUpdatedPickupPayment.setId(pickupPayment.getId());

        partialUpdatedPickupPayment
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .details(UPDATED_DETAILS)
            .paymentUpdatedBy(UPDATED_PAYMENT_UPDATED_BY)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY);

        restPickupPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPickupPayment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPickupPayment))
            )
            .andExpect(status().isOk());

        // Validate the PickupPayment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPickupPaymentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPickupPayment, pickupPayment),
            getPersistedPickupPayment(pickupPayment)
        );
    }

    @Test
    @Transactional
    void fullUpdatePickupPaymentWithPatch() throws Exception {
        // Initialize the database
        insertedPickupPayment = pickupPaymentRepository.saveAndFlush(pickupPayment);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the pickupPayment using partial update
        PickupPayment partialUpdatedPickupPayment = new PickupPayment();
        partialUpdatedPickupPayment.setId(pickupPayment.getId());

        partialUpdatedPickupPayment
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .transactionId(UPDATED_TRANSACTION_ID)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .details(UPDATED_DETAILS)
            .paymentUpdatedBy(UPDATED_PAYMENT_UPDATED_BY)
            .paymentUpdatedTime(UPDATED_PAYMENT_UPDATED_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restPickupPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPickupPayment.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPickupPayment))
            )
            .andExpect(status().isOk());

        // Validate the PickupPayment in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPickupPaymentUpdatableFieldsEquals(partialUpdatedPickupPayment, getPersistedPickupPayment(partialUpdatedPickupPayment));
    }

    @Test
    @Transactional
    void patchNonExistingPickupPayment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pickupPayment.setId(longCount.incrementAndGet());

        // Create the PickupPayment
        PickupPaymentDTO pickupPaymentDTO = pickupPaymentMapper.toDto(pickupPayment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPickupPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pickupPaymentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pickupPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PickupPayment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPickupPayment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pickupPayment.setId(longCount.incrementAndGet());

        // Create the PickupPayment
        PickupPaymentDTO pickupPaymentDTO = pickupPaymentMapper.toDto(pickupPayment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPickupPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(pickupPaymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PickupPayment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPickupPayment() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        pickupPayment.setId(longCount.incrementAndGet());

        // Create the PickupPayment
        PickupPaymentDTO pickupPaymentDTO = pickupPaymentMapper.toDto(pickupPayment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPickupPaymentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(pickupPaymentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PickupPayment in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePickupPayment() throws Exception {
        // Initialize the database
        insertedPickupPayment = pickupPaymentRepository.saveAndFlush(pickupPayment);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the pickupPayment
        restPickupPaymentMockMvc
            .perform(delete(ENTITY_API_URL_ID, pickupPayment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return pickupPaymentRepository.count();
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

    protected PickupPayment getPersistedPickupPayment(PickupPayment pickupPayment) {
        return pickupPaymentRepository.findById(pickupPayment.getId()).orElseThrow();
    }

    protected void assertPersistedPickupPaymentToMatchAllProperties(PickupPayment expectedPickupPayment) {
        assertPickupPaymentAllPropertiesEquals(expectedPickupPayment, getPersistedPickupPayment(expectedPickupPayment));
    }

    protected void assertPersistedPickupPaymentToMatchUpdatableProperties(PickupPayment expectedPickupPayment) {
        assertPickupPaymentAllUpdatablePropertiesEquals(expectedPickupPayment, getPersistedPickupPayment(expectedPickupPayment));
    }
}
