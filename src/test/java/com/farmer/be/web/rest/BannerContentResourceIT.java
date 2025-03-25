package com.farmer.be.web.rest;

import static com.farmer.be.domain.BannerContentAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.BannerContent;
import com.farmer.be.repository.BannerContentRepository;
import com.farmer.be.service.dto.BannerContentDTO;
import com.farmer.be.service.mapper.BannerContentMapper;
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
 * Integration tests for the {@link BannerContentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BannerContentResourceIT {

    private static final String DEFAULT_BANNER_TAG = "AAAAAAAAAA";
    private static final String UPDATED_BANNER_TAG = "BBBBBBBBBB";

    private static final String DEFAULT_LOGO_PATH = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_HEADING = "AAAAAAAAAA";
    private static final String UPDATED_HEADING = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_HEADING = "AAAAAAAAAA";
    private static final String UPDATED_SUB_HEADING = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LANDING_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LANDING_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_LANDING_UTM = "AAAAAAAAAA";
    private static final String UPDATED_LANDING_UTM = "BBBBBBBBBB";

    private static final String DEFAULT_PIXEL_LINK = "AAAAAAAAAA";
    private static final String UPDATED_PIXEL_LINK = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/banner-contents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BannerContentRepository bannerContentRepository;

    @Autowired
    private BannerContentMapper bannerContentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBannerContentMockMvc;

    private BannerContent bannerContent;

    private BannerContent insertedBannerContent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BannerContent createEntity() {
        return new BannerContent()
            .bannerTag(DEFAULT_BANNER_TAG)
            .logoPath(DEFAULT_LOGO_PATH)
            .imagePath(DEFAULT_IMAGE_PATH)
            .heading(DEFAULT_HEADING)
            .subHeading(DEFAULT_SUB_HEADING)
            .description(DEFAULT_DESCRIPTION)
            .landingLink(DEFAULT_LANDING_LINK)
            .landingUtm(DEFAULT_LANDING_UTM)
            .pixelLink(DEFAULT_PIXEL_LINK)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
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
    public static BannerContent createUpdatedEntity() {
        return new BannerContent()
            .bannerTag(UPDATED_BANNER_TAG)
            .logoPath(UPDATED_LOGO_PATH)
            .imagePath(UPDATED_IMAGE_PATH)
            .heading(UPDATED_HEADING)
            .subHeading(UPDATED_SUB_HEADING)
            .description(UPDATED_DESCRIPTION)
            .landingLink(UPDATED_LANDING_LINK)
            .landingUtm(UPDATED_LANDING_UTM)
            .pixelLink(UPDATED_PIXEL_LINK)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        bannerContent = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedBannerContent != null) {
            bannerContentRepository.delete(insertedBannerContent);
            insertedBannerContent = null;
        }
    }

    @Test
    @Transactional
    void createBannerContent() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BannerContent
        BannerContentDTO bannerContentDTO = bannerContentMapper.toDto(bannerContent);
        var returnedBannerContentDTO = om.readValue(
            restBannerContentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bannerContentDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BannerContentDTO.class
        );

        // Validate the BannerContent in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBannerContent = bannerContentMapper.toEntity(returnedBannerContentDTO);
        assertBannerContentUpdatableFieldsEquals(returnedBannerContent, getPersistedBannerContent(returnedBannerContent));

        insertedBannerContent = returnedBannerContent;
    }

    @Test
    @Transactional
    void createBannerContentWithExistingId() throws Exception {
        // Create the BannerContent with an existing ID
        bannerContent.setId(1L);
        BannerContentDTO bannerContentDTO = bannerContentMapper.toDto(bannerContent);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBannerContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bannerContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BannerContent in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkBannerTagIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bannerContent.setBannerTag(null);

        // Create the BannerContent, which fails.
        BannerContentDTO bannerContentDTO = bannerContentMapper.toDto(bannerContent);

        restBannerContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bannerContentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bannerContent.setCreateddBy(null);

        // Create the BannerContent, which fails.
        BannerContentDTO bannerContentDTO = bannerContentMapper.toDto(bannerContent);

        restBannerContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bannerContentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bannerContent.setCreatedTime(null);

        // Create the BannerContent, which fails.
        BannerContentDTO bannerContentDTO = bannerContentMapper.toDto(bannerContent);

        restBannerContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bannerContentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bannerContent.setUpdatedBy(null);

        // Create the BannerContent, which fails.
        BannerContentDTO bannerContentDTO = bannerContentMapper.toDto(bannerContent);

        restBannerContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bannerContentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bannerContent.setUpdatedTime(null);

        // Create the BannerContent, which fails.
        BannerContentDTO bannerContentDTO = bannerContentMapper.toDto(bannerContent);

        restBannerContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bannerContentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBannerContents() throws Exception {
        // Initialize the database
        insertedBannerContent = bannerContentRepository.saveAndFlush(bannerContent);

        // Get all the bannerContentList
        restBannerContentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bannerContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].bannerTag").value(hasItem(DEFAULT_BANNER_TAG)))
            .andExpect(jsonPath("$.[*].logoPath").value(hasItem(DEFAULT_LOGO_PATH)))
            .andExpect(jsonPath("$.[*].imagePath").value(hasItem(DEFAULT_IMAGE_PATH)))
            .andExpect(jsonPath("$.[*].heading").value(hasItem(DEFAULT_HEADING)))
            .andExpect(jsonPath("$.[*].subHeading").value(hasItem(DEFAULT_SUB_HEADING)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].landingLink").value(hasItem(DEFAULT_LANDING_LINK)))
            .andExpect(jsonPath("$.[*].landingUtm").value(hasItem(DEFAULT_LANDING_UTM)))
            .andExpect(jsonPath("$.[*].pixelLink").value(hasItem(DEFAULT_PIXEL_LINK)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getBannerContent() throws Exception {
        // Initialize the database
        insertedBannerContent = bannerContentRepository.saveAndFlush(bannerContent);

        // Get the bannerContent
        restBannerContentMockMvc
            .perform(get(ENTITY_API_URL_ID, bannerContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bannerContent.getId().intValue()))
            .andExpect(jsonPath("$.bannerTag").value(DEFAULT_BANNER_TAG))
            .andExpect(jsonPath("$.logoPath").value(DEFAULT_LOGO_PATH))
            .andExpect(jsonPath("$.imagePath").value(DEFAULT_IMAGE_PATH))
            .andExpect(jsonPath("$.heading").value(DEFAULT_HEADING))
            .andExpect(jsonPath("$.subHeading").value(DEFAULT_SUB_HEADING))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.landingLink").value(DEFAULT_LANDING_LINK))
            .andExpect(jsonPath("$.landingUtm").value(DEFAULT_LANDING_UTM))
            .andExpect(jsonPath("$.pixelLink").value(DEFAULT_PIXEL_LINK))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBannerContent() throws Exception {
        // Get the bannerContent
        restBannerContentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBannerContent() throws Exception {
        // Initialize the database
        insertedBannerContent = bannerContentRepository.saveAndFlush(bannerContent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bannerContent
        BannerContent updatedBannerContent = bannerContentRepository.findById(bannerContent.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBannerContent are not directly saved in db
        em.detach(updatedBannerContent);
        updatedBannerContent
            .bannerTag(UPDATED_BANNER_TAG)
            .logoPath(UPDATED_LOGO_PATH)
            .imagePath(UPDATED_IMAGE_PATH)
            .heading(UPDATED_HEADING)
            .subHeading(UPDATED_SUB_HEADING)
            .description(UPDATED_DESCRIPTION)
            .landingLink(UPDATED_LANDING_LINK)
            .landingUtm(UPDATED_LANDING_UTM)
            .pixelLink(UPDATED_PIXEL_LINK)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        BannerContentDTO bannerContentDTO = bannerContentMapper.toDto(updatedBannerContent);

        restBannerContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bannerContentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bannerContentDTO))
            )
            .andExpect(status().isOk());

        // Validate the BannerContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBannerContentToMatchAllProperties(updatedBannerContent);
    }

    @Test
    @Transactional
    void putNonExistingBannerContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bannerContent.setId(longCount.incrementAndGet());

        // Create the BannerContent
        BannerContentDTO bannerContentDTO = bannerContentMapper.toDto(bannerContent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBannerContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bannerContentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bannerContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BannerContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBannerContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bannerContent.setId(longCount.incrementAndGet());

        // Create the BannerContent
        BannerContentDTO bannerContentDTO = bannerContentMapper.toDto(bannerContent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBannerContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bannerContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BannerContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBannerContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bannerContent.setId(longCount.incrementAndGet());

        // Create the BannerContent
        BannerContentDTO bannerContentDTO = bannerContentMapper.toDto(bannerContent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBannerContentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bannerContentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BannerContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBannerContentWithPatch() throws Exception {
        // Initialize the database
        insertedBannerContent = bannerContentRepository.saveAndFlush(bannerContent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bannerContent using partial update
        BannerContent partialUpdatedBannerContent = new BannerContent();
        partialUpdatedBannerContent.setId(bannerContent.getId());

        partialUpdatedBannerContent
            .description(UPDATED_DESCRIPTION)
            .landingUtm(UPDATED_LANDING_UTM)
            .endTime(UPDATED_END_TIME)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedTime(UPDATED_UPDATED_TIME);

        restBannerContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBannerContent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBannerContent))
            )
            .andExpect(status().isOk());

        // Validate the BannerContent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBannerContentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBannerContent, bannerContent),
            getPersistedBannerContent(bannerContent)
        );
    }

    @Test
    @Transactional
    void fullUpdateBannerContentWithPatch() throws Exception {
        // Initialize the database
        insertedBannerContent = bannerContentRepository.saveAndFlush(bannerContent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bannerContent using partial update
        BannerContent partialUpdatedBannerContent = new BannerContent();
        partialUpdatedBannerContent.setId(bannerContent.getId());

        partialUpdatedBannerContent
            .bannerTag(UPDATED_BANNER_TAG)
            .logoPath(UPDATED_LOGO_PATH)
            .imagePath(UPDATED_IMAGE_PATH)
            .heading(UPDATED_HEADING)
            .subHeading(UPDATED_SUB_HEADING)
            .description(UPDATED_DESCRIPTION)
            .landingLink(UPDATED_LANDING_LINK)
            .landingUtm(UPDATED_LANDING_UTM)
            .pixelLink(UPDATED_PIXEL_LINK)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restBannerContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBannerContent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBannerContent))
            )
            .andExpect(status().isOk());

        // Validate the BannerContent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBannerContentUpdatableFieldsEquals(partialUpdatedBannerContent, getPersistedBannerContent(partialUpdatedBannerContent));
    }

    @Test
    @Transactional
    void patchNonExistingBannerContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bannerContent.setId(longCount.incrementAndGet());

        // Create the BannerContent
        BannerContentDTO bannerContentDTO = bannerContentMapper.toDto(bannerContent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBannerContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bannerContentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bannerContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BannerContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBannerContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bannerContent.setId(longCount.incrementAndGet());

        // Create the BannerContent
        BannerContentDTO bannerContentDTO = bannerContentMapper.toDto(bannerContent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBannerContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bannerContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BannerContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBannerContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bannerContent.setId(longCount.incrementAndGet());

        // Create the BannerContent
        BannerContentDTO bannerContentDTO = bannerContentMapper.toDto(bannerContent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBannerContentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bannerContentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BannerContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBannerContent() throws Exception {
        // Initialize the database
        insertedBannerContent = bannerContentRepository.saveAndFlush(bannerContent);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bannerContent
        restBannerContentMockMvc
            .perform(delete(ENTITY_API_URL_ID, bannerContent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bannerContentRepository.count();
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

    protected BannerContent getPersistedBannerContent(BannerContent bannerContent) {
        return bannerContentRepository.findById(bannerContent.getId()).orElseThrow();
    }

    protected void assertPersistedBannerContentToMatchAllProperties(BannerContent expectedBannerContent) {
        assertBannerContentAllPropertiesEquals(expectedBannerContent, getPersistedBannerContent(expectedBannerContent));
    }

    protected void assertPersistedBannerContentToMatchUpdatableProperties(BannerContent expectedBannerContent) {
        assertBannerContentAllUpdatablePropertiesEquals(expectedBannerContent, getPersistedBannerContent(expectedBannerContent));
    }
}
