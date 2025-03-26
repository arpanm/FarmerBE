package com.farmer.be.web.rest;

import static com.farmer.be.domain.CollectionCenterAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.CollectionCenter;
import com.farmer.be.repository.CollectionCenterRepository;
import com.farmer.be.service.CollectionCenterService;
import com.farmer.be.service.dto.CollectionCenterDTO;
import com.farmer.be.service.mapper.CollectionCenterMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CollectionCenterResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CollectionCenterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CC_ID = "AAAAAAAAAA";
    private static final String UPDATED_CC_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FFDC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_FFDC_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_FFDC_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FFDC_NAME = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/collection-centers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CollectionCenterRepository collectionCenterRepository;

    @Mock
    private CollectionCenterRepository collectionCenterRepositoryMock;

    @Autowired
    private CollectionCenterMapper collectionCenterMapper;

    @Mock
    private CollectionCenterService collectionCenterServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCollectionCenterMockMvc;

    private CollectionCenter collectionCenter;

    private CollectionCenter insertedCollectionCenter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CollectionCenter createEntity() {
        return new CollectionCenter()
            .name(DEFAULT_NAME)
            .ccId(DEFAULT_CC_ID)
            .ffdcCode(DEFAULT_FFDC_CODE)
            .ffdcName(DEFAULT_FFDC_NAME)
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
    public static CollectionCenter createUpdatedEntity() {
        return new CollectionCenter()
            .name(UPDATED_NAME)
            .ccId(UPDATED_CC_ID)
            .ffdcCode(UPDATED_FFDC_CODE)
            .ffdcName(UPDATED_FFDC_NAME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        collectionCenter = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedCollectionCenter != null) {
            collectionCenterRepository.delete(insertedCollectionCenter);
            insertedCollectionCenter = null;
        }
    }

    @Test
    @Transactional
    void createCollectionCenter() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CollectionCenter
        CollectionCenterDTO collectionCenterDTO = collectionCenterMapper.toDto(collectionCenter);
        var returnedCollectionCenterDTO = om.readValue(
            restCollectionCenterMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(collectionCenterDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CollectionCenterDTO.class
        );

        // Validate the CollectionCenter in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCollectionCenter = collectionCenterMapper.toEntity(returnedCollectionCenterDTO);
        assertCollectionCenterUpdatableFieldsEquals(returnedCollectionCenter, getPersistedCollectionCenter(returnedCollectionCenter));

        insertedCollectionCenter = returnedCollectionCenter;
    }

    @Test
    @Transactional
    void createCollectionCenterWithExistingId() throws Exception {
        // Create the CollectionCenter with an existing ID
        collectionCenter.setId(1L);
        CollectionCenterDTO collectionCenterDTO = collectionCenterMapper.toDto(collectionCenter);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollectionCenterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(collectionCenterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CollectionCenter in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        collectionCenter.setCreateddBy(null);

        // Create the CollectionCenter, which fails.
        CollectionCenterDTO collectionCenterDTO = collectionCenterMapper.toDto(collectionCenter);

        restCollectionCenterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(collectionCenterDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        collectionCenter.setCreatedTime(null);

        // Create the CollectionCenter, which fails.
        CollectionCenterDTO collectionCenterDTO = collectionCenterMapper.toDto(collectionCenter);

        restCollectionCenterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(collectionCenterDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        collectionCenter.setUpdatedBy(null);

        // Create the CollectionCenter, which fails.
        CollectionCenterDTO collectionCenterDTO = collectionCenterMapper.toDto(collectionCenter);

        restCollectionCenterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(collectionCenterDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        collectionCenter.setUpdatedTime(null);

        // Create the CollectionCenter, which fails.
        CollectionCenterDTO collectionCenterDTO = collectionCenterMapper.toDto(collectionCenter);

        restCollectionCenterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(collectionCenterDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCollectionCenters() throws Exception {
        // Initialize the database
        insertedCollectionCenter = collectionCenterRepository.saveAndFlush(collectionCenter);

        // Get all the collectionCenterList
        restCollectionCenterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collectionCenter.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].ccId").value(hasItem(DEFAULT_CC_ID)))
            .andExpect(jsonPath("$.[*].ffdcCode").value(hasItem(DEFAULT_FFDC_CODE)))
            .andExpect(jsonPath("$.[*].ffdcName").value(hasItem(DEFAULT_FFDC_NAME)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCollectionCentersWithEagerRelationshipsIsEnabled() throws Exception {
        when(collectionCenterServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCollectionCenterMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(collectionCenterServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCollectionCentersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(collectionCenterServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCollectionCenterMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(collectionCenterRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCollectionCenter() throws Exception {
        // Initialize the database
        insertedCollectionCenter = collectionCenterRepository.saveAndFlush(collectionCenter);

        // Get the collectionCenter
        restCollectionCenterMockMvc
            .perform(get(ENTITY_API_URL_ID, collectionCenter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(collectionCenter.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.ccId").value(DEFAULT_CC_ID))
            .andExpect(jsonPath("$.ffdcCode").value(DEFAULT_FFDC_CODE))
            .andExpect(jsonPath("$.ffdcName").value(DEFAULT_FFDC_NAME))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCollectionCenter() throws Exception {
        // Get the collectionCenter
        restCollectionCenterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCollectionCenter() throws Exception {
        // Initialize the database
        insertedCollectionCenter = collectionCenterRepository.saveAndFlush(collectionCenter);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the collectionCenter
        CollectionCenter updatedCollectionCenter = collectionCenterRepository.findById(collectionCenter.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCollectionCenter are not directly saved in db
        em.detach(updatedCollectionCenter);
        updatedCollectionCenter
            .name(UPDATED_NAME)
            .ccId(UPDATED_CC_ID)
            .ffdcCode(UPDATED_FFDC_CODE)
            .ffdcName(UPDATED_FFDC_NAME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        CollectionCenterDTO collectionCenterDTO = collectionCenterMapper.toDto(updatedCollectionCenter);

        restCollectionCenterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, collectionCenterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(collectionCenterDTO))
            )
            .andExpect(status().isOk());

        // Validate the CollectionCenter in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCollectionCenterToMatchAllProperties(updatedCollectionCenter);
    }

    @Test
    @Transactional
    void putNonExistingCollectionCenter() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        collectionCenter.setId(longCount.incrementAndGet());

        // Create the CollectionCenter
        CollectionCenterDTO collectionCenterDTO = collectionCenterMapper.toDto(collectionCenter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollectionCenterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, collectionCenterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(collectionCenterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CollectionCenter in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCollectionCenter() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        collectionCenter.setId(longCount.incrementAndGet());

        // Create the CollectionCenter
        CollectionCenterDTO collectionCenterDTO = collectionCenterMapper.toDto(collectionCenter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollectionCenterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(collectionCenterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CollectionCenter in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCollectionCenter() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        collectionCenter.setId(longCount.incrementAndGet());

        // Create the CollectionCenter
        CollectionCenterDTO collectionCenterDTO = collectionCenterMapper.toDto(collectionCenter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollectionCenterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(collectionCenterDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CollectionCenter in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCollectionCenterWithPatch() throws Exception {
        // Initialize the database
        insertedCollectionCenter = collectionCenterRepository.saveAndFlush(collectionCenter);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the collectionCenter using partial update
        CollectionCenter partialUpdatedCollectionCenter = new CollectionCenter();
        partialUpdatedCollectionCenter.setId(collectionCenter.getId());

        partialUpdatedCollectionCenter
            .name(UPDATED_NAME)
            .ffdcCode(UPDATED_FFDC_CODE)
            .createddBy(UPDATED_CREATEDD_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restCollectionCenterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollectionCenter.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCollectionCenter))
            )
            .andExpect(status().isOk());

        // Validate the CollectionCenter in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCollectionCenterUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCollectionCenter, collectionCenter),
            getPersistedCollectionCenter(collectionCenter)
        );
    }

    @Test
    @Transactional
    void fullUpdateCollectionCenterWithPatch() throws Exception {
        // Initialize the database
        insertedCollectionCenter = collectionCenterRepository.saveAndFlush(collectionCenter);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the collectionCenter using partial update
        CollectionCenter partialUpdatedCollectionCenter = new CollectionCenter();
        partialUpdatedCollectionCenter.setId(collectionCenter.getId());

        partialUpdatedCollectionCenter
            .name(UPDATED_NAME)
            .ccId(UPDATED_CC_ID)
            .ffdcCode(UPDATED_FFDC_CODE)
            .ffdcName(UPDATED_FFDC_NAME)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restCollectionCenterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollectionCenter.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCollectionCenter))
            )
            .andExpect(status().isOk());

        // Validate the CollectionCenter in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCollectionCenterUpdatableFieldsEquals(
            partialUpdatedCollectionCenter,
            getPersistedCollectionCenter(partialUpdatedCollectionCenter)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCollectionCenter() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        collectionCenter.setId(longCount.incrementAndGet());

        // Create the CollectionCenter
        CollectionCenterDTO collectionCenterDTO = collectionCenterMapper.toDto(collectionCenter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollectionCenterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, collectionCenterDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(collectionCenterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CollectionCenter in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCollectionCenter() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        collectionCenter.setId(longCount.incrementAndGet());

        // Create the CollectionCenter
        CollectionCenterDTO collectionCenterDTO = collectionCenterMapper.toDto(collectionCenter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollectionCenterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(collectionCenterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CollectionCenter in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCollectionCenter() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        collectionCenter.setId(longCount.incrementAndGet());

        // Create the CollectionCenter
        CollectionCenterDTO collectionCenterDTO = collectionCenterMapper.toDto(collectionCenter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollectionCenterMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(collectionCenterDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CollectionCenter in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCollectionCenter() throws Exception {
        // Initialize the database
        insertedCollectionCenter = collectionCenterRepository.saveAndFlush(collectionCenter);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the collectionCenter
        restCollectionCenterMockMvc
            .perform(delete(ENTITY_API_URL_ID, collectionCenter.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return collectionCenterRepository.count();
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

    protected CollectionCenter getPersistedCollectionCenter(CollectionCenter collectionCenter) {
        return collectionCenterRepository.findById(collectionCenter.getId()).orElseThrow();
    }

    protected void assertPersistedCollectionCenterToMatchAllProperties(CollectionCenter expectedCollectionCenter) {
        assertCollectionCenterAllPropertiesEquals(expectedCollectionCenter, getPersistedCollectionCenter(expectedCollectionCenter));
    }

    protected void assertPersistedCollectionCenterToMatchUpdatableProperties(CollectionCenter expectedCollectionCenter) {
        assertCollectionCenterAllUpdatablePropertiesEquals(
            expectedCollectionCenter,
            getPersistedCollectionCenter(expectedCollectionCenter)
        );
    }
}
