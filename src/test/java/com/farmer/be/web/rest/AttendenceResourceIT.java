package com.farmer.be.web.rest;

import static com.farmer.be.domain.AttendenceAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.Attendence;
import com.farmer.be.domain.enumeration.AttendenceType;
import com.farmer.be.repository.AttendenceRepository;
import com.farmer.be.service.dto.AttendenceDTO;
import com.farmer.be.service.mapper.AttendenceMapper;
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
 * Integration tests for the {@link AttendenceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AttendenceResourceIT {

    private static final AttendenceType DEFAULT_ATTENDENCE_TYPE = AttendenceType.CheckIn;
    private static final AttendenceType UPDATED_ATTENDENCE_TYPE = AttendenceType.CheckOut;

    private static final LocalDate DEFAULT_ATTENDENCE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ATTENDENCE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_ATTENDENCE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ATTENDENCE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Float DEFAULT_LAT = 1F;
    private static final Float UPDATED_LAT = 2F;

    private static final Float DEFAULT_LON = 1F;
    private static final Float UPDATED_LON = 2F;

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

    private static final String ENTITY_API_URL = "/api/attendences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AttendenceRepository attendenceRepository;

    @Autowired
    private AttendenceMapper attendenceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttendenceMockMvc;

    private Attendence attendence;

    private Attendence insertedAttendence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attendence createEntity() {
        return new Attendence()
            .attendenceType(DEFAULT_ATTENDENCE_TYPE)
            .attendenceDate(DEFAULT_ATTENDENCE_DATE)
            .attendenceTime(DEFAULT_ATTENDENCE_TIME)
            .lat(DEFAULT_LAT)
            .lon(DEFAULT_LON)
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
    public static Attendence createUpdatedEntity() {
        return new Attendence()
            .attendenceType(UPDATED_ATTENDENCE_TYPE)
            .attendenceDate(UPDATED_ATTENDENCE_DATE)
            .attendenceTime(UPDATED_ATTENDENCE_TIME)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        attendence = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedAttendence != null) {
            attendenceRepository.delete(insertedAttendence);
            insertedAttendence = null;
        }
    }

    @Test
    @Transactional
    void createAttendence() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Attendence
        AttendenceDTO attendenceDTO = attendenceMapper.toDto(attendence);
        var returnedAttendenceDTO = om.readValue(
            restAttendenceMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(attendenceDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AttendenceDTO.class
        );

        // Validate the Attendence in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAttendence = attendenceMapper.toEntity(returnedAttendenceDTO);
        assertAttendenceUpdatableFieldsEquals(returnedAttendence, getPersistedAttendence(returnedAttendence));

        insertedAttendence = returnedAttendence;
    }

    @Test
    @Transactional
    void createAttendenceWithExistingId() throws Exception {
        // Create the Attendence with an existing ID
        attendence.setId(1L);
        AttendenceDTO attendenceDTO = attendenceMapper.toDto(attendence);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(attendenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attendence in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        attendence.setCreateddBy(null);

        // Create the Attendence, which fails.
        AttendenceDTO attendenceDTO = attendenceMapper.toDto(attendence);

        restAttendenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(attendenceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        attendence.setCreatedTime(null);

        // Create the Attendence, which fails.
        AttendenceDTO attendenceDTO = attendenceMapper.toDto(attendence);

        restAttendenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(attendenceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        attendence.setUpdatedBy(null);

        // Create the Attendence, which fails.
        AttendenceDTO attendenceDTO = attendenceMapper.toDto(attendence);

        restAttendenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(attendenceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        attendence.setUpdatedTime(null);

        // Create the Attendence, which fails.
        AttendenceDTO attendenceDTO = attendenceMapper.toDto(attendence);

        restAttendenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(attendenceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAttendences() throws Exception {
        // Initialize the database
        insertedAttendence = attendenceRepository.saveAndFlush(attendence);

        // Get all the attendenceList
        restAttendenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendence.getId().intValue())))
            .andExpect(jsonPath("$.[*].attendenceType").value(hasItem(DEFAULT_ATTENDENCE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].attendenceDate").value(hasItem(DEFAULT_ATTENDENCE_DATE.toString())))
            .andExpect(jsonPath("$.[*].attendenceTime").value(hasItem(DEFAULT_ATTENDENCE_TIME.toString())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.doubleValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getAttendence() throws Exception {
        // Initialize the database
        insertedAttendence = attendenceRepository.saveAndFlush(attendence);

        // Get the attendence
        restAttendenceMockMvc
            .perform(get(ENTITY_API_URL_ID, attendence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attendence.getId().intValue()))
            .andExpect(jsonPath("$.attendenceType").value(DEFAULT_ATTENDENCE_TYPE.toString()))
            .andExpect(jsonPath("$.attendenceDate").value(DEFAULT_ATTENDENCE_DATE.toString()))
            .andExpect(jsonPath("$.attendenceTime").value(DEFAULT_ATTENDENCE_TIME.toString()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.lon").value(DEFAULT_LON.doubleValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAttendence() throws Exception {
        // Get the attendence
        restAttendenceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAttendence() throws Exception {
        // Initialize the database
        insertedAttendence = attendenceRepository.saveAndFlush(attendence);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the attendence
        Attendence updatedAttendence = attendenceRepository.findById(attendence.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAttendence are not directly saved in db
        em.detach(updatedAttendence);
        updatedAttendence
            .attendenceType(UPDATED_ATTENDENCE_TYPE)
            .attendenceDate(UPDATED_ATTENDENCE_DATE)
            .attendenceTime(UPDATED_ATTENDENCE_TIME)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        AttendenceDTO attendenceDTO = attendenceMapper.toDto(updatedAttendence);

        restAttendenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attendenceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(attendenceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Attendence in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAttendenceToMatchAllProperties(updatedAttendence);
    }

    @Test
    @Transactional
    void putNonExistingAttendence() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attendence.setId(longCount.incrementAndGet());

        // Create the Attendence
        AttendenceDTO attendenceDTO = attendenceMapper.toDto(attendence);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, attendenceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(attendenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attendence in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAttendence() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attendence.setId(longCount.incrementAndGet());

        // Create the Attendence
        AttendenceDTO attendenceDTO = attendenceMapper.toDto(attendence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttendenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(attendenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attendence in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAttendence() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attendence.setId(longCount.incrementAndGet());

        // Create the Attendence
        AttendenceDTO attendenceDTO = attendenceMapper.toDto(attendence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttendenceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(attendenceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attendence in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAttendenceWithPatch() throws Exception {
        // Initialize the database
        insertedAttendence = attendenceRepository.saveAndFlush(attendence);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the attendence using partial update
        Attendence partialUpdatedAttendence = new Attendence();
        partialUpdatedAttendence.setId(attendence.getId());

        partialUpdatedAttendence.isActive(UPDATED_IS_ACTIVE).createdTime(UPDATED_CREATED_TIME);

        restAttendenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttendence.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAttendence))
            )
            .andExpect(status().isOk());

        // Validate the Attendence in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAttendenceUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAttendence, attendence),
            getPersistedAttendence(attendence)
        );
    }

    @Test
    @Transactional
    void fullUpdateAttendenceWithPatch() throws Exception {
        // Initialize the database
        insertedAttendence = attendenceRepository.saveAndFlush(attendence);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the attendence using partial update
        Attendence partialUpdatedAttendence = new Attendence();
        partialUpdatedAttendence.setId(attendence.getId());

        partialUpdatedAttendence
            .attendenceType(UPDATED_ATTENDENCE_TYPE)
            .attendenceDate(UPDATED_ATTENDENCE_DATE)
            .attendenceTime(UPDATED_ATTENDENCE_TIME)
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restAttendenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAttendence.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAttendence))
            )
            .andExpect(status().isOk());

        // Validate the Attendence in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAttendenceUpdatableFieldsEquals(partialUpdatedAttendence, getPersistedAttendence(partialUpdatedAttendence));
    }

    @Test
    @Transactional
    void patchNonExistingAttendence() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attendence.setId(longCount.incrementAndGet());

        // Create the Attendence
        AttendenceDTO attendenceDTO = attendenceMapper.toDto(attendence);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, attendenceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(attendenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attendence in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAttendence() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attendence.setId(longCount.incrementAndGet());

        // Create the Attendence
        AttendenceDTO attendenceDTO = attendenceMapper.toDto(attendence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttendenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(attendenceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Attendence in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAttendence() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        attendence.setId(longCount.incrementAndGet());

        // Create the Attendence
        AttendenceDTO attendenceDTO = attendenceMapper.toDto(attendence);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAttendenceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(attendenceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Attendence in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAttendence() throws Exception {
        // Initialize the database
        insertedAttendence = attendenceRepository.saveAndFlush(attendence);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the attendence
        restAttendenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, attendence.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return attendenceRepository.count();
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

    protected Attendence getPersistedAttendence(Attendence attendence) {
        return attendenceRepository.findById(attendence.getId()).orElseThrow();
    }

    protected void assertPersistedAttendenceToMatchAllProperties(Attendence expectedAttendence) {
        assertAttendenceAllPropertiesEquals(expectedAttendence, getPersistedAttendence(expectedAttendence));
    }

    protected void assertPersistedAttendenceToMatchUpdatableProperties(Attendence expectedAttendence) {
        assertAttendenceAllUpdatablePropertiesEquals(expectedAttendence, getPersistedAttendence(expectedAttendence));
    }
}
