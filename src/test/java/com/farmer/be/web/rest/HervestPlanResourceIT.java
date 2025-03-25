package com.farmer.be.web.rest;

import static com.farmer.be.domain.HervestPlanAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.HervestPlan;
import com.farmer.be.repository.HervestPlanRepository;
import com.farmer.be.service.dto.HervestPlanDTO;
import com.farmer.be.service.mapper.HervestPlanMapper;
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
 * Integration tests for the {@link HervestPlanResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HervestPlanResourceIT {

    private static final LocalDate DEFAULT_HERVEST_PLAN_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_HERVEST_PLAN_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_HERVEST_PLAN_VALUE = 1F;
    private static final Float UPDATED_HERVEST_PLAN_VALUE = 2F;

    private static final Float DEFAULT_HERVEST_PLAN_VALUE_MIN = 1F;
    private static final Float UPDATED_HERVEST_PLAN_VALUE_MIN = 2F;

    private static final Float DEFAULT_HERVEST_PLAN_VALUE_MAX = 1F;
    private static final Float UPDATED_HERVEST_PLAN_VALUE_MAX = 2F;

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

    private static final String ENTITY_API_URL = "/api/hervest-plans";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HervestPlanRepository hervestPlanRepository;

    @Autowired
    private HervestPlanMapper hervestPlanMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHervestPlanMockMvc;

    private HervestPlan hervestPlan;

    private HervestPlan insertedHervestPlan;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HervestPlan createEntity() {
        return new HervestPlan()
            .hervestPlanDate(DEFAULT_HERVEST_PLAN_DATE)
            .hervestPlanValue(DEFAULT_HERVEST_PLAN_VALUE)
            .hervestPlanValueMin(DEFAULT_HERVEST_PLAN_VALUE_MIN)
            .hervestPlanValueMax(DEFAULT_HERVEST_PLAN_VALUE_MAX)
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
    public static HervestPlan createUpdatedEntity() {
        return new HervestPlan()
            .hervestPlanDate(UPDATED_HERVEST_PLAN_DATE)
            .hervestPlanValue(UPDATED_HERVEST_PLAN_VALUE)
            .hervestPlanValueMin(UPDATED_HERVEST_PLAN_VALUE_MIN)
            .hervestPlanValueMax(UPDATED_HERVEST_PLAN_VALUE_MAX)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        hervestPlan = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedHervestPlan != null) {
            hervestPlanRepository.delete(insertedHervestPlan);
            insertedHervestPlan = null;
        }
    }

    @Test
    @Transactional
    void createHervestPlan() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the HervestPlan
        HervestPlanDTO hervestPlanDTO = hervestPlanMapper.toDto(hervestPlan);
        var returnedHervestPlanDTO = om.readValue(
            restHervestPlanMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hervestPlanDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            HervestPlanDTO.class
        );

        // Validate the HervestPlan in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedHervestPlan = hervestPlanMapper.toEntity(returnedHervestPlanDTO);
        assertHervestPlanUpdatableFieldsEquals(returnedHervestPlan, getPersistedHervestPlan(returnedHervestPlan));

        insertedHervestPlan = returnedHervestPlan;
    }

    @Test
    @Transactional
    void createHervestPlanWithExistingId() throws Exception {
        // Create the HervestPlan with an existing ID
        hervestPlan.setId(1L);
        HervestPlanDTO hervestPlanDTO = hervestPlanMapper.toDto(hervestPlan);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHervestPlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hervestPlanDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HervestPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hervestPlan.setCreateddBy(null);

        // Create the HervestPlan, which fails.
        HervestPlanDTO hervestPlanDTO = hervestPlanMapper.toDto(hervestPlan);

        restHervestPlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hervestPlanDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hervestPlan.setCreatedTime(null);

        // Create the HervestPlan, which fails.
        HervestPlanDTO hervestPlanDTO = hervestPlanMapper.toDto(hervestPlan);

        restHervestPlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hervestPlanDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hervestPlan.setUpdatedBy(null);

        // Create the HervestPlan, which fails.
        HervestPlanDTO hervestPlanDTO = hervestPlanMapper.toDto(hervestPlan);

        restHervestPlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hervestPlanDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hervestPlan.setUpdatedTime(null);

        // Create the HervestPlan, which fails.
        HervestPlanDTO hervestPlanDTO = hervestPlanMapper.toDto(hervestPlan);

        restHervestPlanMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hervestPlanDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHervestPlans() throws Exception {
        // Initialize the database
        insertedHervestPlan = hervestPlanRepository.saveAndFlush(hervestPlan);

        // Get all the hervestPlanList
        restHervestPlanMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hervestPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].hervestPlanDate").value(hasItem(DEFAULT_HERVEST_PLAN_DATE.toString())))
            .andExpect(jsonPath("$.[*].hervestPlanValue").value(hasItem(DEFAULT_HERVEST_PLAN_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].hervestPlanValueMin").value(hasItem(DEFAULT_HERVEST_PLAN_VALUE_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].hervestPlanValueMax").value(hasItem(DEFAULT_HERVEST_PLAN_VALUE_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getHervestPlan() throws Exception {
        // Initialize the database
        insertedHervestPlan = hervestPlanRepository.saveAndFlush(hervestPlan);

        // Get the hervestPlan
        restHervestPlanMockMvc
            .perform(get(ENTITY_API_URL_ID, hervestPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hervestPlan.getId().intValue()))
            .andExpect(jsonPath("$.hervestPlanDate").value(DEFAULT_HERVEST_PLAN_DATE.toString()))
            .andExpect(jsonPath("$.hervestPlanValue").value(DEFAULT_HERVEST_PLAN_VALUE.doubleValue()))
            .andExpect(jsonPath("$.hervestPlanValueMin").value(DEFAULT_HERVEST_PLAN_VALUE_MIN.doubleValue()))
            .andExpect(jsonPath("$.hervestPlanValueMax").value(DEFAULT_HERVEST_PLAN_VALUE_MAX.doubleValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingHervestPlan() throws Exception {
        // Get the hervestPlan
        restHervestPlanMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHervestPlan() throws Exception {
        // Initialize the database
        insertedHervestPlan = hervestPlanRepository.saveAndFlush(hervestPlan);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hervestPlan
        HervestPlan updatedHervestPlan = hervestPlanRepository.findById(hervestPlan.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHervestPlan are not directly saved in db
        em.detach(updatedHervestPlan);
        updatedHervestPlan
            .hervestPlanDate(UPDATED_HERVEST_PLAN_DATE)
            .hervestPlanValue(UPDATED_HERVEST_PLAN_VALUE)
            .hervestPlanValueMin(UPDATED_HERVEST_PLAN_VALUE_MIN)
            .hervestPlanValueMax(UPDATED_HERVEST_PLAN_VALUE_MAX)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        HervestPlanDTO hervestPlanDTO = hervestPlanMapper.toDto(updatedHervestPlan);

        restHervestPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hervestPlanDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hervestPlanDTO))
            )
            .andExpect(status().isOk());

        // Validate the HervestPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHervestPlanToMatchAllProperties(updatedHervestPlan);
    }

    @Test
    @Transactional
    void putNonExistingHervestPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hervestPlan.setId(longCount.incrementAndGet());

        // Create the HervestPlan
        HervestPlanDTO hervestPlanDTO = hervestPlanMapper.toDto(hervestPlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHervestPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hervestPlanDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hervestPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HervestPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHervestPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hervestPlan.setId(longCount.incrementAndGet());

        // Create the HervestPlan
        HervestPlanDTO hervestPlanDTO = hervestPlanMapper.toDto(hervestPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHervestPlanMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hervestPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HervestPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHervestPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hervestPlan.setId(longCount.incrementAndGet());

        // Create the HervestPlan
        HervestPlanDTO hervestPlanDTO = hervestPlanMapper.toDto(hervestPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHervestPlanMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hervestPlanDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HervestPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHervestPlanWithPatch() throws Exception {
        // Initialize the database
        insertedHervestPlan = hervestPlanRepository.saveAndFlush(hervestPlan);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hervestPlan using partial update
        HervestPlan partialUpdatedHervestPlan = new HervestPlan();
        partialUpdatedHervestPlan.setId(hervestPlan.getId());

        restHervestPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHervestPlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHervestPlan))
            )
            .andExpect(status().isOk());

        // Validate the HervestPlan in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHervestPlanUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHervestPlan, hervestPlan),
            getPersistedHervestPlan(hervestPlan)
        );
    }

    @Test
    @Transactional
    void fullUpdateHervestPlanWithPatch() throws Exception {
        // Initialize the database
        insertedHervestPlan = hervestPlanRepository.saveAndFlush(hervestPlan);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hervestPlan using partial update
        HervestPlan partialUpdatedHervestPlan = new HervestPlan();
        partialUpdatedHervestPlan.setId(hervestPlan.getId());

        partialUpdatedHervestPlan
            .hervestPlanDate(UPDATED_HERVEST_PLAN_DATE)
            .hervestPlanValue(UPDATED_HERVEST_PLAN_VALUE)
            .hervestPlanValueMin(UPDATED_HERVEST_PLAN_VALUE_MIN)
            .hervestPlanValueMax(UPDATED_HERVEST_PLAN_VALUE_MAX)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restHervestPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHervestPlan.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHervestPlan))
            )
            .andExpect(status().isOk());

        // Validate the HervestPlan in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHervestPlanUpdatableFieldsEquals(partialUpdatedHervestPlan, getPersistedHervestPlan(partialUpdatedHervestPlan));
    }

    @Test
    @Transactional
    void patchNonExistingHervestPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hervestPlan.setId(longCount.incrementAndGet());

        // Create the HervestPlan
        HervestPlanDTO hervestPlanDTO = hervestPlanMapper.toDto(hervestPlan);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHervestPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hervestPlanDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hervestPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HervestPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHervestPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hervestPlan.setId(longCount.incrementAndGet());

        // Create the HervestPlan
        HervestPlanDTO hervestPlanDTO = hervestPlanMapper.toDto(hervestPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHervestPlanMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hervestPlanDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HervestPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHervestPlan() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hervestPlan.setId(longCount.incrementAndGet());

        // Create the HervestPlan
        HervestPlanDTO hervestPlanDTO = hervestPlanMapper.toDto(hervestPlan);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHervestPlanMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hervestPlanDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HervestPlan in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHervestPlan() throws Exception {
        // Initialize the database
        insertedHervestPlan = hervestPlanRepository.saveAndFlush(hervestPlan);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the hervestPlan
        restHervestPlanMockMvc
            .perform(delete(ENTITY_API_URL_ID, hervestPlan.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return hervestPlanRepository.count();
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

    protected HervestPlan getPersistedHervestPlan(HervestPlan hervestPlan) {
        return hervestPlanRepository.findById(hervestPlan.getId()).orElseThrow();
    }

    protected void assertPersistedHervestPlanToMatchAllProperties(HervestPlan expectedHervestPlan) {
        assertHervestPlanAllPropertiesEquals(expectedHervestPlan, getPersistedHervestPlan(expectedHervestPlan));
    }

    protected void assertPersistedHervestPlanToMatchUpdatableProperties(HervestPlan expectedHervestPlan) {
        assertHervestPlanAllUpdatablePropertiesEquals(expectedHervestPlan, getPersistedHervestPlan(expectedHervestPlan));
    }
}
