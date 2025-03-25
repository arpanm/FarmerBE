package com.farmer.be.web.rest;

import static com.farmer.be.domain.CarouselContentAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.CarouselContent;
import com.farmer.be.repository.CarouselContentRepository;
import com.farmer.be.service.dto.CarouselContentDTO;
import com.farmer.be.service.mapper.CarouselContentMapper;
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
 * Integration tests for the {@link CarouselContentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CarouselContentResourceIT {

    private static final String DEFAULT_CAROUSEL_TAG = "AAAAAAAAAA";
    private static final String UPDATED_CAROUSEL_TAG = "BBBBBBBBBB";

    private static final Boolean DEFAULT_SHOW_VIEW_MORE = false;
    private static final Boolean UPDATED_SHOW_VIEW_MORE = true;

    private static final String DEFAULT_VIEW_MORE_LINK = "AAAAAAAAAA";
    private static final String UPDATED_VIEW_MORE_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_VIEW_MORE_UTM = "AAAAAAAAAA";
    private static final String UPDATED_VIEW_MORE_UTM = "BBBBBBBBBB";

    private static final String DEFAULT_PIXEL_LINK = "AAAAAAAAAA";
    private static final String UPDATED_PIXEL_LINK = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/carousel-contents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CarouselContentRepository carouselContentRepository;

    @Autowired
    private CarouselContentMapper carouselContentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCarouselContentMockMvc;

    private CarouselContent carouselContent;

    private CarouselContent insertedCarouselContent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarouselContent createEntity() {
        return new CarouselContent()
            .carouselTag(DEFAULT_CAROUSEL_TAG)
            .showViewMore(DEFAULT_SHOW_VIEW_MORE)
            .viewMoreLink(DEFAULT_VIEW_MORE_LINK)
            .viewMoreUtm(DEFAULT_VIEW_MORE_UTM)
            .pixelLink(DEFAULT_PIXEL_LINK)
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
    public static CarouselContent createUpdatedEntity() {
        return new CarouselContent()
            .carouselTag(UPDATED_CAROUSEL_TAG)
            .showViewMore(UPDATED_SHOW_VIEW_MORE)
            .viewMoreLink(UPDATED_VIEW_MORE_LINK)
            .viewMoreUtm(UPDATED_VIEW_MORE_UTM)
            .pixelLink(UPDATED_PIXEL_LINK)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        carouselContent = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedCarouselContent != null) {
            carouselContentRepository.delete(insertedCarouselContent);
            insertedCarouselContent = null;
        }
    }

    @Test
    @Transactional
    void createCarouselContent() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CarouselContent
        CarouselContentDTO carouselContentDTO = carouselContentMapper.toDto(carouselContent);
        var returnedCarouselContentDTO = om.readValue(
            restCarouselContentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carouselContentDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CarouselContentDTO.class
        );

        // Validate the CarouselContent in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCarouselContent = carouselContentMapper.toEntity(returnedCarouselContentDTO);
        assertCarouselContentUpdatableFieldsEquals(returnedCarouselContent, getPersistedCarouselContent(returnedCarouselContent));

        insertedCarouselContent = returnedCarouselContent;
    }

    @Test
    @Transactional
    void createCarouselContentWithExistingId() throws Exception {
        // Create the CarouselContent with an existing ID
        carouselContent.setId(1L);
        CarouselContentDTO carouselContentDTO = carouselContentMapper.toDto(carouselContent);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarouselContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carouselContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CarouselContent in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCarouselTagIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        carouselContent.setCarouselTag(null);

        // Create the CarouselContent, which fails.
        CarouselContentDTO carouselContentDTO = carouselContentMapper.toDto(carouselContent);

        restCarouselContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carouselContentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        carouselContent.setCreateddBy(null);

        // Create the CarouselContent, which fails.
        CarouselContentDTO carouselContentDTO = carouselContentMapper.toDto(carouselContent);

        restCarouselContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carouselContentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        carouselContent.setCreatedTime(null);

        // Create the CarouselContent, which fails.
        CarouselContentDTO carouselContentDTO = carouselContentMapper.toDto(carouselContent);

        restCarouselContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carouselContentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        carouselContent.setUpdatedBy(null);

        // Create the CarouselContent, which fails.
        CarouselContentDTO carouselContentDTO = carouselContentMapper.toDto(carouselContent);

        restCarouselContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carouselContentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        carouselContent.setUpdatedTime(null);

        // Create the CarouselContent, which fails.
        CarouselContentDTO carouselContentDTO = carouselContentMapper.toDto(carouselContent);

        restCarouselContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carouselContentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCarouselContents() throws Exception {
        // Initialize the database
        insertedCarouselContent = carouselContentRepository.saveAndFlush(carouselContent);

        // Get all the carouselContentList
        restCarouselContentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carouselContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].carouselTag").value(hasItem(DEFAULT_CAROUSEL_TAG)))
            .andExpect(jsonPath("$.[*].showViewMore").value(hasItem(DEFAULT_SHOW_VIEW_MORE)))
            .andExpect(jsonPath("$.[*].viewMoreLink").value(hasItem(DEFAULT_VIEW_MORE_LINK)))
            .andExpect(jsonPath("$.[*].viewMoreUtm").value(hasItem(DEFAULT_VIEW_MORE_UTM)))
            .andExpect(jsonPath("$.[*].pixelLink").value(hasItem(DEFAULT_PIXEL_LINK)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getCarouselContent() throws Exception {
        // Initialize the database
        insertedCarouselContent = carouselContentRepository.saveAndFlush(carouselContent);

        // Get the carouselContent
        restCarouselContentMockMvc
            .perform(get(ENTITY_API_URL_ID, carouselContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carouselContent.getId().intValue()))
            .andExpect(jsonPath("$.carouselTag").value(DEFAULT_CAROUSEL_TAG))
            .andExpect(jsonPath("$.showViewMore").value(DEFAULT_SHOW_VIEW_MORE))
            .andExpect(jsonPath("$.viewMoreLink").value(DEFAULT_VIEW_MORE_LINK))
            .andExpect(jsonPath("$.viewMoreUtm").value(DEFAULT_VIEW_MORE_UTM))
            .andExpect(jsonPath("$.pixelLink").value(DEFAULT_PIXEL_LINK))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCarouselContent() throws Exception {
        // Get the carouselContent
        restCarouselContentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCarouselContent() throws Exception {
        // Initialize the database
        insertedCarouselContent = carouselContentRepository.saveAndFlush(carouselContent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the carouselContent
        CarouselContent updatedCarouselContent = carouselContentRepository.findById(carouselContent.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCarouselContent are not directly saved in db
        em.detach(updatedCarouselContent);
        updatedCarouselContent
            .carouselTag(UPDATED_CAROUSEL_TAG)
            .showViewMore(UPDATED_SHOW_VIEW_MORE)
            .viewMoreLink(UPDATED_VIEW_MORE_LINK)
            .viewMoreUtm(UPDATED_VIEW_MORE_UTM)
            .pixelLink(UPDATED_PIXEL_LINK)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        CarouselContentDTO carouselContentDTO = carouselContentMapper.toDto(updatedCarouselContent);

        restCarouselContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carouselContentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(carouselContentDTO))
            )
            .andExpect(status().isOk());

        // Validate the CarouselContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCarouselContentToMatchAllProperties(updatedCarouselContent);
    }

    @Test
    @Transactional
    void putNonExistingCarouselContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carouselContent.setId(longCount.incrementAndGet());

        // Create the CarouselContent
        CarouselContentDTO carouselContentDTO = carouselContentMapper.toDto(carouselContent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarouselContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carouselContentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(carouselContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarouselContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCarouselContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carouselContent.setId(longCount.incrementAndGet());

        // Create the CarouselContent
        CarouselContentDTO carouselContentDTO = carouselContentMapper.toDto(carouselContent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarouselContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(carouselContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarouselContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCarouselContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carouselContent.setId(longCount.incrementAndGet());

        // Create the CarouselContent
        CarouselContentDTO carouselContentDTO = carouselContentMapper.toDto(carouselContent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarouselContentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(carouselContentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarouselContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCarouselContentWithPatch() throws Exception {
        // Initialize the database
        insertedCarouselContent = carouselContentRepository.saveAndFlush(carouselContent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the carouselContent using partial update
        CarouselContent partialUpdatedCarouselContent = new CarouselContent();
        partialUpdatedCarouselContent.setId(carouselContent.getId());

        partialUpdatedCarouselContent.createddBy(UPDATED_CREATEDD_BY).updatedTime(UPDATED_UPDATED_TIME);

        restCarouselContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarouselContent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCarouselContent))
            )
            .andExpect(status().isOk());

        // Validate the CarouselContent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCarouselContentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCarouselContent, carouselContent),
            getPersistedCarouselContent(carouselContent)
        );
    }

    @Test
    @Transactional
    void fullUpdateCarouselContentWithPatch() throws Exception {
        // Initialize the database
        insertedCarouselContent = carouselContentRepository.saveAndFlush(carouselContent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the carouselContent using partial update
        CarouselContent partialUpdatedCarouselContent = new CarouselContent();
        partialUpdatedCarouselContent.setId(carouselContent.getId());

        partialUpdatedCarouselContent
            .carouselTag(UPDATED_CAROUSEL_TAG)
            .showViewMore(UPDATED_SHOW_VIEW_MORE)
            .viewMoreLink(UPDATED_VIEW_MORE_LINK)
            .viewMoreUtm(UPDATED_VIEW_MORE_UTM)
            .pixelLink(UPDATED_PIXEL_LINK)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restCarouselContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarouselContent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCarouselContent))
            )
            .andExpect(status().isOk());

        // Validate the CarouselContent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCarouselContentUpdatableFieldsEquals(
            partialUpdatedCarouselContent,
            getPersistedCarouselContent(partialUpdatedCarouselContent)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCarouselContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carouselContent.setId(longCount.incrementAndGet());

        // Create the CarouselContent
        CarouselContentDTO carouselContentDTO = carouselContentMapper.toDto(carouselContent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarouselContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, carouselContentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(carouselContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarouselContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCarouselContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carouselContent.setId(longCount.incrementAndGet());

        // Create the CarouselContent
        CarouselContentDTO carouselContentDTO = carouselContentMapper.toDto(carouselContent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarouselContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(carouselContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarouselContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCarouselContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        carouselContent.setId(longCount.incrementAndGet());

        // Create the CarouselContent
        CarouselContentDTO carouselContentDTO = carouselContentMapper.toDto(carouselContent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarouselContentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(carouselContentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarouselContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCarouselContent() throws Exception {
        // Initialize the database
        insertedCarouselContent = carouselContentRepository.saveAndFlush(carouselContent);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the carouselContent
        restCarouselContentMockMvc
            .perform(delete(ENTITY_API_URL_ID, carouselContent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return carouselContentRepository.count();
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

    protected CarouselContent getPersistedCarouselContent(CarouselContent carouselContent) {
        return carouselContentRepository.findById(carouselContent.getId()).orElseThrow();
    }

    protected void assertPersistedCarouselContentToMatchAllProperties(CarouselContent expectedCarouselContent) {
        assertCarouselContentAllPropertiesEquals(expectedCarouselContent, getPersistedCarouselContent(expectedCarouselContent));
    }

    protected void assertPersistedCarouselContentToMatchUpdatableProperties(CarouselContent expectedCarouselContent) {
        assertCarouselContentAllUpdatablePropertiesEquals(expectedCarouselContent, getPersistedCarouselContent(expectedCarouselContent));
    }
}
