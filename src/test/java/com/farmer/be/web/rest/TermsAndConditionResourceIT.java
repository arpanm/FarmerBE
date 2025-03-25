package com.farmer.be.web.rest;

import static com.farmer.be.domain.TermsAndConditionAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.TermsAndCondition;
import com.farmer.be.repository.TermsAndConditionRepository;
import com.farmer.be.service.dto.TermsAndConditionDTO;
import com.farmer.be.service.mapper.TermsAndConditionMapper;
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
 * Integration tests for the {@link TermsAndConditionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TermsAndConditionResourceIT {

    private static final String DEFAULT_TERMS_LINK = "AAAAAAAAAA";
    private static final String UPDATED_TERMS_LINK = "BBBBBBBBBB";

    private static final Instant DEFAULT_AGGREED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_AGGREED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/terms-and-conditions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TermsAndConditionRepository termsAndConditionRepository;

    @Autowired
    private TermsAndConditionMapper termsAndConditionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTermsAndConditionMockMvc;

    private TermsAndCondition termsAndCondition;

    private TermsAndCondition insertedTermsAndCondition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TermsAndCondition createEntity() {
        return new TermsAndCondition()
            .termsLink(DEFAULT_TERMS_LINK)
            .aggreedOn(DEFAULT_AGGREED_ON)
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
    public static TermsAndCondition createUpdatedEntity() {
        return new TermsAndCondition()
            .termsLink(UPDATED_TERMS_LINK)
            .aggreedOn(UPDATED_AGGREED_ON)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        termsAndCondition = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedTermsAndCondition != null) {
            termsAndConditionRepository.delete(insertedTermsAndCondition);
            insertedTermsAndCondition = null;
        }
    }

    @Test
    @Transactional
    void createTermsAndCondition() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TermsAndCondition
        TermsAndConditionDTO termsAndConditionDTO = termsAndConditionMapper.toDto(termsAndCondition);
        var returnedTermsAndConditionDTO = om.readValue(
            restTermsAndConditionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(termsAndConditionDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TermsAndConditionDTO.class
        );

        // Validate the TermsAndCondition in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTermsAndCondition = termsAndConditionMapper.toEntity(returnedTermsAndConditionDTO);
        assertTermsAndConditionUpdatableFieldsEquals(returnedTermsAndCondition, getPersistedTermsAndCondition(returnedTermsAndCondition));

        insertedTermsAndCondition = returnedTermsAndCondition;
    }

    @Test
    @Transactional
    void createTermsAndConditionWithExistingId() throws Exception {
        // Create the TermsAndCondition with an existing ID
        termsAndCondition.setId(1L);
        TermsAndConditionDTO termsAndConditionDTO = termsAndConditionMapper.toDto(termsAndCondition);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTermsAndConditionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(termsAndConditionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TermsAndCondition in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        termsAndCondition.setCreateddBy(null);

        // Create the TermsAndCondition, which fails.
        TermsAndConditionDTO termsAndConditionDTO = termsAndConditionMapper.toDto(termsAndCondition);

        restTermsAndConditionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(termsAndConditionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        termsAndCondition.setCreatedTime(null);

        // Create the TermsAndCondition, which fails.
        TermsAndConditionDTO termsAndConditionDTO = termsAndConditionMapper.toDto(termsAndCondition);

        restTermsAndConditionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(termsAndConditionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        termsAndCondition.setUpdatedBy(null);

        // Create the TermsAndCondition, which fails.
        TermsAndConditionDTO termsAndConditionDTO = termsAndConditionMapper.toDto(termsAndCondition);

        restTermsAndConditionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(termsAndConditionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        termsAndCondition.setUpdatedTime(null);

        // Create the TermsAndCondition, which fails.
        TermsAndConditionDTO termsAndConditionDTO = termsAndConditionMapper.toDto(termsAndCondition);

        restTermsAndConditionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(termsAndConditionDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTermsAndConditions() throws Exception {
        // Initialize the database
        insertedTermsAndCondition = termsAndConditionRepository.saveAndFlush(termsAndCondition);

        // Get all the termsAndConditionList
        restTermsAndConditionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(termsAndCondition.getId().intValue())))
            .andExpect(jsonPath("$.[*].termsLink").value(hasItem(DEFAULT_TERMS_LINK)))
            .andExpect(jsonPath("$.[*].aggreedOn").value(hasItem(DEFAULT_AGGREED_ON.toString())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getTermsAndCondition() throws Exception {
        // Initialize the database
        insertedTermsAndCondition = termsAndConditionRepository.saveAndFlush(termsAndCondition);

        // Get the termsAndCondition
        restTermsAndConditionMockMvc
            .perform(get(ENTITY_API_URL_ID, termsAndCondition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(termsAndCondition.getId().intValue()))
            .andExpect(jsonPath("$.termsLink").value(DEFAULT_TERMS_LINK))
            .andExpect(jsonPath("$.aggreedOn").value(DEFAULT_AGGREED_ON.toString()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTermsAndCondition() throws Exception {
        // Get the termsAndCondition
        restTermsAndConditionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTermsAndCondition() throws Exception {
        // Initialize the database
        insertedTermsAndCondition = termsAndConditionRepository.saveAndFlush(termsAndCondition);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the termsAndCondition
        TermsAndCondition updatedTermsAndCondition = termsAndConditionRepository.findById(termsAndCondition.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTermsAndCondition are not directly saved in db
        em.detach(updatedTermsAndCondition);
        updatedTermsAndCondition
            .termsLink(UPDATED_TERMS_LINK)
            .aggreedOn(UPDATED_AGGREED_ON)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        TermsAndConditionDTO termsAndConditionDTO = termsAndConditionMapper.toDto(updatedTermsAndCondition);

        restTermsAndConditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, termsAndConditionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(termsAndConditionDTO))
            )
            .andExpect(status().isOk());

        // Validate the TermsAndCondition in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTermsAndConditionToMatchAllProperties(updatedTermsAndCondition);
    }

    @Test
    @Transactional
    void putNonExistingTermsAndCondition() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termsAndCondition.setId(longCount.incrementAndGet());

        // Create the TermsAndCondition
        TermsAndConditionDTO termsAndConditionDTO = termsAndConditionMapper.toDto(termsAndCondition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTermsAndConditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, termsAndConditionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(termsAndConditionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermsAndCondition in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTermsAndCondition() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termsAndCondition.setId(longCount.incrementAndGet());

        // Create the TermsAndCondition
        TermsAndConditionDTO termsAndConditionDTO = termsAndConditionMapper.toDto(termsAndCondition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermsAndConditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(termsAndConditionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermsAndCondition in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTermsAndCondition() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termsAndCondition.setId(longCount.incrementAndGet());

        // Create the TermsAndCondition
        TermsAndConditionDTO termsAndConditionDTO = termsAndConditionMapper.toDto(termsAndCondition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermsAndConditionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(termsAndConditionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TermsAndCondition in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTermsAndConditionWithPatch() throws Exception {
        // Initialize the database
        insertedTermsAndCondition = termsAndConditionRepository.saveAndFlush(termsAndCondition);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the termsAndCondition using partial update
        TermsAndCondition partialUpdatedTermsAndCondition = new TermsAndCondition();
        partialUpdatedTermsAndCondition.setId(termsAndCondition.getId());

        partialUpdatedTermsAndCondition.termsLink(UPDATED_TERMS_LINK).isActive(UPDATED_IS_ACTIVE).createddBy(UPDATED_CREATEDD_BY);

        restTermsAndConditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTermsAndCondition.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTermsAndCondition))
            )
            .andExpect(status().isOk());

        // Validate the TermsAndCondition in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTermsAndConditionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTermsAndCondition, termsAndCondition),
            getPersistedTermsAndCondition(termsAndCondition)
        );
    }

    @Test
    @Transactional
    void fullUpdateTermsAndConditionWithPatch() throws Exception {
        // Initialize the database
        insertedTermsAndCondition = termsAndConditionRepository.saveAndFlush(termsAndCondition);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the termsAndCondition using partial update
        TermsAndCondition partialUpdatedTermsAndCondition = new TermsAndCondition();
        partialUpdatedTermsAndCondition.setId(termsAndCondition.getId());

        partialUpdatedTermsAndCondition
            .termsLink(UPDATED_TERMS_LINK)
            .aggreedOn(UPDATED_AGGREED_ON)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restTermsAndConditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTermsAndCondition.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTermsAndCondition))
            )
            .andExpect(status().isOk());

        // Validate the TermsAndCondition in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTermsAndConditionUpdatableFieldsEquals(
            partialUpdatedTermsAndCondition,
            getPersistedTermsAndCondition(partialUpdatedTermsAndCondition)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTermsAndCondition() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termsAndCondition.setId(longCount.incrementAndGet());

        // Create the TermsAndCondition
        TermsAndConditionDTO termsAndConditionDTO = termsAndConditionMapper.toDto(termsAndCondition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTermsAndConditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, termsAndConditionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(termsAndConditionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermsAndCondition in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTermsAndCondition() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termsAndCondition.setId(longCount.incrementAndGet());

        // Create the TermsAndCondition
        TermsAndConditionDTO termsAndConditionDTO = termsAndConditionMapper.toDto(termsAndCondition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermsAndConditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(termsAndConditionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TermsAndCondition in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTermsAndCondition() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        termsAndCondition.setId(longCount.incrementAndGet());

        // Create the TermsAndCondition
        TermsAndConditionDTO termsAndConditionDTO = termsAndConditionMapper.toDto(termsAndCondition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTermsAndConditionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(termsAndConditionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TermsAndCondition in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTermsAndCondition() throws Exception {
        // Initialize the database
        insertedTermsAndCondition = termsAndConditionRepository.saveAndFlush(termsAndCondition);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the termsAndCondition
        restTermsAndConditionMockMvc
            .perform(delete(ENTITY_API_URL_ID, termsAndCondition.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return termsAndConditionRepository.count();
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

    protected TermsAndCondition getPersistedTermsAndCondition(TermsAndCondition termsAndCondition) {
        return termsAndConditionRepository.findById(termsAndCondition.getId()).orElseThrow();
    }

    protected void assertPersistedTermsAndConditionToMatchAllProperties(TermsAndCondition expectedTermsAndCondition) {
        assertTermsAndConditionAllPropertiesEquals(expectedTermsAndCondition, getPersistedTermsAndCondition(expectedTermsAndCondition));
    }

    protected void assertPersistedTermsAndConditionToMatchUpdatableProperties(TermsAndCondition expectedTermsAndCondition) {
        assertTermsAndConditionAllUpdatablePropertiesEquals(
            expectedTermsAndCondition,
            getPersistedTermsAndCondition(expectedTermsAndCondition)
        );
    }
}
