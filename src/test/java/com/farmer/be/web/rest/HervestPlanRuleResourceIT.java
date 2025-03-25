package com.farmer.be.web.rest;

import static com.farmer.be.domain.HervestPlanRuleAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.HervestPlanRule;
import com.farmer.be.domain.enumeration.FrequencyType;
import com.farmer.be.repository.HervestPlanRuleRepository;
import com.farmer.be.service.dto.HervestPlanRuleDTO;
import com.farmer.be.service.mapper.HervestPlanRuleMapper;
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
 * Integration tests for the {@link HervestPlanRuleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HervestPlanRuleResourceIT {

    private static final FrequencyType DEFAULT_FREQUENCY_TYPE = FrequencyType.DaysOfWeek;
    private static final FrequencyType UPDATED_FREQUENCY_TYPE = FrequencyType.DaysOfMonth;

    private static final Float DEFAULT_HERVEST_PLAN_VALUE = 1F;
    private static final Float UPDATED_HERVEST_PLAN_VALUE = 2F;

    private static final Float DEFAULT_HERVEST_PLAN_VALUE_MIN = 1F;
    private static final Float UPDATED_HERVEST_PLAN_VALUE_MIN = 2F;

    private static final Float DEFAULT_HERVEST_PLAN_VALUE_MAX = 1F;
    private static final Float UPDATED_HERVEST_PLAN_VALUE_MAX = 2F;

    private static final String DEFAULT_DAYS_OF_WEEK = "AAAAAAAAAA";
    private static final String UPDATED_DAYS_OF_WEEK = "BBBBBBBBBB";

    private static final String DEFAULT_DAYS_OF_MONTH = "AAAAAAAAAA";
    private static final String UPDATED_DAYS_OF_MONTH = "BBBBBBBBBB";

    private static final String DEFAULT_DAYS_OF_YEAR = "AAAAAAAAAA";
    private static final String UPDATED_DAYS_OF_YEAR = "BBBBBBBBBB";

    private static final Long DEFAULT_ALTERNATE_XDAYS = 1L;
    private static final Long UPDATED_ALTERNATE_XDAYS = 2L;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_HAS_END_DATE = false;
    private static final Boolean UPDATED_HAS_END_DATE = true;

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

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

    private static final String ENTITY_API_URL = "/api/hervest-plan-rules";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private HervestPlanRuleRepository hervestPlanRuleRepository;

    @Autowired
    private HervestPlanRuleMapper hervestPlanRuleMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHervestPlanRuleMockMvc;

    private HervestPlanRule hervestPlanRule;

    private HervestPlanRule insertedHervestPlanRule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HervestPlanRule createEntity() {
        return new HervestPlanRule()
            .frequencyType(DEFAULT_FREQUENCY_TYPE)
            .hervestPlanValue(DEFAULT_HERVEST_PLAN_VALUE)
            .hervestPlanValueMin(DEFAULT_HERVEST_PLAN_VALUE_MIN)
            .hervestPlanValueMax(DEFAULT_HERVEST_PLAN_VALUE_MAX)
            .daysOfWeek(DEFAULT_DAYS_OF_WEEK)
            .daysOfMonth(DEFAULT_DAYS_OF_MONTH)
            .daysOfYear(DEFAULT_DAYS_OF_YEAR)
            .alternateXdays(DEFAULT_ALTERNATE_XDAYS)
            .startDate(DEFAULT_START_DATE)
            .hasEndDate(DEFAULT_HAS_END_DATE)
            .endDate(DEFAULT_END_DATE)
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
    public static HervestPlanRule createUpdatedEntity() {
        return new HervestPlanRule()
            .frequencyType(UPDATED_FREQUENCY_TYPE)
            .hervestPlanValue(UPDATED_HERVEST_PLAN_VALUE)
            .hervestPlanValueMin(UPDATED_HERVEST_PLAN_VALUE_MIN)
            .hervestPlanValueMax(UPDATED_HERVEST_PLAN_VALUE_MAX)
            .daysOfWeek(UPDATED_DAYS_OF_WEEK)
            .daysOfMonth(UPDATED_DAYS_OF_MONTH)
            .daysOfYear(UPDATED_DAYS_OF_YEAR)
            .alternateXdays(UPDATED_ALTERNATE_XDAYS)
            .startDate(UPDATED_START_DATE)
            .hasEndDate(UPDATED_HAS_END_DATE)
            .endDate(UPDATED_END_DATE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        hervestPlanRule = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedHervestPlanRule != null) {
            hervestPlanRuleRepository.delete(insertedHervestPlanRule);
            insertedHervestPlanRule = null;
        }
    }

    @Test
    @Transactional
    void createHervestPlanRule() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the HervestPlanRule
        HervestPlanRuleDTO hervestPlanRuleDTO = hervestPlanRuleMapper.toDto(hervestPlanRule);
        var returnedHervestPlanRuleDTO = om.readValue(
            restHervestPlanRuleMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hervestPlanRuleDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            HervestPlanRuleDTO.class
        );

        // Validate the HervestPlanRule in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedHervestPlanRule = hervestPlanRuleMapper.toEntity(returnedHervestPlanRuleDTO);
        assertHervestPlanRuleUpdatableFieldsEquals(returnedHervestPlanRule, getPersistedHervestPlanRule(returnedHervestPlanRule));

        insertedHervestPlanRule = returnedHervestPlanRule;
    }

    @Test
    @Transactional
    void createHervestPlanRuleWithExistingId() throws Exception {
        // Create the HervestPlanRule with an existing ID
        hervestPlanRule.setId(1L);
        HervestPlanRuleDTO hervestPlanRuleDTO = hervestPlanRuleMapper.toDto(hervestPlanRule);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHervestPlanRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hervestPlanRuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the HervestPlanRule in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hervestPlanRule.setCreateddBy(null);

        // Create the HervestPlanRule, which fails.
        HervestPlanRuleDTO hervestPlanRuleDTO = hervestPlanRuleMapper.toDto(hervestPlanRule);

        restHervestPlanRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hervestPlanRuleDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hervestPlanRule.setCreatedTime(null);

        // Create the HervestPlanRule, which fails.
        HervestPlanRuleDTO hervestPlanRuleDTO = hervestPlanRuleMapper.toDto(hervestPlanRule);

        restHervestPlanRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hervestPlanRuleDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hervestPlanRule.setUpdatedBy(null);

        // Create the HervestPlanRule, which fails.
        HervestPlanRuleDTO hervestPlanRuleDTO = hervestPlanRuleMapper.toDto(hervestPlanRule);

        restHervestPlanRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hervestPlanRuleDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        hervestPlanRule.setUpdatedTime(null);

        // Create the HervestPlanRule, which fails.
        HervestPlanRuleDTO hervestPlanRuleDTO = hervestPlanRuleMapper.toDto(hervestPlanRule);

        restHervestPlanRuleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hervestPlanRuleDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHervestPlanRules() throws Exception {
        // Initialize the database
        insertedHervestPlanRule = hervestPlanRuleRepository.saveAndFlush(hervestPlanRule);

        // Get all the hervestPlanRuleList
        restHervestPlanRuleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hervestPlanRule.getId().intValue())))
            .andExpect(jsonPath("$.[*].frequencyType").value(hasItem(DEFAULT_FREQUENCY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].hervestPlanValue").value(hasItem(DEFAULT_HERVEST_PLAN_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].hervestPlanValueMin").value(hasItem(DEFAULT_HERVEST_PLAN_VALUE_MIN.doubleValue())))
            .andExpect(jsonPath("$.[*].hervestPlanValueMax").value(hasItem(DEFAULT_HERVEST_PLAN_VALUE_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].daysOfWeek").value(hasItem(DEFAULT_DAYS_OF_WEEK)))
            .andExpect(jsonPath("$.[*].daysOfMonth").value(hasItem(DEFAULT_DAYS_OF_MONTH)))
            .andExpect(jsonPath("$.[*].daysOfYear").value(hasItem(DEFAULT_DAYS_OF_YEAR)))
            .andExpect(jsonPath("$.[*].alternateXdays").value(hasItem(DEFAULT_ALTERNATE_XDAYS.intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].hasEndDate").value(hasItem(DEFAULT_HAS_END_DATE)))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getHervestPlanRule() throws Exception {
        // Initialize the database
        insertedHervestPlanRule = hervestPlanRuleRepository.saveAndFlush(hervestPlanRule);

        // Get the hervestPlanRule
        restHervestPlanRuleMockMvc
            .perform(get(ENTITY_API_URL_ID, hervestPlanRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hervestPlanRule.getId().intValue()))
            .andExpect(jsonPath("$.frequencyType").value(DEFAULT_FREQUENCY_TYPE.toString()))
            .andExpect(jsonPath("$.hervestPlanValue").value(DEFAULT_HERVEST_PLAN_VALUE.doubleValue()))
            .andExpect(jsonPath("$.hervestPlanValueMin").value(DEFAULT_HERVEST_PLAN_VALUE_MIN.doubleValue()))
            .andExpect(jsonPath("$.hervestPlanValueMax").value(DEFAULT_HERVEST_PLAN_VALUE_MAX.doubleValue()))
            .andExpect(jsonPath("$.daysOfWeek").value(DEFAULT_DAYS_OF_WEEK))
            .andExpect(jsonPath("$.daysOfMonth").value(DEFAULT_DAYS_OF_MONTH))
            .andExpect(jsonPath("$.daysOfYear").value(DEFAULT_DAYS_OF_YEAR))
            .andExpect(jsonPath("$.alternateXdays").value(DEFAULT_ALTERNATE_XDAYS.intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.hasEndDate").value(DEFAULT_HAS_END_DATE))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingHervestPlanRule() throws Exception {
        // Get the hervestPlanRule
        restHervestPlanRuleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingHervestPlanRule() throws Exception {
        // Initialize the database
        insertedHervestPlanRule = hervestPlanRuleRepository.saveAndFlush(hervestPlanRule);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hervestPlanRule
        HervestPlanRule updatedHervestPlanRule = hervestPlanRuleRepository.findById(hervestPlanRule.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedHervestPlanRule are not directly saved in db
        em.detach(updatedHervestPlanRule);
        updatedHervestPlanRule
            .frequencyType(UPDATED_FREQUENCY_TYPE)
            .hervestPlanValue(UPDATED_HERVEST_PLAN_VALUE)
            .hervestPlanValueMin(UPDATED_HERVEST_PLAN_VALUE_MIN)
            .hervestPlanValueMax(UPDATED_HERVEST_PLAN_VALUE_MAX)
            .daysOfWeek(UPDATED_DAYS_OF_WEEK)
            .daysOfMonth(UPDATED_DAYS_OF_MONTH)
            .daysOfYear(UPDATED_DAYS_OF_YEAR)
            .alternateXdays(UPDATED_ALTERNATE_XDAYS)
            .startDate(UPDATED_START_DATE)
            .hasEndDate(UPDATED_HAS_END_DATE)
            .endDate(UPDATED_END_DATE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        HervestPlanRuleDTO hervestPlanRuleDTO = hervestPlanRuleMapper.toDto(updatedHervestPlanRule);

        restHervestPlanRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hervestPlanRuleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hervestPlanRuleDTO))
            )
            .andExpect(status().isOk());

        // Validate the HervestPlanRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedHervestPlanRuleToMatchAllProperties(updatedHervestPlanRule);
    }

    @Test
    @Transactional
    void putNonExistingHervestPlanRule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hervestPlanRule.setId(longCount.incrementAndGet());

        // Create the HervestPlanRule
        HervestPlanRuleDTO hervestPlanRuleDTO = hervestPlanRuleMapper.toDto(hervestPlanRule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHervestPlanRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hervestPlanRuleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hervestPlanRuleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HervestPlanRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHervestPlanRule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hervestPlanRule.setId(longCount.incrementAndGet());

        // Create the HervestPlanRule
        HervestPlanRuleDTO hervestPlanRuleDTO = hervestPlanRuleMapper.toDto(hervestPlanRule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHervestPlanRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(hervestPlanRuleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HervestPlanRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHervestPlanRule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hervestPlanRule.setId(longCount.incrementAndGet());

        // Create the HervestPlanRule
        HervestPlanRuleDTO hervestPlanRuleDTO = hervestPlanRuleMapper.toDto(hervestPlanRule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHervestPlanRuleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(hervestPlanRuleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HervestPlanRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHervestPlanRuleWithPatch() throws Exception {
        // Initialize the database
        insertedHervestPlanRule = hervestPlanRuleRepository.saveAndFlush(hervestPlanRule);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hervestPlanRule using partial update
        HervestPlanRule partialUpdatedHervestPlanRule = new HervestPlanRule();
        partialUpdatedHervestPlanRule.setId(hervestPlanRule.getId());

        partialUpdatedHervestPlanRule
            .frequencyType(UPDATED_FREQUENCY_TYPE)
            .hervestPlanValue(UPDATED_HERVEST_PLAN_VALUE)
            .hervestPlanValueMin(UPDATED_HERVEST_PLAN_VALUE_MIN)
            .daysOfWeek(UPDATED_DAYS_OF_WEEK)
            .hasEndDate(UPDATED_HAS_END_DATE)
            .endDate(UPDATED_END_DATE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedTime(UPDATED_UPDATED_TIME);

        restHervestPlanRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHervestPlanRule.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHervestPlanRule))
            )
            .andExpect(status().isOk());

        // Validate the HervestPlanRule in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHervestPlanRuleUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedHervestPlanRule, hervestPlanRule),
            getPersistedHervestPlanRule(hervestPlanRule)
        );
    }

    @Test
    @Transactional
    void fullUpdateHervestPlanRuleWithPatch() throws Exception {
        // Initialize the database
        insertedHervestPlanRule = hervestPlanRuleRepository.saveAndFlush(hervestPlanRule);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the hervestPlanRule using partial update
        HervestPlanRule partialUpdatedHervestPlanRule = new HervestPlanRule();
        partialUpdatedHervestPlanRule.setId(hervestPlanRule.getId());

        partialUpdatedHervestPlanRule
            .frequencyType(UPDATED_FREQUENCY_TYPE)
            .hervestPlanValue(UPDATED_HERVEST_PLAN_VALUE)
            .hervestPlanValueMin(UPDATED_HERVEST_PLAN_VALUE_MIN)
            .hervestPlanValueMax(UPDATED_HERVEST_PLAN_VALUE_MAX)
            .daysOfWeek(UPDATED_DAYS_OF_WEEK)
            .daysOfMonth(UPDATED_DAYS_OF_MONTH)
            .daysOfYear(UPDATED_DAYS_OF_YEAR)
            .alternateXdays(UPDATED_ALTERNATE_XDAYS)
            .startDate(UPDATED_START_DATE)
            .hasEndDate(UPDATED_HAS_END_DATE)
            .endDate(UPDATED_END_DATE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restHervestPlanRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHervestPlanRule.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedHervestPlanRule))
            )
            .andExpect(status().isOk());

        // Validate the HervestPlanRule in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertHervestPlanRuleUpdatableFieldsEquals(
            partialUpdatedHervestPlanRule,
            getPersistedHervestPlanRule(partialUpdatedHervestPlanRule)
        );
    }

    @Test
    @Transactional
    void patchNonExistingHervestPlanRule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hervestPlanRule.setId(longCount.incrementAndGet());

        // Create the HervestPlanRule
        HervestPlanRuleDTO hervestPlanRuleDTO = hervestPlanRuleMapper.toDto(hervestPlanRule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHervestPlanRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hervestPlanRuleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hervestPlanRuleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HervestPlanRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHervestPlanRule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hervestPlanRule.setId(longCount.incrementAndGet());

        // Create the HervestPlanRule
        HervestPlanRuleDTO hervestPlanRuleDTO = hervestPlanRuleMapper.toDto(hervestPlanRule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHervestPlanRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(hervestPlanRuleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the HervestPlanRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHervestPlanRule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        hervestPlanRule.setId(longCount.incrementAndGet());

        // Create the HervestPlanRule
        HervestPlanRuleDTO hervestPlanRuleDTO = hervestPlanRuleMapper.toDto(hervestPlanRule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHervestPlanRuleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(hervestPlanRuleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HervestPlanRule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHervestPlanRule() throws Exception {
        // Initialize the database
        insertedHervestPlanRule = hervestPlanRuleRepository.saveAndFlush(hervestPlanRule);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the hervestPlanRule
        restHervestPlanRuleMockMvc
            .perform(delete(ENTITY_API_URL_ID, hervestPlanRule.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return hervestPlanRuleRepository.count();
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

    protected HervestPlanRule getPersistedHervestPlanRule(HervestPlanRule hervestPlanRule) {
        return hervestPlanRuleRepository.findById(hervestPlanRule.getId()).orElseThrow();
    }

    protected void assertPersistedHervestPlanRuleToMatchAllProperties(HervestPlanRule expectedHervestPlanRule) {
        assertHervestPlanRuleAllPropertiesEquals(expectedHervestPlanRule, getPersistedHervestPlanRule(expectedHervestPlanRule));
    }

    protected void assertPersistedHervestPlanRuleToMatchUpdatableProperties(HervestPlanRule expectedHervestPlanRule) {
        assertHervestPlanRuleAllUpdatablePropertiesEquals(expectedHervestPlanRule, getPersistedHervestPlanRule(expectedHervestPlanRule));
    }
}
