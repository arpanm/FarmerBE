package com.farmer.be.web.rest;

import static com.farmer.be.domain.AccessoriesAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.Accessories;
import com.farmer.be.repository.AccessoriesRepository;
import com.farmer.be.service.dto.AccessoriesDTO;
import com.farmer.be.service.mapper.AccessoriesMapper;
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
 * Integration tests for the {@link AccessoriesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AccessoriesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_ORDER_NO = 1L;
    private static final Long UPDATED_ORDER_NO = 2L;

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

    private static final String ENTITY_API_URL = "/api/accessories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AccessoriesRepository accessoriesRepository;

    @Autowired
    private AccessoriesMapper accessoriesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccessoriesMockMvc;

    private Accessories accessories;

    private Accessories insertedAccessories;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accessories createEntity() {
        return new Accessories()
            .name(DEFAULT_NAME)
            .imagePath(DEFAULT_IMAGE_PATH)
            .description(DEFAULT_DESCRIPTION)
            .orderNo(DEFAULT_ORDER_NO)
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
    public static Accessories createUpdatedEntity() {
        return new Accessories()
            .name(UPDATED_NAME)
            .imagePath(UPDATED_IMAGE_PATH)
            .description(UPDATED_DESCRIPTION)
            .orderNo(UPDATED_ORDER_NO)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        accessories = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAccessories != null) {
            accessoriesRepository.delete(insertedAccessories);
            insertedAccessories = null;
        }
    }

    @Test
    @Transactional
    void createAccessories() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Accessories
        AccessoriesDTO accessoriesDTO = accessoriesMapper.toDto(accessories);
        var returnedAccessoriesDTO = om.readValue(
            restAccessoriesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(accessoriesDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AccessoriesDTO.class
        );

        // Validate the Accessories in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAccessories = accessoriesMapper.toEntity(returnedAccessoriesDTO);
        assertAccessoriesUpdatableFieldsEquals(returnedAccessories, getPersistedAccessories(returnedAccessories));

        insertedAccessories = returnedAccessories;
    }

    @Test
    @Transactional
    void createAccessoriesWithExistingId() throws Exception {
        // Create the Accessories with an existing ID
        accessories.setId(1L);
        AccessoriesDTO accessoriesDTO = accessoriesMapper.toDto(accessories);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccessoriesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(accessoriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Accessories in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        accessories.setCreateddBy(null);

        // Create the Accessories, which fails.
        AccessoriesDTO accessoriesDTO = accessoriesMapper.toDto(accessories);

        restAccessoriesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(accessoriesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        accessories.setCreatedTime(null);

        // Create the Accessories, which fails.
        AccessoriesDTO accessoriesDTO = accessoriesMapper.toDto(accessories);

        restAccessoriesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(accessoriesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        accessories.setUpdatedBy(null);

        // Create the Accessories, which fails.
        AccessoriesDTO accessoriesDTO = accessoriesMapper.toDto(accessories);

        restAccessoriesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(accessoriesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        accessories.setUpdatedTime(null);

        // Create the Accessories, which fails.
        AccessoriesDTO accessoriesDTO = accessoriesMapper.toDto(accessories);

        restAccessoriesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(accessoriesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAccessories() throws Exception {
        // Initialize the database
        insertedAccessories = accessoriesRepository.saveAndFlush(accessories);

        // Get all the accessoriesList
        restAccessoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accessories.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].imagePath").value(hasItem(DEFAULT_IMAGE_PATH)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO.intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getAccessories() throws Exception {
        // Initialize the database
        insertedAccessories = accessoriesRepository.saveAndFlush(accessories);

        // Get the accessories
        restAccessoriesMockMvc
            .perform(get(ENTITY_API_URL_ID, accessories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accessories.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.imagePath").value(DEFAULT_IMAGE_PATH))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.orderNo").value(DEFAULT_ORDER_NO.intValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAccessories() throws Exception {
        // Get the accessories
        restAccessoriesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAccessories() throws Exception {
        // Initialize the database
        insertedAccessories = accessoriesRepository.saveAndFlush(accessories);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the accessories
        Accessories updatedAccessories = accessoriesRepository.findById(accessories.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAccessories are not directly saved in db
        em.detach(updatedAccessories);
        updatedAccessories
            .name(UPDATED_NAME)
            .imagePath(UPDATED_IMAGE_PATH)
            .description(UPDATED_DESCRIPTION)
            .orderNo(UPDATED_ORDER_NO)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        AccessoriesDTO accessoriesDTO = accessoriesMapper.toDto(updatedAccessories);

        restAccessoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accessoriesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(accessoriesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Accessories in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAccessoriesToMatchAllProperties(updatedAccessories);
    }

    @Test
    @Transactional
    void putNonExistingAccessories() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        accessories.setId(longCount.incrementAndGet());

        // Create the Accessories
        AccessoriesDTO accessoriesDTO = accessoriesMapper.toDto(accessories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccessoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accessoriesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(accessoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accessories in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccessories() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        accessories.setId(longCount.incrementAndGet());

        // Create the Accessories
        AccessoriesDTO accessoriesDTO = accessoriesMapper.toDto(accessories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccessoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(accessoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accessories in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccessories() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        accessories.setId(longCount.incrementAndGet());

        // Create the Accessories
        AccessoriesDTO accessoriesDTO = accessoriesMapper.toDto(accessories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccessoriesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(accessoriesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Accessories in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAccessoriesWithPatch() throws Exception {
        // Initialize the database
        insertedAccessories = accessoriesRepository.saveAndFlush(accessories);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the accessories using partial update
        Accessories partialUpdatedAccessories = new Accessories();
        partialUpdatedAccessories.setId(accessories.getId());

        partialUpdatedAccessories.createdTime(UPDATED_CREATED_TIME).updatedTime(UPDATED_UPDATED_TIME);

        restAccessoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccessories.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAccessories))
            )
            .andExpect(status().isOk());

        // Validate the Accessories in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAccessoriesUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAccessories, accessories),
            getPersistedAccessories(accessories)
        );
    }

    @Test
    @Transactional
    void fullUpdateAccessoriesWithPatch() throws Exception {
        // Initialize the database
        insertedAccessories = accessoriesRepository.saveAndFlush(accessories);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the accessories using partial update
        Accessories partialUpdatedAccessories = new Accessories();
        partialUpdatedAccessories.setId(accessories.getId());

        partialUpdatedAccessories
            .name(UPDATED_NAME)
            .imagePath(UPDATED_IMAGE_PATH)
            .description(UPDATED_DESCRIPTION)
            .orderNo(UPDATED_ORDER_NO)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restAccessoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccessories.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAccessories))
            )
            .andExpect(status().isOk());

        // Validate the Accessories in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAccessoriesUpdatableFieldsEquals(partialUpdatedAccessories, getPersistedAccessories(partialUpdatedAccessories));
    }

    @Test
    @Transactional
    void patchNonExistingAccessories() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        accessories.setId(longCount.incrementAndGet());

        // Create the Accessories
        AccessoriesDTO accessoriesDTO = accessoriesMapper.toDto(accessories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccessoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accessoriesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(accessoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accessories in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccessories() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        accessories.setId(longCount.incrementAndGet());

        // Create the Accessories
        AccessoriesDTO accessoriesDTO = accessoriesMapper.toDto(accessories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccessoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(accessoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accessories in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccessories() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        accessories.setId(longCount.incrementAndGet());

        // Create the Accessories
        AccessoriesDTO accessoriesDTO = accessoriesMapper.toDto(accessories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccessoriesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(accessoriesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Accessories in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAccessories() throws Exception {
        // Initialize the database
        insertedAccessories = accessoriesRepository.saveAndFlush(accessories);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the accessories
        restAccessoriesMockMvc
            .perform(delete(ENTITY_API_URL_ID, accessories.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return accessoriesRepository.count();
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

    protected Accessories getPersistedAccessories(Accessories accessories) {
        return accessoriesRepository.findById(accessories.getId()).orElseThrow();
    }

    protected void assertPersistedAccessoriesToMatchAllProperties(Accessories expectedAccessories) {
        assertAccessoriesAllPropertiesEquals(expectedAccessories, getPersistedAccessories(expectedAccessories));
    }

    protected void assertPersistedAccessoriesToMatchUpdatableProperties(Accessories expectedAccessories) {
        assertAccessoriesAllUpdatablePropertiesEquals(expectedAccessories, getPersistedAccessories(expectedAccessories));
    }
}
