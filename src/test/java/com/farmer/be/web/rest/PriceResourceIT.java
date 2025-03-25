package com.farmer.be.web.rest;

import static com.farmer.be.domain.PriceAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.Price;
import com.farmer.be.repository.PriceRepository;
import com.farmer.be.service.dto.PriceDTO;
import com.farmer.be.service.mapper.PriceMapper;
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
 * Integration tests for the {@link PriceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PriceResourceIT {

    private static final LocalDate DEFAULT_PRICE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PRICE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_PRICE_VALUE = 1F;
    private static final Float UPDATED_PRICE_VALUE = 2F;

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

    private static final String ENTITY_API_URL = "/api/prices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private PriceMapper priceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPriceMockMvc;

    private Price price;

    private Price insertedPrice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Price createEntity() {
        return new Price()
            .priceDate(DEFAULT_PRICE_DATE)
            .priceValue(DEFAULT_PRICE_VALUE)
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
    public static Price createUpdatedEntity() {
        return new Price()
            .priceDate(UPDATED_PRICE_DATE)
            .priceValue(UPDATED_PRICE_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        price = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedPrice != null) {
            priceRepository.delete(insertedPrice);
            insertedPrice = null;
        }
    }

    @Test
    @Transactional
    void createPrice() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Price
        PriceDTO priceDTO = priceMapper.toDto(price);
        var returnedPriceDTO = om.readValue(
            restPriceMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(priceDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PriceDTO.class
        );

        // Validate the Price in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPrice = priceMapper.toEntity(returnedPriceDTO);
        assertPriceUpdatableFieldsEquals(returnedPrice, getPersistedPrice(returnedPrice));

        insertedPrice = returnedPrice;
    }

    @Test
    @Transactional
    void createPriceWithExistingId() throws Exception {
        // Create the Price with an existing ID
        price.setId(1L);
        PriceDTO priceDTO = priceMapper.toDto(price);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPriceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(priceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Price in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        price.setCreateddBy(null);

        // Create the Price, which fails.
        PriceDTO priceDTO = priceMapper.toDto(price);

        restPriceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(priceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        price.setCreatedTime(null);

        // Create the Price, which fails.
        PriceDTO priceDTO = priceMapper.toDto(price);

        restPriceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(priceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        price.setUpdatedBy(null);

        // Create the Price, which fails.
        PriceDTO priceDTO = priceMapper.toDto(price);

        restPriceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(priceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        price.setUpdatedTime(null);

        // Create the Price, which fails.
        PriceDTO priceDTO = priceMapper.toDto(price);

        restPriceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(priceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPrices() throws Exception {
        // Initialize the database
        insertedPrice = priceRepository.saveAndFlush(price);

        // Get all the priceList
        restPriceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(price.getId().intValue())))
            .andExpect(jsonPath("$.[*].priceDate").value(hasItem(DEFAULT_PRICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].priceValue").value(hasItem(DEFAULT_PRICE_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getPrice() throws Exception {
        // Initialize the database
        insertedPrice = priceRepository.saveAndFlush(price);

        // Get the price
        restPriceMockMvc
            .perform(get(ENTITY_API_URL_ID, price.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(price.getId().intValue()))
            .andExpect(jsonPath("$.priceDate").value(DEFAULT_PRICE_DATE.toString()))
            .andExpect(jsonPath("$.priceValue").value(DEFAULT_PRICE_VALUE.doubleValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPrice() throws Exception {
        // Get the price
        restPriceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrice() throws Exception {
        // Initialize the database
        insertedPrice = priceRepository.saveAndFlush(price);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the price
        Price updatedPrice = priceRepository.findById(price.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPrice are not directly saved in db
        em.detach(updatedPrice);
        updatedPrice
            .priceDate(UPDATED_PRICE_DATE)
            .priceValue(UPDATED_PRICE_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        PriceDTO priceDTO = priceMapper.toDto(updatedPrice);

        restPriceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, priceDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(priceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Price in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPriceToMatchAllProperties(updatedPrice);
    }

    @Test
    @Transactional
    void putNonExistingPrice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        price.setId(longCount.incrementAndGet());

        // Create the Price
        PriceDTO priceDTO = priceMapper.toDto(price);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPriceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, priceDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(priceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Price in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        price.setId(longCount.incrementAndGet());

        // Create the Price
        PriceDTO priceDTO = priceMapper.toDto(price);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPriceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(priceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Price in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        price.setId(longCount.incrementAndGet());

        // Create the Price
        PriceDTO priceDTO = priceMapper.toDto(price);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPriceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(priceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Price in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePriceWithPatch() throws Exception {
        // Initialize the database
        insertedPrice = priceRepository.saveAndFlush(price);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the price using partial update
        Price partialUpdatedPrice = new Price();
        partialUpdatedPrice.setId(price.getId());

        partialUpdatedPrice.isActive(UPDATED_IS_ACTIVE).updatedBy(UPDATED_UPDATED_BY).updatedTime(UPDATED_UPDATED_TIME);

        restPriceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrice.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPrice))
            )
            .andExpect(status().isOk());

        // Validate the Price in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPriceUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPrice, price), getPersistedPrice(price));
    }

    @Test
    @Transactional
    void fullUpdatePriceWithPatch() throws Exception {
        // Initialize the database
        insertedPrice = priceRepository.saveAndFlush(price);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the price using partial update
        Price partialUpdatedPrice = new Price();
        partialUpdatedPrice.setId(price.getId());

        partialUpdatedPrice
            .priceDate(UPDATED_PRICE_DATE)
            .priceValue(UPDATED_PRICE_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restPriceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrice.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPrice))
            )
            .andExpect(status().isOk());

        // Validate the Price in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPriceUpdatableFieldsEquals(partialUpdatedPrice, getPersistedPrice(partialUpdatedPrice));
    }

    @Test
    @Transactional
    void patchNonExistingPrice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        price.setId(longCount.incrementAndGet());

        // Create the Price
        PriceDTO priceDTO = priceMapper.toDto(price);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPriceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, priceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(priceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Price in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        price.setId(longCount.incrementAndGet());

        // Create the Price
        PriceDTO priceDTO = priceMapper.toDto(price);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPriceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(priceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Price in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        price.setId(longCount.incrementAndGet());

        // Create the Price
        PriceDTO priceDTO = priceMapper.toDto(price);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPriceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(priceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Price in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrice() throws Exception {
        // Initialize the database
        insertedPrice = priceRepository.saveAndFlush(price);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the price
        restPriceMockMvc
            .perform(delete(ENTITY_API_URL_ID, price.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return priceRepository.count();
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

    protected Price getPersistedPrice(Price price) {
        return priceRepository.findById(price.getId()).orElseThrow();
    }

    protected void assertPersistedPriceToMatchAllProperties(Price expectedPrice) {
        assertPriceAllPropertiesEquals(expectedPrice, getPersistedPrice(expectedPrice));
    }

    protected void assertPersistedPriceToMatchUpdatableProperties(Price expectedPrice) {
        assertPriceAllUpdatablePropertiesEquals(expectedPrice, getPersistedPrice(expectedPrice));
    }
}
