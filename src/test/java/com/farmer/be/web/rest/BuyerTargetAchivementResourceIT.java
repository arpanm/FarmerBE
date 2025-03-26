package com.farmer.be.web.rest;

import static com.farmer.be.domain.BuyerTargetAchivementAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.BuyerTargetAchivement;
import com.farmer.be.repository.BuyerTargetAchivementRepository;
import com.farmer.be.service.dto.BuyerTargetAchivementDTO;
import com.farmer.be.service.mapper.BuyerTargetAchivementMapper;
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
 * Integration tests for the {@link BuyerTargetAchivementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BuyerTargetAchivementResourceIT {

    private static final Long DEFAULT_LABOUR = 1L;
    private static final Long UPDATED_LABOUR = 2L;

    private static final Long DEFAULT_FARM_VISIT = 1L;
    private static final Long UPDATED_FARM_VISIT = 2L;

    private static final Float DEFAULT_TOTAL_COLLECTION = 1F;
    private static final Float UPDATED_TOTAL_COLLECTION = 2F;

    private static final LocalDate DEFAULT_TARGET_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TARGET_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_ATTENDENCE_HOURS = 1F;
    private static final Float UPDATED_ATTENDENCE_HOURS = 2F;

    private static final Long DEFAULT_ACHIVEMENT_LABOUR = 1L;
    private static final Long UPDATED_ACHIVEMENT_LABOUR = 2L;

    private static final Long DEFAULT_ACHIVEMENT_FARM_VISIT = 1L;
    private static final Long UPDATED_ACHIVEMENT_FARM_VISIT = 2L;

    private static final Float DEFAULT_ACHIVEMENT_TOTAL_COLLECTION = 1F;
    private static final Float UPDATED_ACHIVEMENT_TOTAL_COLLECTION = 2F;

    private static final Float DEFAULT_ACHIVEMENT_ATTENDENCE_HOURS = 1F;
    private static final Float UPDATED_ACHIVEMENT_ATTENDENCE_HOURS = 2F;

    private static final Float DEFAULT_ACHIVEMENT_SCORE = 1F;
    private static final Float UPDATED_ACHIVEMENT_SCORE = 2F;

    private static final Float DEFAULT_INCENTIVE = 1F;
    private static final Float UPDATED_INCENTIVE = 2F;

    private static final Float DEFAULT_KM_DRIVEN = 1F;
    private static final Float UPDATED_KM_DRIVEN = 2F;

    private static final Float DEFAULT_CONVEYANCE = 1F;
    private static final Float UPDATED_CONVEYANCE = 2F;

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

    private static final String ENTITY_API_URL = "/api/buyer-target-achivements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BuyerTargetAchivementRepository buyerTargetAchivementRepository;

    @Autowired
    private BuyerTargetAchivementMapper buyerTargetAchivementMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBuyerTargetAchivementMockMvc;

    private BuyerTargetAchivement buyerTargetAchivement;

    private BuyerTargetAchivement insertedBuyerTargetAchivement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BuyerTargetAchivement createEntity() {
        return new BuyerTargetAchivement()
            .labour(DEFAULT_LABOUR)
            .farmVisit(DEFAULT_FARM_VISIT)
            .totalCollection(DEFAULT_TOTAL_COLLECTION)
            .targetDate(DEFAULT_TARGET_DATE)
            .attendenceHours(DEFAULT_ATTENDENCE_HOURS)
            .achivementLabour(DEFAULT_ACHIVEMENT_LABOUR)
            .achivementFarmVisit(DEFAULT_ACHIVEMENT_FARM_VISIT)
            .achivementTotalCollection(DEFAULT_ACHIVEMENT_TOTAL_COLLECTION)
            .achivementAttendenceHours(DEFAULT_ACHIVEMENT_ATTENDENCE_HOURS)
            .achivementScore(DEFAULT_ACHIVEMENT_SCORE)
            .incentive(DEFAULT_INCENTIVE)
            .kmDriven(DEFAULT_KM_DRIVEN)
            .conveyance(DEFAULT_CONVEYANCE)
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
    public static BuyerTargetAchivement createUpdatedEntity() {
        return new BuyerTargetAchivement()
            .labour(UPDATED_LABOUR)
            .farmVisit(UPDATED_FARM_VISIT)
            .totalCollection(UPDATED_TOTAL_COLLECTION)
            .targetDate(UPDATED_TARGET_DATE)
            .attendenceHours(UPDATED_ATTENDENCE_HOURS)
            .achivementLabour(UPDATED_ACHIVEMENT_LABOUR)
            .achivementFarmVisit(UPDATED_ACHIVEMENT_FARM_VISIT)
            .achivementTotalCollection(UPDATED_ACHIVEMENT_TOTAL_COLLECTION)
            .achivementAttendenceHours(UPDATED_ACHIVEMENT_ATTENDENCE_HOURS)
            .achivementScore(UPDATED_ACHIVEMENT_SCORE)
            .incentive(UPDATED_INCENTIVE)
            .kmDriven(UPDATED_KM_DRIVEN)
            .conveyance(UPDATED_CONVEYANCE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        buyerTargetAchivement = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedBuyerTargetAchivement != null) {
            buyerTargetAchivementRepository.delete(insertedBuyerTargetAchivement);
            insertedBuyerTargetAchivement = null;
        }
    }

    @Test
    @Transactional
    void createBuyerTargetAchivement() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BuyerTargetAchivement
        BuyerTargetAchivementDTO buyerTargetAchivementDTO = buyerTargetAchivementMapper.toDto(buyerTargetAchivement);
        var returnedBuyerTargetAchivementDTO = om.readValue(
            restBuyerTargetAchivementMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buyerTargetAchivementDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BuyerTargetAchivementDTO.class
        );

        // Validate the BuyerTargetAchivement in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBuyerTargetAchivement = buyerTargetAchivementMapper.toEntity(returnedBuyerTargetAchivementDTO);
        assertBuyerTargetAchivementUpdatableFieldsEquals(
            returnedBuyerTargetAchivement,
            getPersistedBuyerTargetAchivement(returnedBuyerTargetAchivement)
        );

        insertedBuyerTargetAchivement = returnedBuyerTargetAchivement;
    }

    @Test
    @Transactional
    void createBuyerTargetAchivementWithExistingId() throws Exception {
        // Create the BuyerTargetAchivement with an existing ID
        buyerTargetAchivement.setId(1L);
        BuyerTargetAchivementDTO buyerTargetAchivementDTO = buyerTargetAchivementMapper.toDto(buyerTargetAchivement);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuyerTargetAchivementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buyerTargetAchivementDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BuyerTargetAchivement in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        buyerTargetAchivement.setCreateddBy(null);

        // Create the BuyerTargetAchivement, which fails.
        BuyerTargetAchivementDTO buyerTargetAchivementDTO = buyerTargetAchivementMapper.toDto(buyerTargetAchivement);

        restBuyerTargetAchivementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buyerTargetAchivementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        buyerTargetAchivement.setCreatedTime(null);

        // Create the BuyerTargetAchivement, which fails.
        BuyerTargetAchivementDTO buyerTargetAchivementDTO = buyerTargetAchivementMapper.toDto(buyerTargetAchivement);

        restBuyerTargetAchivementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buyerTargetAchivementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        buyerTargetAchivement.setUpdatedBy(null);

        // Create the BuyerTargetAchivement, which fails.
        BuyerTargetAchivementDTO buyerTargetAchivementDTO = buyerTargetAchivementMapper.toDto(buyerTargetAchivement);

        restBuyerTargetAchivementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buyerTargetAchivementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        buyerTargetAchivement.setUpdatedTime(null);

        // Create the BuyerTargetAchivement, which fails.
        BuyerTargetAchivementDTO buyerTargetAchivementDTO = buyerTargetAchivementMapper.toDto(buyerTargetAchivement);

        restBuyerTargetAchivementMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buyerTargetAchivementDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBuyerTargetAchivements() throws Exception {
        // Initialize the database
        insertedBuyerTargetAchivement = buyerTargetAchivementRepository.saveAndFlush(buyerTargetAchivement);

        // Get all the buyerTargetAchivementList
        restBuyerTargetAchivementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buyerTargetAchivement.getId().intValue())))
            .andExpect(jsonPath("$.[*].labour").value(hasItem(DEFAULT_LABOUR.intValue())))
            .andExpect(jsonPath("$.[*].farmVisit").value(hasItem(DEFAULT_FARM_VISIT.intValue())))
            .andExpect(jsonPath("$.[*].totalCollection").value(hasItem(DEFAULT_TOTAL_COLLECTION.doubleValue())))
            .andExpect(jsonPath("$.[*].targetDate").value(hasItem(DEFAULT_TARGET_DATE.toString())))
            .andExpect(jsonPath("$.[*].attendenceHours").value(hasItem(DEFAULT_ATTENDENCE_HOURS.doubleValue())))
            .andExpect(jsonPath("$.[*].achivementLabour").value(hasItem(DEFAULT_ACHIVEMENT_LABOUR.intValue())))
            .andExpect(jsonPath("$.[*].achivementFarmVisit").value(hasItem(DEFAULT_ACHIVEMENT_FARM_VISIT.intValue())))
            .andExpect(jsonPath("$.[*].achivementTotalCollection").value(hasItem(DEFAULT_ACHIVEMENT_TOTAL_COLLECTION.doubleValue())))
            .andExpect(jsonPath("$.[*].achivementAttendenceHours").value(hasItem(DEFAULT_ACHIVEMENT_ATTENDENCE_HOURS.doubleValue())))
            .andExpect(jsonPath("$.[*].achivementScore").value(hasItem(DEFAULT_ACHIVEMENT_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].incentive").value(hasItem(DEFAULT_INCENTIVE.doubleValue())))
            .andExpect(jsonPath("$.[*].kmDriven").value(hasItem(DEFAULT_KM_DRIVEN.doubleValue())))
            .andExpect(jsonPath("$.[*].conveyance").value(hasItem(DEFAULT_CONVEYANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getBuyerTargetAchivement() throws Exception {
        // Initialize the database
        insertedBuyerTargetAchivement = buyerTargetAchivementRepository.saveAndFlush(buyerTargetAchivement);

        // Get the buyerTargetAchivement
        restBuyerTargetAchivementMockMvc
            .perform(get(ENTITY_API_URL_ID, buyerTargetAchivement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(buyerTargetAchivement.getId().intValue()))
            .andExpect(jsonPath("$.labour").value(DEFAULT_LABOUR.intValue()))
            .andExpect(jsonPath("$.farmVisit").value(DEFAULT_FARM_VISIT.intValue()))
            .andExpect(jsonPath("$.totalCollection").value(DEFAULT_TOTAL_COLLECTION.doubleValue()))
            .andExpect(jsonPath("$.targetDate").value(DEFAULT_TARGET_DATE.toString()))
            .andExpect(jsonPath("$.attendenceHours").value(DEFAULT_ATTENDENCE_HOURS.doubleValue()))
            .andExpect(jsonPath("$.achivementLabour").value(DEFAULT_ACHIVEMENT_LABOUR.intValue()))
            .andExpect(jsonPath("$.achivementFarmVisit").value(DEFAULT_ACHIVEMENT_FARM_VISIT.intValue()))
            .andExpect(jsonPath("$.achivementTotalCollection").value(DEFAULT_ACHIVEMENT_TOTAL_COLLECTION.doubleValue()))
            .andExpect(jsonPath("$.achivementAttendenceHours").value(DEFAULT_ACHIVEMENT_ATTENDENCE_HOURS.doubleValue()))
            .andExpect(jsonPath("$.achivementScore").value(DEFAULT_ACHIVEMENT_SCORE.doubleValue()))
            .andExpect(jsonPath("$.incentive").value(DEFAULT_INCENTIVE.doubleValue()))
            .andExpect(jsonPath("$.kmDriven").value(DEFAULT_KM_DRIVEN.doubleValue()))
            .andExpect(jsonPath("$.conveyance").value(DEFAULT_CONVEYANCE.doubleValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingBuyerTargetAchivement() throws Exception {
        // Get the buyerTargetAchivement
        restBuyerTargetAchivementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBuyerTargetAchivement() throws Exception {
        // Initialize the database
        insertedBuyerTargetAchivement = buyerTargetAchivementRepository.saveAndFlush(buyerTargetAchivement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the buyerTargetAchivement
        BuyerTargetAchivement updatedBuyerTargetAchivement = buyerTargetAchivementRepository
            .findById(buyerTargetAchivement.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedBuyerTargetAchivement are not directly saved in db
        em.detach(updatedBuyerTargetAchivement);
        updatedBuyerTargetAchivement
            .labour(UPDATED_LABOUR)
            .farmVisit(UPDATED_FARM_VISIT)
            .totalCollection(UPDATED_TOTAL_COLLECTION)
            .targetDate(UPDATED_TARGET_DATE)
            .attendenceHours(UPDATED_ATTENDENCE_HOURS)
            .achivementLabour(UPDATED_ACHIVEMENT_LABOUR)
            .achivementFarmVisit(UPDATED_ACHIVEMENT_FARM_VISIT)
            .achivementTotalCollection(UPDATED_ACHIVEMENT_TOTAL_COLLECTION)
            .achivementAttendenceHours(UPDATED_ACHIVEMENT_ATTENDENCE_HOURS)
            .achivementScore(UPDATED_ACHIVEMENT_SCORE)
            .incentive(UPDATED_INCENTIVE)
            .kmDriven(UPDATED_KM_DRIVEN)
            .conveyance(UPDATED_CONVEYANCE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        BuyerTargetAchivementDTO buyerTargetAchivementDTO = buyerTargetAchivementMapper.toDto(updatedBuyerTargetAchivement);

        restBuyerTargetAchivementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, buyerTargetAchivementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(buyerTargetAchivementDTO))
            )
            .andExpect(status().isOk());

        // Validate the BuyerTargetAchivement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBuyerTargetAchivementToMatchAllProperties(updatedBuyerTargetAchivement);
    }

    @Test
    @Transactional
    void putNonExistingBuyerTargetAchivement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        buyerTargetAchivement.setId(longCount.incrementAndGet());

        // Create the BuyerTargetAchivement
        BuyerTargetAchivementDTO buyerTargetAchivementDTO = buyerTargetAchivementMapper.toDto(buyerTargetAchivement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuyerTargetAchivementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, buyerTargetAchivementDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(buyerTargetAchivementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyerTargetAchivement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBuyerTargetAchivement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        buyerTargetAchivement.setId(longCount.incrementAndGet());

        // Create the BuyerTargetAchivement
        BuyerTargetAchivementDTO buyerTargetAchivementDTO = buyerTargetAchivementMapper.toDto(buyerTargetAchivement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyerTargetAchivementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(buyerTargetAchivementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyerTargetAchivement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBuyerTargetAchivement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        buyerTargetAchivement.setId(longCount.incrementAndGet());

        // Create the BuyerTargetAchivement
        BuyerTargetAchivementDTO buyerTargetAchivementDTO = buyerTargetAchivementMapper.toDto(buyerTargetAchivement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyerTargetAchivementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(buyerTargetAchivementDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BuyerTargetAchivement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBuyerTargetAchivementWithPatch() throws Exception {
        // Initialize the database
        insertedBuyerTargetAchivement = buyerTargetAchivementRepository.saveAndFlush(buyerTargetAchivement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the buyerTargetAchivement using partial update
        BuyerTargetAchivement partialUpdatedBuyerTargetAchivement = new BuyerTargetAchivement();
        partialUpdatedBuyerTargetAchivement.setId(buyerTargetAchivement.getId());

        partialUpdatedBuyerTargetAchivement
            .labour(UPDATED_LABOUR)
            .totalCollection(UPDATED_TOTAL_COLLECTION)
            .achivementTotalCollection(UPDATED_ACHIVEMENT_TOTAL_COLLECTION)
            .isActive(UPDATED_IS_ACTIVE)
            .updatedTime(UPDATED_UPDATED_TIME);

        restBuyerTargetAchivementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuyerTargetAchivement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBuyerTargetAchivement))
            )
            .andExpect(status().isOk());

        // Validate the BuyerTargetAchivement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBuyerTargetAchivementUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBuyerTargetAchivement, buyerTargetAchivement),
            getPersistedBuyerTargetAchivement(buyerTargetAchivement)
        );
    }

    @Test
    @Transactional
    void fullUpdateBuyerTargetAchivementWithPatch() throws Exception {
        // Initialize the database
        insertedBuyerTargetAchivement = buyerTargetAchivementRepository.saveAndFlush(buyerTargetAchivement);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the buyerTargetAchivement using partial update
        BuyerTargetAchivement partialUpdatedBuyerTargetAchivement = new BuyerTargetAchivement();
        partialUpdatedBuyerTargetAchivement.setId(buyerTargetAchivement.getId());

        partialUpdatedBuyerTargetAchivement
            .labour(UPDATED_LABOUR)
            .farmVisit(UPDATED_FARM_VISIT)
            .totalCollection(UPDATED_TOTAL_COLLECTION)
            .targetDate(UPDATED_TARGET_DATE)
            .attendenceHours(UPDATED_ATTENDENCE_HOURS)
            .achivementLabour(UPDATED_ACHIVEMENT_LABOUR)
            .achivementFarmVisit(UPDATED_ACHIVEMENT_FARM_VISIT)
            .achivementTotalCollection(UPDATED_ACHIVEMENT_TOTAL_COLLECTION)
            .achivementAttendenceHours(UPDATED_ACHIVEMENT_ATTENDENCE_HOURS)
            .achivementScore(UPDATED_ACHIVEMENT_SCORE)
            .incentive(UPDATED_INCENTIVE)
            .kmDriven(UPDATED_KM_DRIVEN)
            .conveyance(UPDATED_CONVEYANCE)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restBuyerTargetAchivementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBuyerTargetAchivement.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBuyerTargetAchivement))
            )
            .andExpect(status().isOk());

        // Validate the BuyerTargetAchivement in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBuyerTargetAchivementUpdatableFieldsEquals(
            partialUpdatedBuyerTargetAchivement,
            getPersistedBuyerTargetAchivement(partialUpdatedBuyerTargetAchivement)
        );
    }

    @Test
    @Transactional
    void patchNonExistingBuyerTargetAchivement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        buyerTargetAchivement.setId(longCount.incrementAndGet());

        // Create the BuyerTargetAchivement
        BuyerTargetAchivementDTO buyerTargetAchivementDTO = buyerTargetAchivementMapper.toDto(buyerTargetAchivement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBuyerTargetAchivementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, buyerTargetAchivementDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(buyerTargetAchivementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyerTargetAchivement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBuyerTargetAchivement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        buyerTargetAchivement.setId(longCount.incrementAndGet());

        // Create the BuyerTargetAchivement
        BuyerTargetAchivementDTO buyerTargetAchivementDTO = buyerTargetAchivementMapper.toDto(buyerTargetAchivement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyerTargetAchivementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(buyerTargetAchivementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BuyerTargetAchivement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBuyerTargetAchivement() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        buyerTargetAchivement.setId(longCount.incrementAndGet());

        // Create the BuyerTargetAchivement
        BuyerTargetAchivementDTO buyerTargetAchivementDTO = buyerTargetAchivementMapper.toDto(buyerTargetAchivement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBuyerTargetAchivementMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(buyerTargetAchivementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BuyerTargetAchivement in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBuyerTargetAchivement() throws Exception {
        // Initialize the database
        insertedBuyerTargetAchivement = buyerTargetAchivementRepository.saveAndFlush(buyerTargetAchivement);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the buyerTargetAchivement
        restBuyerTargetAchivementMockMvc
            .perform(delete(ENTITY_API_URL_ID, buyerTargetAchivement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return buyerTargetAchivementRepository.count();
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

    protected BuyerTargetAchivement getPersistedBuyerTargetAchivement(BuyerTargetAchivement buyerTargetAchivement) {
        return buyerTargetAchivementRepository.findById(buyerTargetAchivement.getId()).orElseThrow();
    }

    protected void assertPersistedBuyerTargetAchivementToMatchAllProperties(BuyerTargetAchivement expectedBuyerTargetAchivement) {
        assertBuyerTargetAchivementAllPropertiesEquals(
            expectedBuyerTargetAchivement,
            getPersistedBuyerTargetAchivement(expectedBuyerTargetAchivement)
        );
    }

    protected void assertPersistedBuyerTargetAchivementToMatchUpdatableProperties(BuyerTargetAchivement expectedBuyerTargetAchivement) {
        assertBuyerTargetAchivementAllUpdatablePropertiesEquals(
            expectedBuyerTargetAchivement,
            getPersistedBuyerTargetAchivement(expectedBuyerTargetAchivement)
        );
    }
}
