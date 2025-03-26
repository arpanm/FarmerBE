package com.farmer.be.web.rest;

import static com.farmer.be.domain.DemandDataFileAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.DemandDataFile;
import com.farmer.be.domain.enumeration.UploadStatus;
import com.farmer.be.repository.DemandDataFileRepository;
import com.farmer.be.service.dto.DemandDataFileDTO;
import com.farmer.be.service.mapper.DemandDataFileMapper;
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
 * Integration tests for the {@link DemandDataFileResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DemandDataFileResourceIT {

    private static final LocalDate DEFAULT_UPLOADED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPLOADED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_UPLOADED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPLOADED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_UPLOADED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPLOADED_BY = "BBBBBBBBBB";

    private static final UploadStatus DEFAULT_STATUS = UploadStatus.Init;
    private static final UploadStatus UPDATED_STATUS = UploadStatus.Success;

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

    private static final String ENTITY_API_URL = "/api/demand-data-files";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DemandDataFileRepository demandDataFileRepository;

    @Autowired
    private DemandDataFileMapper demandDataFileMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandDataFileMockMvc;

    private DemandDataFile demandDataFile;

    private DemandDataFile insertedDemandDataFile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandDataFile createEntity() {
        return new DemandDataFile()
            .uploadedDate(DEFAULT_UPLOADED_DATE)
            .uploadedTime(DEFAULT_UPLOADED_TIME)
            .fileName(DEFAULT_FILE_NAME)
            .uploadedBy(DEFAULT_UPLOADED_BY)
            .status(DEFAULT_STATUS)
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
    public static DemandDataFile createUpdatedEntity() {
        return new DemandDataFile()
            .uploadedDate(UPDATED_UPLOADED_DATE)
            .uploadedTime(UPDATED_UPLOADED_TIME)
            .fileName(UPDATED_FILE_NAME)
            .uploadedBy(UPDATED_UPLOADED_BY)
            .status(UPDATED_STATUS)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        demandDataFile = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedDemandDataFile != null) {
            demandDataFileRepository.delete(insertedDemandDataFile);
            insertedDemandDataFile = null;
        }
    }

    @Test
    @Transactional
    void createDemandDataFile() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the DemandDataFile
        DemandDataFileDTO demandDataFileDTO = demandDataFileMapper.toDto(demandDataFile);
        var returnedDemandDataFileDTO = om.readValue(
            restDemandDataFileMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandDataFileDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DemandDataFileDTO.class
        );

        // Validate the DemandDataFile in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDemandDataFile = demandDataFileMapper.toEntity(returnedDemandDataFileDTO);
        assertDemandDataFileUpdatableFieldsEquals(returnedDemandDataFile, getPersistedDemandDataFile(returnedDemandDataFile));

        insertedDemandDataFile = returnedDemandDataFile;
    }

    @Test
    @Transactional
    void createDemandDataFileWithExistingId() throws Exception {
        // Create the DemandDataFile with an existing ID
        demandDataFile.setId(1L);
        DemandDataFileDTO demandDataFileDTO = demandDataFileMapper.toDto(demandDataFile);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandDataFileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandDataFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DemandDataFile in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        demandDataFile.setCreateddBy(null);

        // Create the DemandDataFile, which fails.
        DemandDataFileDTO demandDataFileDTO = demandDataFileMapper.toDto(demandDataFile);

        restDemandDataFileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandDataFileDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        demandDataFile.setCreatedTime(null);

        // Create the DemandDataFile, which fails.
        DemandDataFileDTO demandDataFileDTO = demandDataFileMapper.toDto(demandDataFile);

        restDemandDataFileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandDataFileDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        demandDataFile.setUpdatedBy(null);

        // Create the DemandDataFile, which fails.
        DemandDataFileDTO demandDataFileDTO = demandDataFileMapper.toDto(demandDataFile);

        restDemandDataFileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandDataFileDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        demandDataFile.setUpdatedTime(null);

        // Create the DemandDataFile, which fails.
        DemandDataFileDTO demandDataFileDTO = demandDataFileMapper.toDto(demandDataFile);

        restDemandDataFileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandDataFileDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDemandDataFiles() throws Exception {
        // Initialize the database
        insertedDemandDataFile = demandDataFileRepository.saveAndFlush(demandDataFile);

        // Get all the demandDataFileList
        restDemandDataFileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandDataFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].uploadedDate").value(hasItem(DEFAULT_UPLOADED_DATE.toString())))
            .andExpect(jsonPath("$.[*].uploadedTime").value(hasItem(DEFAULT_UPLOADED_TIME.toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].uploadedBy").value(hasItem(DEFAULT_UPLOADED_BY)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getDemandDataFile() throws Exception {
        // Initialize the database
        insertedDemandDataFile = demandDataFileRepository.saveAndFlush(demandDataFile);

        // Get the demandDataFile
        restDemandDataFileMockMvc
            .perform(get(ENTITY_API_URL_ID, demandDataFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demandDataFile.getId().intValue()))
            .andExpect(jsonPath("$.uploadedDate").value(DEFAULT_UPLOADED_DATE.toString()))
            .andExpect(jsonPath("$.uploadedTime").value(DEFAULT_UPLOADED_TIME.toString()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME))
            .andExpect(jsonPath("$.uploadedBy").value(DEFAULT_UPLOADED_BY))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDemandDataFile() throws Exception {
        // Get the demandDataFile
        restDemandDataFileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDemandDataFile() throws Exception {
        // Initialize the database
        insertedDemandDataFile = demandDataFileRepository.saveAndFlush(demandDataFile);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the demandDataFile
        DemandDataFile updatedDemandDataFile = demandDataFileRepository.findById(demandDataFile.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDemandDataFile are not directly saved in db
        em.detach(updatedDemandDataFile);
        updatedDemandDataFile
            .uploadedDate(UPDATED_UPLOADED_DATE)
            .uploadedTime(UPDATED_UPLOADED_TIME)
            .fileName(UPDATED_FILE_NAME)
            .uploadedBy(UPDATED_UPLOADED_BY)
            .status(UPDATED_STATUS)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        DemandDataFileDTO demandDataFileDTO = demandDataFileMapper.toDto(updatedDemandDataFile);

        restDemandDataFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandDataFileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(demandDataFileDTO))
            )
            .andExpect(status().isOk());

        // Validate the DemandDataFile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDemandDataFileToMatchAllProperties(updatedDemandDataFile);
    }

    @Test
    @Transactional
    void putNonExistingDemandDataFile() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demandDataFile.setId(longCount.incrementAndGet());

        // Create the DemandDataFile
        DemandDataFileDTO demandDataFileDTO = demandDataFileMapper.toDto(demandDataFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandDataFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandDataFileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(demandDataFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandDataFile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemandDataFile() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demandDataFile.setId(longCount.incrementAndGet());

        // Create the DemandDataFile
        DemandDataFileDTO demandDataFileDTO = demandDataFileMapper.toDto(demandDataFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandDataFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(demandDataFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandDataFile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemandDataFile() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demandDataFile.setId(longCount.incrementAndGet());

        // Create the DemandDataFile
        DemandDataFileDTO demandDataFileDTO = demandDataFileMapper.toDto(demandDataFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandDataFileMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(demandDataFileDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandDataFile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemandDataFileWithPatch() throws Exception {
        // Initialize the database
        insertedDemandDataFile = demandDataFileRepository.saveAndFlush(demandDataFile);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the demandDataFile using partial update
        DemandDataFile partialUpdatedDemandDataFile = new DemandDataFile();
        partialUpdatedDemandDataFile.setId(demandDataFile.getId());

        partialUpdatedDemandDataFile
            .uploadedDate(UPDATED_UPLOADED_DATE)
            .uploadedTime(UPDATED_UPLOADED_TIME)
            .fileName(UPDATED_FILE_NAME)
            .uploadedBy(UPDATED_UPLOADED_BY)
            .status(UPDATED_STATUS)
            .createddBy(UPDATED_CREATEDD_BY)
            .updatedBy(UPDATED_UPDATED_BY);

        restDemandDataFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandDataFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDemandDataFile))
            )
            .andExpect(status().isOk());

        // Validate the DemandDataFile in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDemandDataFileUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDemandDataFile, demandDataFile),
            getPersistedDemandDataFile(demandDataFile)
        );
    }

    @Test
    @Transactional
    void fullUpdateDemandDataFileWithPatch() throws Exception {
        // Initialize the database
        insertedDemandDataFile = demandDataFileRepository.saveAndFlush(demandDataFile);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the demandDataFile using partial update
        DemandDataFile partialUpdatedDemandDataFile = new DemandDataFile();
        partialUpdatedDemandDataFile.setId(demandDataFile.getId());

        partialUpdatedDemandDataFile
            .uploadedDate(UPDATED_UPLOADED_DATE)
            .uploadedTime(UPDATED_UPLOADED_TIME)
            .fileName(UPDATED_FILE_NAME)
            .uploadedBy(UPDATED_UPLOADED_BY)
            .status(UPDATED_STATUS)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restDemandDataFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandDataFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDemandDataFile))
            )
            .andExpect(status().isOk());

        // Validate the DemandDataFile in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDemandDataFileUpdatableFieldsEquals(partialUpdatedDemandDataFile, getPersistedDemandDataFile(partialUpdatedDemandDataFile));
    }

    @Test
    @Transactional
    void patchNonExistingDemandDataFile() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demandDataFile.setId(longCount.incrementAndGet());

        // Create the DemandDataFile
        DemandDataFileDTO demandDataFileDTO = demandDataFileMapper.toDto(demandDataFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandDataFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demandDataFileDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(demandDataFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandDataFile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemandDataFile() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demandDataFile.setId(longCount.incrementAndGet());

        // Create the DemandDataFile
        DemandDataFileDTO demandDataFileDTO = demandDataFileMapper.toDto(demandDataFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandDataFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(demandDataFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandDataFile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemandDataFile() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        demandDataFile.setId(longCount.incrementAndGet());

        // Create the DemandDataFile
        DemandDataFileDTO demandDataFileDTO = demandDataFileMapper.toDto(demandDataFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandDataFileMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(demandDataFileDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandDataFile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemandDataFile() throws Exception {
        // Initialize the database
        insertedDemandDataFile = demandDataFileRepository.saveAndFlush(demandDataFile);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the demandDataFile
        restDemandDataFileMockMvc
            .perform(delete(ENTITY_API_URL_ID, demandDataFile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return demandDataFileRepository.count();
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

    protected DemandDataFile getPersistedDemandDataFile(DemandDataFile demandDataFile) {
        return demandDataFileRepository.findById(demandDataFile.getId()).orElseThrow();
    }

    protected void assertPersistedDemandDataFileToMatchAllProperties(DemandDataFile expectedDemandDataFile) {
        assertDemandDataFileAllPropertiesEquals(expectedDemandDataFile, getPersistedDemandDataFile(expectedDemandDataFile));
    }

    protected void assertPersistedDemandDataFileToMatchUpdatableProperties(DemandDataFile expectedDemandDataFile) {
        assertDemandDataFileAllUpdatablePropertiesEquals(expectedDemandDataFile, getPersistedDemandDataFile(expectedDemandDataFile));
    }
}
