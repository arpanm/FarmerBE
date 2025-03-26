package com.farmer.be.web.rest;

import static com.farmer.be.domain.BuyerAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.Buyer;
import com.farmer.be.domain.enumeration.Language;
import com.farmer.be.repository.BuyerRepository;
import com.farmer.be.service.dto.BuyerDTO;
import com.farmer.be.service.mapper.BuyerMapper;
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
 * Integration tests for the {@link BuyerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BuyerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = ">\".J<@DE\\w.re";
    private static final String UPDATED_EMAIL = ";~J@&A:}3.%GF[s1";

    private static final Long DEFAULT_PHONE = 1000000000L;
    private static final Long UPDATED_PHONE = 1000000001L;

    private static final Boolean DEFAULT_IS_WHATS_APP_ENABLED = false;
    private static final Boolean UPDATED_IS_WHATS_APP_ENABLED = true;

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

    private static final String ENTITY_API_URL = "/api/buyers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private BuyerMapper buyerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBuyerMockMvc;

    private Buyer buyer;

    private Buyer insertedBuyer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Buyer createEntity() {
        return new Buyer()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .isWhatsAppEnabled(DEFAULT_IS_WHATS_APP_ENABLED)
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
    public static Buyer createUpdatedEntity() {
        return new Buyer()
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .isWhatsAppEnabled(UPDATED_IS_WHATS_APP_ENABLED)
            .preferedLanguage(UPDATED_PREFERED_LANGUAGE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        buyer = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedBuyer != null) {
            buyerRepository.delete(insertedBuyer);
            insertedBuyer = null;
        }
    }

    @Test
    @Transactional
    void createBuyer() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Buyer
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);
        var returnedBuyerDTO = om.readValue(
            restBuyerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buyerDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BuyerDTO.class
        );

        // Validate the Buyer in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBuyer = buyerMapper.toEntity(returnedBuyerDTO);
        assertBuyerUpdatableFieldsEquals(returnedBuyer, getPersistedBuyer(returnedBuyer));

        insertedBuyer = returnedBuyer;
    }

    @Test
    @Transactional
    void createBuyerWithExistingId() throws Exception {
        // Create the Buyer with an existing ID
        buyer.setId(1L);
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuyerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buyerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Buyer in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPhoneIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        buyer.setPhone(null);

        // Create the Buyer, which fails.
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);

        restBuyerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buyerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        buyer.setCreateddBy(null);

        // Create the Buyer, which fails.
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);

        restBuyerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buyerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        buyer.setCreatedTime(null);

        // Create the Buyer, which fails.
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);

        restBuyerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buyerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        buyer.setUpdatedBy(null);

        // Create the Buyer, which fails.
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);

        restBuyerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buyerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        buyer.setUpdatedTime(null);

        // Create the Buyer, which fails.
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);

        restBuyerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buyerDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBuyers() throws Exception {
        // Initialize the database
        insertedBuyer = buyerRepository.saveAndFlush(buyer);

        // Get all the buyerList
        restBuyerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buyer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.intValue())))
            .andExpect(jsonPath("$.[*].isWhatsAppEnabled").value(hasItem(DEFAULT_IS_WHATS_APP_ENABLED)))
            .andExpect(jsonPath("$.[*].preferedLanguage").value(hasItem(DEFAULT_PREFERED_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getBuyer() throws Exception {
        // Initialize the database
        insertedBuyer = buyerRepository.saveAndFlush(buyer);

        // Get the buyer
        restBuyerMockMvc
            .perform(get(ENTITY_API_URL_ID, buyer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(buyer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.intValue()))
            .andExpect(jsonPath("$.isWhatsAppEnabled").value(DEFAULT_IS_WHATS_APP_ENABLED))
            .andExpect(jsonPath("$.preferedLanguage").value(DEFAULT_PREFERED_LANGUAGE.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBuyer() throws Exception {
        // Get the buyer
        restBuyerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBuyer() throws Exception {
        // Initialize the database
        insertedBuyer = buyerRepository.saveAndFlush(buyer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the buyer
        Buyer updatedBuyer = buyerRepository.findById(buyer.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBuyer are not directly saved in db
        em.detach(updatedBuyer);
        updatedBuyer
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .isWhatsAppEnabled(UPDATED_IS_WHATS_APP_ENABLED)
            .preferedLanguage(UPDATED_PREFERED_LANGUAGE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        BuyerDTO buyerDTO = buyerMapper.toDto(updatedBuyer);

        restBuyerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, buyerDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buyerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Buyer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBuyerToMatchAllProperties(updatedBuyer);
    }

    @Test
    @Transactional
    void putNonExistingBuyer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        buyer.setId(longCount.incrementAndGet());

        // Create the Buyer
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuyerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, buyerDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buyerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Buyer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBuyer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        buyer.setId(longCount.incrementAndGet());

        // Create the Buyer
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(buyerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Buyer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBuyer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        buyer.setId(longCount.incrementAndGet());

        // Create the Buyer
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buyerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Buyer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBuyerWithPatch() throws Exception {
        // Initialize the database
        insertedBuyer = buyerRepository.saveAndFlush(buyer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the buyer using partial update
        Buyer partialUpdatedBuyer = new Buyer();
        partialUpdatedBuyer.setId(buyer.getId());

        partialUpdatedBuyer
            .name(UPDATED_NAME)
            .isWhatsAppEnabled(UPDATED_IS_WHATS_APP_ENABLED)
            .preferedLanguage(UPDATED_PREFERED_LANGUAGE)
            .isActive(UPDATED_IS_ACTIVE)
            .createdTime(UPDATED_CREATED_TIME);

        restBuyerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuyer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBuyer))
            )
            .andExpect(status().isOk());

        // Validate the Buyer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBuyerUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBuyer, buyer), getPersistedBuyer(buyer));
    }

    @Test
    @Transactional
    void fullUpdateBuyerWithPatch() throws Exception {
        // Initialize the database
        insertedBuyer = buyerRepository.saveAndFlush(buyer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the buyer using partial update
        Buyer partialUpdatedBuyer = new Buyer();
        partialUpdatedBuyer.setId(buyer.getId());

        partialUpdatedBuyer
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .isWhatsAppEnabled(UPDATED_IS_WHATS_APP_ENABLED)
            .preferedLanguage(UPDATED_PREFERED_LANGUAGE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restBuyerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuyer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBuyer))
            )
            .andExpect(status().isOk());

        // Validate the Buyer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBuyerUpdatableFieldsEquals(partialUpdatedBuyer, getPersistedBuyer(partialUpdatedBuyer));
    }

    @Test
    @Transactional
    void patchNonExistingBuyer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        buyer.setId(longCount.incrementAndGet());

        // Create the Buyer
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuyerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, buyerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(buyerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Buyer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBuyer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        buyer.setId(longCount.incrementAndGet());

        // Create the Buyer
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(buyerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Buyer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBuyer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        buyer.setId(longCount.incrementAndGet());

        // Create the Buyer
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(buyerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Buyer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBuyer() throws Exception {
        // Initialize the database
        insertedBuyer = buyerRepository.saveAndFlush(buyer);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the buyer
        restBuyerMockMvc
            .perform(delete(ENTITY_API_URL_ID, buyer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return buyerRepository.count();
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

    protected Buyer getPersistedBuyer(Buyer buyer) {
        return buyerRepository.findById(buyer.getId()).orElseThrow();
    }

    protected void assertPersistedBuyerToMatchAllProperties(Buyer expectedBuyer) {
        assertBuyerAllPropertiesEquals(expectedBuyer, getPersistedBuyer(expectedBuyer));
    }

    protected void assertPersistedBuyerToMatchUpdatableProperties(Buyer expectedBuyer) {
        assertBuyerAllUpdatablePropertiesEquals(expectedBuyer, getPersistedBuyer(expectedBuyer));
    }
}
