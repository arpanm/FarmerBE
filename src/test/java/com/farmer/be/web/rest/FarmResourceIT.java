package com.farmer.be.web.rest;

import static com.farmer.be.domain.FarmAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.Farm;
import com.farmer.be.domain.enumeration.FarmType;
import com.farmer.be.repository.FarmRepository;
import com.farmer.be.service.FarmService;
import com.farmer.be.service.dto.FarmDTO;
import com.farmer.be.service.mapper.FarmMapper;
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
 * Integration tests for the {@link FarmResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FarmResourceIT {

    private static final FarmType DEFAULT_FARM_TYPE = FarmType.Own;
    private static final FarmType UPDATED_FARM_TYPE = FarmType.Family;

    private static final String DEFAULT_OWNER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIONSHIP_WITH_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_RELATIONSHIP_WITH_OWNER = "BBBBBBBBBB";

    private static final Float DEFAULT_AREA_IN_ACRES = 1F;
    private static final Float UPDATED_AREA_IN_ACRES = 2F;

    private static final String DEFAULT_FARM_DOCUMENT_NO = "AAAAAAAAAA";
    private static final String UPDATED_FARM_DOCUMENT_NO = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/farms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FarmRepository farmRepository;

    @Mock
    private FarmRepository farmRepositoryMock;

    @Autowired
    private FarmMapper farmMapper;

    @Mock
    private FarmService farmServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFarmMockMvc;

    private Farm farm;

    private Farm insertedFarm;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Farm createEntity() {
        return new Farm()
            .farmType(DEFAULT_FARM_TYPE)
            .ownerName(DEFAULT_OWNER_NAME)
            .relationshipWithOwner(DEFAULT_RELATIONSHIP_WITH_OWNER)
            .areaInAcres(DEFAULT_AREA_IN_ACRES)
            .farmDocumentNo(DEFAULT_FARM_DOCUMENT_NO)
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
    public static Farm createUpdatedEntity() {
        return new Farm()
            .farmType(UPDATED_FARM_TYPE)
            .ownerName(UPDATED_OWNER_NAME)
            .relationshipWithOwner(UPDATED_RELATIONSHIP_WITH_OWNER)
            .areaInAcres(UPDATED_AREA_IN_ACRES)
            .farmDocumentNo(UPDATED_FARM_DOCUMENT_NO)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        farm = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedFarm != null) {
            farmRepository.delete(insertedFarm);
            insertedFarm = null;
        }
    }

    @Test
    @Transactional
    void createFarm() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Farm
        FarmDTO farmDTO = farmMapper.toDto(farm);
        var returnedFarmDTO = om.readValue(
            restFarmMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(farmDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FarmDTO.class
        );

        // Validate the Farm in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFarm = farmMapper.toEntity(returnedFarmDTO);
        assertFarmUpdatableFieldsEquals(returnedFarm, getPersistedFarm(returnedFarm));

        insertedFarm = returnedFarm;
    }

    @Test
    @Transactional
    void createFarmWithExistingId() throws Exception {
        // Create the Farm with an existing ID
        farm.setId(1L);
        FarmDTO farmDTO = farmMapper.toDto(farm);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFarmMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(farmDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Farm in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        farm.setCreateddBy(null);

        // Create the Farm, which fails.
        FarmDTO farmDTO = farmMapper.toDto(farm);

        restFarmMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(farmDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        farm.setCreatedTime(null);

        // Create the Farm, which fails.
        FarmDTO farmDTO = farmMapper.toDto(farm);

        restFarmMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(farmDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        farm.setUpdatedBy(null);

        // Create the Farm, which fails.
        FarmDTO farmDTO = farmMapper.toDto(farm);

        restFarmMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(farmDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        farm.setUpdatedTime(null);

        // Create the Farm, which fails.
        FarmDTO farmDTO = farmMapper.toDto(farm);

        restFarmMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(farmDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFarms() throws Exception {
        // Initialize the database
        insertedFarm = farmRepository.saveAndFlush(farm);

        // Get all the farmList
        restFarmMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(farm.getId().intValue())))
            .andExpect(jsonPath("$.[*].farmType").value(hasItem(DEFAULT_FARM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].ownerName").value(hasItem(DEFAULT_OWNER_NAME)))
            .andExpect(jsonPath("$.[*].relationshipWithOwner").value(hasItem(DEFAULT_RELATIONSHIP_WITH_OWNER)))
            .andExpect(jsonPath("$.[*].areaInAcres").value(hasItem(DEFAULT_AREA_IN_ACRES.doubleValue())))
            .andExpect(jsonPath("$.[*].farmDocumentNo").value(hasItem(DEFAULT_FARM_DOCUMENT_NO)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFarmsWithEagerRelationshipsIsEnabled() throws Exception {
        when(farmServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFarmMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(farmServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFarmsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(farmServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFarmMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(farmRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getFarm() throws Exception {
        // Initialize the database
        insertedFarm = farmRepository.saveAndFlush(farm);

        // Get the farm
        restFarmMockMvc
            .perform(get(ENTITY_API_URL_ID, farm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(farm.getId().intValue()))
            .andExpect(jsonPath("$.farmType").value(DEFAULT_FARM_TYPE.toString()))
            .andExpect(jsonPath("$.ownerName").value(DEFAULT_OWNER_NAME))
            .andExpect(jsonPath("$.relationshipWithOwner").value(DEFAULT_RELATIONSHIP_WITH_OWNER))
            .andExpect(jsonPath("$.areaInAcres").value(DEFAULT_AREA_IN_ACRES.doubleValue()))
            .andExpect(jsonPath("$.farmDocumentNo").value(DEFAULT_FARM_DOCUMENT_NO))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFarm() throws Exception {
        // Get the farm
        restFarmMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFarm() throws Exception {
        // Initialize the database
        insertedFarm = farmRepository.saveAndFlush(farm);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the farm
        Farm updatedFarm = farmRepository.findById(farm.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFarm are not directly saved in db
        em.detach(updatedFarm);
        updatedFarm
            .farmType(UPDATED_FARM_TYPE)
            .ownerName(UPDATED_OWNER_NAME)
            .relationshipWithOwner(UPDATED_RELATIONSHIP_WITH_OWNER)
            .areaInAcres(UPDATED_AREA_IN_ACRES)
            .farmDocumentNo(UPDATED_FARM_DOCUMENT_NO)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        FarmDTO farmDTO = farmMapper.toDto(updatedFarm);

        restFarmMockMvc
            .perform(put(ENTITY_API_URL_ID, farmDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(farmDTO)))
            .andExpect(status().isOk());

        // Validate the Farm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFarmToMatchAllProperties(updatedFarm);
    }

    @Test
    @Transactional
    void putNonExistingFarm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        farm.setId(longCount.incrementAndGet());

        // Create the Farm
        FarmDTO farmDTO = farmMapper.toDto(farm);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFarmMockMvc
            .perform(put(ENTITY_API_URL_ID, farmDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(farmDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Farm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFarm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        farm.setId(longCount.incrementAndGet());

        // Create the Farm
        FarmDTO farmDTO = farmMapper.toDto(farm);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFarmMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(farmDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Farm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFarm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        farm.setId(longCount.incrementAndGet());

        // Create the Farm
        FarmDTO farmDTO = farmMapper.toDto(farm);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFarmMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(farmDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Farm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFarmWithPatch() throws Exception {
        // Initialize the database
        insertedFarm = farmRepository.saveAndFlush(farm);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the farm using partial update
        Farm partialUpdatedFarm = new Farm();
        partialUpdatedFarm.setId(farm.getId());

        partialUpdatedFarm.farmType(UPDATED_FARM_TYPE).areaInAcres(UPDATED_AREA_IN_ACRES).createdTime(UPDATED_CREATED_TIME);

        restFarmMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFarm.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFarm))
            )
            .andExpect(status().isOk());

        // Validate the Farm in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFarmUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedFarm, farm), getPersistedFarm(farm));
    }

    @Test
    @Transactional
    void fullUpdateFarmWithPatch() throws Exception {
        // Initialize the database
        insertedFarm = farmRepository.saveAndFlush(farm);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the farm using partial update
        Farm partialUpdatedFarm = new Farm();
        partialUpdatedFarm.setId(farm.getId());

        partialUpdatedFarm
            .farmType(UPDATED_FARM_TYPE)
            .ownerName(UPDATED_OWNER_NAME)
            .relationshipWithOwner(UPDATED_RELATIONSHIP_WITH_OWNER)
            .areaInAcres(UPDATED_AREA_IN_ACRES)
            .farmDocumentNo(UPDATED_FARM_DOCUMENT_NO)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restFarmMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFarm.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFarm))
            )
            .andExpect(status().isOk());

        // Validate the Farm in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFarmUpdatableFieldsEquals(partialUpdatedFarm, getPersistedFarm(partialUpdatedFarm));
    }

    @Test
    @Transactional
    void patchNonExistingFarm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        farm.setId(longCount.incrementAndGet());

        // Create the Farm
        FarmDTO farmDTO = farmMapper.toDto(farm);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFarmMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, farmDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(farmDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Farm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFarm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        farm.setId(longCount.incrementAndGet());

        // Create the Farm
        FarmDTO farmDTO = farmMapper.toDto(farm);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFarmMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(farmDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Farm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFarm() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        farm.setId(longCount.incrementAndGet());

        // Create the Farm
        FarmDTO farmDTO = farmMapper.toDto(farm);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFarmMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(farmDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Farm in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFarm() throws Exception {
        // Initialize the database
        insertedFarm = farmRepository.saveAndFlush(farm);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the farm
        restFarmMockMvc
            .perform(delete(ENTITY_API_URL_ID, farm.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return farmRepository.count();
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

    protected Farm getPersistedFarm(Farm farm) {
        return farmRepository.findById(farm.getId()).orElseThrow();
    }

    protected void assertPersistedFarmToMatchAllProperties(Farm expectedFarm) {
        assertFarmAllPropertiesEquals(expectedFarm, getPersistedFarm(expectedFarm));
    }

    protected void assertPersistedFarmToMatchUpdatableProperties(Farm expectedFarm) {
        assertFarmAllUpdatablePropertiesEquals(expectedFarm, getPersistedFarm(expectedFarm));
    }
}
