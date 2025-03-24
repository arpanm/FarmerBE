package com.farmer.be.web.rest;

import static com.farmer.be.domain.SessionContextAsserts.*;
import static com.farmer.be.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.farmer.be.IntegrationTest;
import com.farmer.be.domain.SessionContext;
import com.farmer.be.domain.enumeration.Language;
import com.farmer.be.repository.SessionContextRepository;
import com.farmer.be.service.dto.SessionContextDTO;
import com.farmer.be.service.mapper.SessionContextMapper;
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
 * Integration tests for the {@link SessionContextResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SessionContextResourceIT {

    private static final String DEFAULT_SESSION_ID = "AAAAAAAAAA";
    private static final String UPDATED_SESSION_ID = "BBBBBBBBBB";

    private static final Language DEFAULT_LANGUAGE = Language.English;
    private static final Language UPDATED_LANGUAGE = Language.Hindi;

    private static final Boolean DEFAULT_IS_LOGGED_IN = false;
    private static final Boolean UPDATED_IS_LOGGED_IN = true;

    private static final String DEFAULT_FARMER_ID = "AAAAAAAAAA";
    private static final String UPDATED_FARMER_ID = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/session-contexts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SessionContextRepository sessionContextRepository;

    @Autowired
    private SessionContextMapper sessionContextMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSessionContextMockMvc;

    private SessionContext sessionContext;

    private SessionContext insertedSessionContext;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SessionContext createEntity() {
        return new SessionContext()
            .sessionId(DEFAULT_SESSION_ID)
            .language(DEFAULT_LANGUAGE)
            .isLoggedIn(DEFAULT_IS_LOGGED_IN)
            .farmerId(DEFAULT_FARMER_ID)
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
    public static SessionContext createUpdatedEntity() {
        return new SessionContext()
            .sessionId(UPDATED_SESSION_ID)
            .language(UPDATED_LANGUAGE)
            .isLoggedIn(UPDATED_IS_LOGGED_IN)
            .farmerId(UPDATED_FARMER_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
    }

    @BeforeEach
    public void initTest() {
        sessionContext = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedSessionContext != null) {
            sessionContextRepository.delete(insertedSessionContext);
            insertedSessionContext = null;
        }
    }

    @Test
    @Transactional
    void createSessionContext() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SessionContext
        SessionContextDTO sessionContextDTO = sessionContextMapper.toDto(sessionContext);
        var returnedSessionContextDTO = om.readValue(
            restSessionContextMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sessionContextDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SessionContextDTO.class
        );

        // Validate the SessionContext in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSessionContext = sessionContextMapper.toEntity(returnedSessionContextDTO);
        assertSessionContextUpdatableFieldsEquals(returnedSessionContext, getPersistedSessionContext(returnedSessionContext));

        insertedSessionContext = returnedSessionContext;
    }

    @Test
    @Transactional
    void createSessionContextWithExistingId() throws Exception {
        // Create the SessionContext with an existing ID
        sessionContext.setId(1L);
        SessionContextDTO sessionContextDTO = sessionContextMapper.toDto(sessionContext);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSessionContextMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sessionContextDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SessionContext in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreateddByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sessionContext.setCreateddBy(null);

        // Create the SessionContext, which fails.
        SessionContextDTO sessionContextDTO = sessionContextMapper.toDto(sessionContext);

        restSessionContextMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sessionContextDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sessionContext.setCreatedTime(null);

        // Create the SessionContext, which fails.
        SessionContextDTO sessionContextDTO = sessionContextMapper.toDto(sessionContext);

        restSessionContextMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sessionContextDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedByIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sessionContext.setUpdatedBy(null);

        // Create the SessionContext, which fails.
        SessionContextDTO sessionContextDTO = sessionContextMapper.toDto(sessionContext);

        restSessionContextMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sessionContextDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sessionContext.setUpdatedTime(null);

        // Create the SessionContext, which fails.
        SessionContextDTO sessionContextDTO = sessionContextMapper.toDto(sessionContext);

        restSessionContextMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sessionContextDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSessionContexts() throws Exception {
        // Initialize the database
        insertedSessionContext = sessionContextRepository.saveAndFlush(sessionContext);

        // Get all the sessionContextList
        restSessionContextMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sessionContext.getId().intValue())))
            .andExpect(jsonPath("$.[*].sessionId").value(hasItem(DEFAULT_SESSION_ID)))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].isLoggedIn").value(hasItem(DEFAULT_IS_LOGGED_IN)))
            .andExpect(jsonPath("$.[*].farmerId").value(hasItem(DEFAULT_FARMER_ID)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].createddBy").value(hasItem(DEFAULT_CREATEDD_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTime").value(hasItem(DEFAULT_UPDATED_TIME.toString())));
    }

    @Test
    @Transactional
    void getSessionContext() throws Exception {
        // Initialize the database
        insertedSessionContext = sessionContextRepository.saveAndFlush(sessionContext);

        // Get the sessionContext
        restSessionContextMockMvc
            .perform(get(ENTITY_API_URL_ID, sessionContext.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sessionContext.getId().intValue()))
            .andExpect(jsonPath("$.sessionId").value(DEFAULT_SESSION_ID))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.isLoggedIn").value(DEFAULT_IS_LOGGED_IN))
            .andExpect(jsonPath("$.farmerId").value(DEFAULT_FARMER_ID))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.createddBy").value(DEFAULT_CREATEDD_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTime").value(DEFAULT_UPDATED_TIME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSessionContext() throws Exception {
        // Get the sessionContext
        restSessionContextMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSessionContext() throws Exception {
        // Initialize the database
        insertedSessionContext = sessionContextRepository.saveAndFlush(sessionContext);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sessionContext
        SessionContext updatedSessionContext = sessionContextRepository.findById(sessionContext.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSessionContext are not directly saved in db
        em.detach(updatedSessionContext);
        updatedSessionContext
            .sessionId(UPDATED_SESSION_ID)
            .language(UPDATED_LANGUAGE)
            .isLoggedIn(UPDATED_IS_LOGGED_IN)
            .farmerId(UPDATED_FARMER_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);
        SessionContextDTO sessionContextDTO = sessionContextMapper.toDto(updatedSessionContext);

        restSessionContextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sessionContextDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sessionContextDTO))
            )
            .andExpect(status().isOk());

        // Validate the SessionContext in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSessionContextToMatchAllProperties(updatedSessionContext);
    }

    @Test
    @Transactional
    void putNonExistingSessionContext() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sessionContext.setId(longCount.incrementAndGet());

        // Create the SessionContext
        SessionContextDTO sessionContextDTO = sessionContextMapper.toDto(sessionContext);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSessionContextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sessionContextDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sessionContextDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SessionContext in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSessionContext() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sessionContext.setId(longCount.incrementAndGet());

        // Create the SessionContext
        SessionContextDTO sessionContextDTO = sessionContextMapper.toDto(sessionContext);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSessionContextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sessionContextDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SessionContext in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSessionContext() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sessionContext.setId(longCount.incrementAndGet());

        // Create the SessionContext
        SessionContextDTO sessionContextDTO = sessionContextMapper.toDto(sessionContext);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSessionContextMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sessionContextDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SessionContext in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSessionContextWithPatch() throws Exception {
        // Initialize the database
        insertedSessionContext = sessionContextRepository.saveAndFlush(sessionContext);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sessionContext using partial update
        SessionContext partialUpdatedSessionContext = new SessionContext();
        partialUpdatedSessionContext.setId(sessionContext.getId());

        partialUpdatedSessionContext.createddBy(UPDATED_CREATEDD_BY).createdTime(UPDATED_CREATED_TIME);

        restSessionContextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSessionContext.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSessionContext))
            )
            .andExpect(status().isOk());

        // Validate the SessionContext in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSessionContextUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSessionContext, sessionContext),
            getPersistedSessionContext(sessionContext)
        );
    }

    @Test
    @Transactional
    void fullUpdateSessionContextWithPatch() throws Exception {
        // Initialize the database
        insertedSessionContext = sessionContextRepository.saveAndFlush(sessionContext);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sessionContext using partial update
        SessionContext partialUpdatedSessionContext = new SessionContext();
        partialUpdatedSessionContext.setId(sessionContext.getId());

        partialUpdatedSessionContext
            .sessionId(UPDATED_SESSION_ID)
            .language(UPDATED_LANGUAGE)
            .isLoggedIn(UPDATED_IS_LOGGED_IN)
            .farmerId(UPDATED_FARMER_ID)
            .isActive(UPDATED_IS_ACTIVE)
            .createddBy(UPDATED_CREATEDD_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTime(UPDATED_UPDATED_TIME);

        restSessionContextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSessionContext.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSessionContext))
            )
            .andExpect(status().isOk());

        // Validate the SessionContext in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSessionContextUpdatableFieldsEquals(partialUpdatedSessionContext, getPersistedSessionContext(partialUpdatedSessionContext));
    }

    @Test
    @Transactional
    void patchNonExistingSessionContext() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sessionContext.setId(longCount.incrementAndGet());

        // Create the SessionContext
        SessionContextDTO sessionContextDTO = sessionContextMapper.toDto(sessionContext);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSessionContextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sessionContextDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sessionContextDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SessionContext in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSessionContext() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sessionContext.setId(longCount.incrementAndGet());

        // Create the SessionContext
        SessionContextDTO sessionContextDTO = sessionContextMapper.toDto(sessionContext);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSessionContextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sessionContextDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SessionContext in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSessionContext() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sessionContext.setId(longCount.incrementAndGet());

        // Create the SessionContext
        SessionContextDTO sessionContextDTO = sessionContextMapper.toDto(sessionContext);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSessionContextMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sessionContextDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SessionContext in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSessionContext() throws Exception {
        // Initialize the database
        insertedSessionContext = sessionContextRepository.saveAndFlush(sessionContext);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sessionContext
        restSessionContextMockMvc
            .perform(delete(ENTITY_API_URL_ID, sessionContext.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sessionContextRepository.count();
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

    protected SessionContext getPersistedSessionContext(SessionContext sessionContext) {
        return sessionContextRepository.findById(sessionContext.getId()).orElseThrow();
    }

    protected void assertPersistedSessionContextToMatchAllProperties(SessionContext expectedSessionContext) {
        assertSessionContextAllPropertiesEquals(expectedSessionContext, getPersistedSessionContext(expectedSessionContext));
    }

    protected void assertPersistedSessionContextToMatchUpdatableProperties(SessionContext expectedSessionContext) {
        assertSessionContextAllUpdatablePropertiesEquals(expectedSessionContext, getPersistedSessionContext(expectedSessionContext));
    }
}
