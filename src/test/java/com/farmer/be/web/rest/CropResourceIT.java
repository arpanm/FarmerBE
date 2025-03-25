package com.farmer.be.web.rest;

import static com.farmer.be.domain.CropAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.Crop;
import com.farmer.be.repository.CropRepository;
import com.farmer.be.service.dto.CropDTO;
import com.farmer.be.service.mapper.CropMapper;
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
 * Integration tests for the {@link CropResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CropResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_ORDER_NO = 1L;
    private static final Long UPDATED_ORDER_NO = 2L;

    private static final String DEFAULT_SKU_ID = "AAAAAAAAAA";
    private static final String UPDATED_SKU_ID = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/crops";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private CropMapper cropMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCropMockMvc;

    private Crop crop;

    private Crop insertedCrop;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Crop createEntity() {
        return new Crop()
            .name(DEFAULT_NAME)
            .imagePath(DEFAULT_IMAGE_PATH)
            .description(DEFAULT_DESCRIPTION)
            .orderNo(DEFAULT_ORDER_NO)
            .skuId(DEFAULT_SKU_ID)
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
    public static Crop createUpdatedEntity() {
        return new Crop()
            .name(UPDATED_NAME)
            .imagePath(UPDATED_IMAGE_PATH)
            .description(UPDATED_DESCRIPTION)
            .orderNo(UPDATED_ORDER_NO)
            .skuId(UPDATED_SKU_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        crop = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedCrop != null) {
            cropRepository.delete(insertedCrop);
            insertedCrop = null;
        }
    }

    @Test
    @Transactional
    void createCrop() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Crop
        CropDTO cropDTO = cropMapper.toDto(crop);
        var returnedCropDTO = om.readValue(
            restCropMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cropDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CropDTO.class
        );

        // Validate the Crop in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCrop = cropMapper.toEntity(returnedCropDTO);
        assertCropUpdatableFieldsEquals(returnedCrop, getPersistedCrop(returnedCrop));

        insertedCrop = returnedCrop;
    }

    @Test
    @Transactional
    void createCropWithExistingId() throws Exception {
        // Create the Crop with an existing ID
        crop.setId(1L);
        CropDTO cropDTO = cropMapper.toDto(crop);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCropMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cropDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Crop in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        crop.setCreateddBy(null);

        // Create the Crop, which fails.
        CropDTO cropDTO = cropMapper.toDto(crop);

        restCropMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cropDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        crop.setCreatedTime(null);

        // Create the Crop, which fails.
        CropDTO cropDTO = cropMapper.toDto(crop);

        restCropMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cropDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        crop.setUpdatedBy(null);

        // Create the Crop, which fails.
        CropDTO cropDTO = cropMapper.toDto(crop);

        restCropMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cropDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        crop.setUpdatedTime(null);

        // Create the Crop, which fails.
        CropDTO cropDTO = cropMapper.toDto(crop);

        restCropMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cropDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCrops() throws Exception {
        // Initialize the database
        insertedCrop = cropRepository.saveAndFlush(crop);

        // Get all the cropList
        restCropMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crop.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].imagePath").value(hasItem(DEFAULT_IMAGE_PATH)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].orderNo").value(hasItem(DEFAULT_ORDER_NO.intValue())))
            .andExpect(jsonPath("$.[*].skuId").value(hasItem(DEFAULT_SKU_ID)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getCrop() throws Exception {
        // Initialize the database
        insertedCrop = cropRepository.saveAndFlush(crop);

        // Get the crop
        restCropMockMvc
            .perform(get(ENTITY_API_URL_ID, crop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(crop.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.imagePath").value(DEFAULT_IMAGE_PATH))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.orderNo").value(DEFAULT_ORDER_NO.intValue()))
            .andExpect(jsonPath("$.skuId").value(DEFAULT_SKU_ID))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCrop() throws Exception {
        // Get the crop
        restCropMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCrop() throws Exception {
        // Initialize the database
        insertedCrop = cropRepository.saveAndFlush(crop);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the crop
        Crop updatedCrop = cropRepository.findById(crop.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCrop are not directly saved in db
        em.detach(updatedCrop);
        updatedCrop
            .name(UPDATED_NAME)
            .imagePath(UPDATED_IMAGE_PATH)
            .description(UPDATED_DESCRIPTION)
            .orderNo(UPDATED_ORDER_NO)
            .skuId(UPDATED_SKU_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        CropDTO cropDTO = cropMapper.toDto(updatedCrop);

        restCropMockMvc
            .perform(put(ENTITY_API_URL_ID, cropDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cropDTO)))
            .andExpect(status().isOk());

        // Validate the Crop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCropToMatchAllProperties(updatedCrop);
    }

    @Test
    @Transactional
    void putNonExistingCrop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        crop.setId(longCount.incrementAndGet());

        // Create the Crop
        CropDTO cropDTO = cropMapper.toDto(crop);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCropMockMvc
            .perform(put(ENTITY_API_URL_ID, cropDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cropDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Crop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCrop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        crop.setId(longCount.incrementAndGet());

        // Create the Crop
        CropDTO cropDTO = cropMapper.toDto(crop);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCropMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cropDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Crop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCrop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        crop.setId(longCount.incrementAndGet());

        // Create the Crop
        CropDTO cropDTO = cropMapper.toDto(crop);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCropMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cropDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Crop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCropWithPatch() throws Exception {
        // Initialize the database
        insertedCrop = cropRepository.saveAndFlush(crop);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the crop using partial update
        Crop partialUpdatedCrop = new Crop();
        partialUpdatedCrop.setId(crop.getId());

        partialUpdatedCrop
            .name(UPDATED_NAME)
            .imagePath(UPDATED_IMAGE_PATH)
            .description(UPDATED_DESCRIPTION)
            .orderNo(UPDATED_ORDER_NO)
            .skuId(UPDATED_SKU_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .updatedBy(UPDATED_UPDATED_BY);

        restCropMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCrop.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCrop))
            )
            .andExpect(status().isOk());

        // Validate the Crop in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCropUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCrop, crop), getPersistedCrop(crop));
    }

    @Test
    @Transactional
    void fullUpdateCropWithPatch() throws Exception {
        // Initialize the database
        insertedCrop = cropRepository.saveAndFlush(crop);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the crop using partial update
        Crop partialUpdatedCrop = new Crop();
        partialUpdatedCrop.setId(crop.getId());

        partialUpdatedCrop
            .name(UPDATED_NAME)
            .imagePath(UPDATED_IMAGE_PATH)
            .description(UPDATED_DESCRIPTION)
            .orderNo(UPDATED_ORDER_NO)
            .skuId(UPDATED_SKU_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restCropMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCrop.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCrop))
            )
            .andExpect(status().isOk());

        // Validate the Crop in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCropUpdatableFieldsEquals(partialUpdatedCrop, getPersistedCrop(partialUpdatedCrop));
    }

    @Test
    @Transactional
    void patchNonExistingCrop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        crop.setId(longCount.incrementAndGet());

        // Create the Crop
        CropDTO cropDTO = cropMapper.toDto(crop);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCropMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cropDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cropDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Crop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCrop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        crop.setId(longCount.incrementAndGet());

        // Create the Crop
        CropDTO cropDTO = cropMapper.toDto(crop);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCropMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cropDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Crop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCrop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        crop.setId(longCount.incrementAndGet());

        // Create the Crop
        CropDTO cropDTO = cropMapper.toDto(crop);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCropMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cropDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Crop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCrop() throws Exception {
        // Initialize the database
        insertedCrop = cropRepository.saveAndFlush(crop);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the crop
        restCropMockMvc
            .perform(delete(ENTITY_API_URL_ID, crop.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cropRepository.count();
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

    protected Crop getPersistedCrop(Crop crop) {
        return cropRepository.findById(crop.getId()).orElseThrow();
    }

    protected void assertPersistedCropToMatchAllProperties(Crop expectedCrop) {
        assertCropAllPropertiesEquals(expectedCrop, getPersistedCrop(expectedCrop));
    }

    protected void assertPersistedCropToMatchUpdatableProperties(Crop expectedCrop) {
        assertCropAllUpdatablePropertiesEquals(expectedCrop, getPersistedCrop(expectedCrop));
    }
}
