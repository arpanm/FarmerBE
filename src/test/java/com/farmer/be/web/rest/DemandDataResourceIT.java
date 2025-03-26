package com.farmer.be.web.rest;

import static com.farmer.be.domain.DemandDataAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.DemandData;
import com.farmer.be.repository.DemandDataRepository;
import com.farmer.be.service.dto.DemandDataDTO;
import com.farmer.be.service.mapper.DemandDataMapper;
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
 * Integration tests for the {@link DemandDataResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DemandDataResourceIT {

    private static final String DEFAULT_FROM_CPC = "AAAAAAAAAA";
    private static final String UPDATED_FROM_CPC = "BBBBBBBBBB";

    private static final String DEFAULT_TO_CC = "AAAAAAAAAA";
    private static final String UPDATED_TO_CC = "BBBBBBBBBB";

    private static final String DEFAULT_P_CODE = "AAAAAAAAAA";
    private static final String UPDATED_P_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ARTICLE = "AAAAAAAAAA";
    private static final String UPDATED_ARTICLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_UOM = "AAAAAAAAAA";
    private static final String UPDATED_UOM = "BBBBBBBBBB";

    private static final Float DEFAULT_NET_WEIGHT_GRAMS = 1F;
    private static final Float UPDATED_NET_WEIGHT_GRAMS = 2F;

    private static final Float DEFAULT_CRATE_SIZE = 1F;
    private static final Float UPDATED_CRATE_SIZE = 2F;

    private static final Float DEFAULT_INDENT_UOM = 1F;
    private static final Float UPDATED_INDENT_UOM = 2F;

    private static final Float DEFAULT_INDENT_KG = 1F;
    private static final Float UPDATED_INDENT_KG = 2F;

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

    private static final String ENTITY_API_URL = "/api/demand-data";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DemandDataRepository demandDataRepository;

    @Autowired
    private DemandDataMapper demandDataMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandDataMockMvc;

    private DemandData demandData;

    private DemandData insertedDemandData;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandData createEntity() {
        return new DemandData()
            .fromCPC(DEFAULT_FROM_CPC)
            .toCC(DEFAULT_TO_CC)
            .pCode(DEFAULT_P_CODE)
            .article(DEFAULT_ARTICLE)
            .description(DEFAULT_DESCRIPTION)
            .uom(DEFAULT_UOM)
            .netWeightGrams(DEFAULT_NET_WEIGHT_GRAMS)
            .crateSize(DEFAULT_CRATE_SIZE)
            .indentUom(DEFAULT_INDENT_UOM)
            .indentKg(DEFAULT_INDENT_KG)
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
    public static DemandData createUpdatedEntity() {
        return new DemandData()
            .fromCPC(UPDATED_FROM_CPC)
            .toCC(UPDATED_TO_CC)
            .pCode(UPDATED_P_CODE)
            .article(UPDATED_ARTICLE)
            .description(UPDATED_DESCRIPTION)
            .uom(UPDATED_UOM)
            .netWeightGrams(UPDATED_NET_WEIGHT_GRAMS)
            .crateSize(UPDATED_CRATE_SIZE)
            .indentUom(UPDATED_INDENT_UOM)
            .indentKg(UPDATED_INDENT_KG)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        demandData = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedDemandData != null) {
            demandDataRepository.delete(insertedDemandData);
            insertedDemandData = null;
        }
    }

    @Test
    @Transactional
    void createDemandData() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DemandData
        DemandDataDTO demandDataDTO = demandDataMapper.toDto(demandData);
        var returnedDemandDataDTO = om.readValue(
            restDemandDataMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandDataDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DemandDataDTO.class
        );

        // Validate the DemandData in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDemandData = demandDataMapper.toEntity(returnedDemandDataDTO);
        assertDemandDataUpdatableFieldsEquals(returnedDemandData, getPersistedDemandData(returnedDemandData));

        insertedDemandData = returnedDemandData;
    }

    @Test
    @Transactional
    void createDemandDataWithExistingId() throws Exception {
        // Create the DemandData with an existing ID
        demandData.setId(1L);
        DemandDataDTO demandDataDTO = demandDataMapper.toDto(demandData);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandDataDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DemandData in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        demandData.setCreateddBy(null);

        // Create the DemandData, which fails.
        DemandDataDTO demandDataDTO = demandDataMapper.toDto(demandData);

        restDemandDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandDataDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        demandData.setCreatedTime(null);

        // Create the DemandData, which fails.
        DemandDataDTO demandDataDTO = demandDataMapper.toDto(demandData);

        restDemandDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandDataDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        demandData.setUpdatedBy(null);

        // Create the DemandData, which fails.
        DemandDataDTO demandDataDTO = demandDataMapper.toDto(demandData);

        restDemandDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandDataDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        demandData.setUpdatedTime(null);

        // Create the DemandData, which fails.
        DemandDataDTO demandDataDTO = demandDataMapper.toDto(demandData);

        restDemandDataMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandDataDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDemandData() throws Exception {
        // Initialize the database
        insertedDemandData = demandDataRepository.saveAndFlush(demandData);

        // Get all the demandDataList
        restDemandDataMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandData.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromCPC").value(hasItem(DEFAULT_FROM_CPC)))
            .andExpect(jsonPath("$.[*].toCC").value(hasItem(DEFAULT_TO_CC)))
            .andExpect(jsonPath("$.[*].pCode").value(hasItem(DEFAULT_P_CODE)))
            .andExpect(jsonPath("$.[*].article").value(hasItem(DEFAULT_ARTICLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].uom").value(hasItem(DEFAULT_UOM)))
            .andExpect(jsonPath("$.[*].netWeightGrams").value(hasItem(DEFAULT_NET_WEIGHT_GRAMS.doubleValue())))
            .andExpect(jsonPath("$.[*].crateSize").value(hasItem(DEFAULT_CRATE_SIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].indentUom").value(hasItem(DEFAULT_INDENT_UOM.doubleValue())))
            .andExpect(jsonPath("$.[*].indentKg").value(hasItem(DEFAULT_INDENT_KG.doubleValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getDemandData() throws Exception {
        // Initialize the database
        insertedDemandData = demandDataRepository.saveAndFlush(demandData);

        // Get the demandData
        restDemandDataMockMvc
            .perform(get(ENTITY_API_URL_ID, demandData.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demandData.getId().intValue()))
            .andExpect(jsonPath("$.fromCPC").value(DEFAULT_FROM_CPC))
            .andExpect(jsonPath("$.toCC").value(DEFAULT_TO_CC))
            .andExpect(jsonPath("$.pCode").value(DEFAULT_P_CODE))
            .andExpect(jsonPath("$.article").value(DEFAULT_ARTICLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.uom").value(DEFAULT_UOM))
            .andExpect(jsonPath("$.netWeightGrams").value(DEFAULT_NET_WEIGHT_GRAMS.doubleValue()))
            .andExpect(jsonPath("$.crateSize").value(DEFAULT_CRATE_SIZE.doubleValue()))
            .andExpect(jsonPath("$.indentUom").value(DEFAULT_INDENT_UOM.doubleValue()))
            .andExpect(jsonPath("$.indentKg").value(DEFAULT_INDENT_KG.doubleValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDemandData() throws Exception {
        // Get the demandData
        restDemandDataMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDemandData() throws Exception {
        // Initialize the database
        insertedDemandData = demandDataRepository.saveAndFlush(demandData);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the demandData
        DemandData updatedDemandData = demandDataRepository.findById(demandData.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDemandData are not directly saved in db
        em.detach(updatedDemandData);
        updatedDemandData
            .fromCPC(UPDATED_FROM_CPC)
            .toCC(UPDATED_TO_CC)
            .pCode(UPDATED_P_CODE)
            .article(UPDATED_ARTICLE)
            .description(UPDATED_DESCRIPTION)
            .uom(UPDATED_UOM)
            .netWeightGrams(UPDATED_NET_WEIGHT_GRAMS)
            .crateSize(UPDATED_CRATE_SIZE)
            .indentUom(UPDATED_INDENT_UOM)
            .indentKg(UPDATED_INDENT_KG)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        DemandDataDTO demandDataDTO = demandDataMapper.toDto(updatedDemandData);

        restDemandDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(demandDataDTO))
            )
            .andExpect(status().isOk());

        // Validate the DemandData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDemandDataToMatchAllProperties(updatedDemandData);
    }

    @Test
    @Transactional
    void putNonExistingDemandData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demandData.setId(longCount.incrementAndGet());

        // Create the DemandData
        DemandDataDTO demandDataDTO = demandDataMapper.toDto(demandData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandDataDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(demandDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemandData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demandData.setId(longCount.incrementAndGet());

        // Create the DemandData
        DemandDataDTO demandDataDTO = demandDataMapper.toDto(demandData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandDataMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(demandDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemandData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demandData.setId(longCount.incrementAndGet());

        // Create the DemandData
        DemandDataDTO demandDataDTO = demandDataMapper.toDto(demandData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandDataMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandDataDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemandDataWithPatch() throws Exception {
        // Initialize the database
        insertedDemandData = demandDataRepository.saveAndFlush(demandData);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the demandData using partial update
        DemandData partialUpdatedDemandData = new DemandData();
        partialUpdatedDemandData.setId(demandData.getId());

        partialUpdatedDemandData
            .fromCPC(UPDATED_FROM_CPC)
            .pCode(UPDATED_P_CODE)
            .article(UPDATED_ARTICLE)
            .uom(UPDATED_UOM)
            .crateSize(UPDATED_CRATE_SIZE)
            .indentKg(UPDATED_INDENT_KG)
            .isActive(UPDATED_IS_ACTIVE);

        restDemandDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandData.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDemandData))
            )
            .andExpect(status().isOk());

        // Validate the DemandData in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDemandDataUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDemandData, demandData),
            getPersistedDemandData(demandData)
        );
    }

    @Test
    @Transactional
    void fullUpdateDemandDataWithPatch() throws Exception {
        // Initialize the database
        insertedDemandData = demandDataRepository.saveAndFlush(demandData);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the demandData using partial update
        DemandData partialUpdatedDemandData = new DemandData();
        partialUpdatedDemandData.setId(demandData.getId());

        partialUpdatedDemandData
            .fromCPC(UPDATED_FROM_CPC)
            .toCC(UPDATED_TO_CC)
            .pCode(UPDATED_P_CODE)
            .article(UPDATED_ARTICLE)
            .description(UPDATED_DESCRIPTION)
            .uom(UPDATED_UOM)
            .netWeightGrams(UPDATED_NET_WEIGHT_GRAMS)
            .crateSize(UPDATED_CRATE_SIZE)
            .indentUom(UPDATED_INDENT_UOM)
            .indentKg(UPDATED_INDENT_KG)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restDemandDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandData.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDemandData))
            )
            .andExpect(status().isOk());

        // Validate the DemandData in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDemandDataUpdatableFieldsEquals(partialUpdatedDemandData, getPersistedDemandData(partialUpdatedDemandData));
    }

    @Test
    @Transactional
    void patchNonExistingDemandData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demandData.setId(longCount.incrementAndGet());

        // Create the DemandData
        DemandDataDTO demandDataDTO = demandDataMapper.toDto(demandData);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demandDataDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(demandDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemandData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demandData.setId(longCount.incrementAndGet());

        // Create the DemandData
        DemandDataDTO demandDataDTO = demandDataMapper.toDto(demandData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandDataMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(demandDataDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemandData() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demandData.setId(longCount.incrementAndGet());

        // Create the DemandData
        DemandDataDTO demandDataDTO = demandDataMapper.toDto(demandData);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandDataMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(demandDataDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandData in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemandData() throws Exception {
        // Initialize the database
        insertedDemandData = demandDataRepository.saveAndFlush(demandData);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the demandData
        restDemandDataMockMvc
            .perform(delete(ENTITY_API_URL_ID, demandData.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return demandDataRepository.count();
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

    protected DemandData getPersistedDemandData(DemandData demandData) {
        return demandDataRepository.findById(demandData.getId()).orElseThrow();
    }

    protected void assertPersistedDemandDataToMatchAllProperties(DemandData expectedDemandData) {
        assertDemandDataAllPropertiesEquals(expectedDemandData, getPersistedDemandData(expectedDemandData));
    }

    protected void assertPersistedDemandDataToMatchUpdatableProperties(DemandData expectedDemandData) {
        assertDemandDataAllUpdatablePropertiesEquals(expectedDemandData, getPersistedDemandData(expectedDemandData));
    }
}
