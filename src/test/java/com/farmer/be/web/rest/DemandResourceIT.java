package com.farmer.be.web.rest;

import static com.farmer.be.domain.DemandAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.Demand;
import com.farmer.be.repository.DemandRepository;
import com.farmer.be.service.dto.DemandDTO;
import com.farmer.be.service.mapper.DemandMapper;
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
 * Integration tests for the {@link DemandResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DemandResourceIT {

    private static final LocalDate DEFAULT_DEMAND_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEMAND_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_DEMAND_VALUE = 1F;
    private static final Float UPDATED_DEMAND_VALUE = 2F;

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

    private static final String ENTITY_API_URL = "/api/demands";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DemandRepository demandRepository;

    @Autowired
    private DemandMapper demandMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandMockMvc;

    private Demand demand;

    private Demand insertedDemand;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Demand createEntity() {
        return new Demand()
            .demandDate(DEFAULT_DEMAND_DATE)
            .demandValue(DEFAULT_DEMAND_VALUE)
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
    public static Demand createUpdatedEntity() {
        return new Demand()
            .demandDate(UPDATED_DEMAND_DATE)
            .demandValue(UPDATED_DEMAND_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        demand = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedDemand != null) {
            demandRepository.delete(insertedDemand);
            insertedDemand = null;
        }
    }

    @Test
    @Transactional
    void createDemand() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Demand
        DemandDTO demandDTO = demandMapper.toDto(demand);
        var returnedDemandDTO = om.readValue(
            restDemandMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DemandDTO.class
        );

        // Validate the Demand in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDemand = demandMapper.toEntity(returnedDemandDTO);
        assertDemandUpdatableFieldsEquals(returnedDemand, getPersistedDemand(returnedDemand));

        insertedDemand = returnedDemand;
    }

    @Test
    @Transactional
    void createDemandWithExistingId() throws Exception {
        // Create the Demand with an existing ID
        demand.setId(1L);
        DemandDTO demandDTO = demandMapper.toDto(demand);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Demand in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        demand.setCreateddBy(null);

        // Create the Demand, which fails.
        DemandDTO demandDTO = demandMapper.toDto(demand);

        restDemandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        demand.setCreatedTime(null);

        // Create the Demand, which fails.
        DemandDTO demandDTO = demandMapper.toDto(demand);

        restDemandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        demand.setUpdatedBy(null);

        // Create the Demand, which fails.
        DemandDTO demandDTO = demandMapper.toDto(demand);

        restDemandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        demand.setUpdatedTime(null);

        // Create the Demand, which fails.
        DemandDTO demandDTO = demandMapper.toDto(demand);

        restDemandMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDemands() throws Exception {
        // Initialize the database
        insertedDemand = demandRepository.saveAndFlush(demand);

        // Get all the demandList
        restDemandMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demand.getId().intValue())))
            .andExpect(jsonPath("$.[*].demandDate").value(hasItem(DEFAULT_DEMAND_DATE.toString())))
            .andExpect(jsonPath("$.[*].demandValue").value(hasItem(DEFAULT_DEMAND_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getDemand() throws Exception {
        // Initialize the database
        insertedDemand = demandRepository.saveAndFlush(demand);

        // Get the demand
        restDemandMockMvc
            .perform(get(ENTITY_API_URL_ID, demand.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demand.getId().intValue()))
            .andExpect(jsonPath("$.demandDate").value(DEFAULT_DEMAND_DATE.toString()))
            .andExpect(jsonPath("$.demandValue").value(DEFAULT_DEMAND_VALUE.doubleValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDemand() throws Exception {
        // Get the demand
        restDemandMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDemand() throws Exception {
        // Initialize the database
        insertedDemand = demandRepository.saveAndFlush(demand);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the demand
        Demand updatedDemand = demandRepository.findById(demand.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDemand are not directly saved in db
        em.detach(updatedDemand);
        updatedDemand
            .demandDate(UPDATED_DEMAND_DATE)
            .demandValue(UPDATED_DEMAND_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        DemandDTO demandDTO = demandMapper.toDto(updatedDemand);

        restDemandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandDTO))
            )
            .andExpect(status().isOk());

        // Validate the Demand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDemandToMatchAllProperties(updatedDemand);
    }

    @Test
    @Transactional
    void putNonExistingDemand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demand.setId(longCount.incrementAndGet());

        // Create the Demand
        DemandDTO demandDTO = demandMapper.toDto(demand);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demand.setId(longCount.incrementAndGet());

        // Create the Demand
        DemandDTO demandDTO = demandMapper.toDto(demand);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(demandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demand.setId(longCount.incrementAndGet());

        // Create the Demand
        DemandDTO demandDTO = demandMapper.toDto(demand);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Demand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemandWithPatch() throws Exception {
        // Initialize the database
        insertedDemand = demandRepository.saveAndFlush(demand);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the demand using partial update
        Demand partialUpdatedDemand = new Demand();
        partialUpdatedDemand.setId(demand.getId());

        partialUpdatedDemand.isActive(UPDATED_IS_ACTIVE).createddBy(UPDATED_CREATEDD_BY);

        restDemandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemand.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDemand))
            )
            .andExpect(status().isOk());

        // Validate the Demand in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDemandUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedDemand, demand), getPersistedDemand(demand));
    }

    @Test
    @Transactional
    void fullUpdateDemandWithPatch() throws Exception {
        // Initialize the database
        insertedDemand = demandRepository.saveAndFlush(demand);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the demand using partial update
        Demand partialUpdatedDemand = new Demand();
        partialUpdatedDemand.setId(demand.getId());

        partialUpdatedDemand
            .demandDate(UPDATED_DEMAND_DATE)
            .demandValue(UPDATED_DEMAND_VALUE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restDemandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemand.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDemand))
            )
            .andExpect(status().isOk());

        // Validate the Demand in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDemandUpdatableFieldsEquals(partialUpdatedDemand, getPersistedDemand(partialUpdatedDemand));
    }

    @Test
    @Transactional
    void patchNonExistingDemand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demand.setId(longCount.incrementAndGet());

        // Create the Demand
        DemandDTO demandDTO = demandMapper.toDto(demand);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demandDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(demandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demand.setId(longCount.incrementAndGet());

        // Create the Demand
        DemandDTO demandDTO = demandMapper.toDto(demand);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(demandDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Demand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemand() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demand.setId(longCount.incrementAndGet());

        // Create the Demand
        DemandDTO demandDTO = demandMapper.toDto(demand);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(demandDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Demand in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemand() throws Exception {
        // Initialize the database
        insertedDemand = demandRepository.saveAndFlush(demand);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the demand
        restDemandMockMvc
            .perform(delete(ENTITY_API_URL_ID, demand.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return demandRepository.count();
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

    protected Demand getPersistedDemand(Demand demand) {
        return demandRepository.findById(demand.getId()).orElseThrow();
    }

    protected void assertPersistedDemandToMatchAllProperties(Demand expectedDemand) {
        assertDemandAllPropertiesEquals(expectedDemand, getPersistedDemand(expectedDemand));
    }

    protected void assertPersistedDemandToMatchUpdatableProperties(Demand expectedDemand) {
        assertDemandAllUpdatablePropertiesEquals(expectedDemand, getPersistedDemand(expectedDemand));
    }
}
