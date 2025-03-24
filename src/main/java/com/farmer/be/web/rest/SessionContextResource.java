package com.farmer.be.web.rest;

import com.farmer.be.repository.SessionContextRepository;
import com.farmer.be.service.SessionContextService;
import com.farmer.be.service.dto.SessionContextDTO;
import com.farmer.be.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.farmer.be.domain.SessionContext}.
 */
@RestController
@RequestMapping("/api/session-contexts")
public class SessionContextResource {

    private static final Logger LOG = LoggerFactory.getLogger(SessionContextResource.class);

    private static final String ENTITY_NAME = "sessionContext";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SessionContextService sessionContextService;

    private final SessionContextRepository sessionContextRepository;

    public SessionContextResource(SessionContextService sessionContextService, SessionContextRepository sessionContextRepository) {
        this.sessionContextService = sessionContextService;
        this.sessionContextRepository = sessionContextRepository;
    }

    /**
     * {@code POST  /session-contexts} : Create a new sessionContext.
     *
     * @param sessionContextDTO the sessionContextDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sessionContextDTO, or with status {@code 400 (Bad Request)} if the sessionContext has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SessionContextDTO> createSessionContext(@Valid @RequestBody SessionContextDTO sessionContextDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save SessionContext : {}", sessionContextDTO);
        if (sessionContextDTO.getId() != null) {
            throw new BadRequestAlertException("A new sessionContext cannot already have an ID", ENTITY_NAME, "idexists");
        }
        sessionContextDTO = sessionContextService.save(sessionContextDTO);
        return ResponseEntity.created(new URI("/api/session-contexts/" + sessionContextDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, sessionContextDTO.getId().toString()))
            .body(sessionContextDTO);
    }

    /**
     * {@code PUT  /session-contexts/:id} : Updates an existing sessionContext.
     *
     * @param id the id of the sessionContextDTO to save.
     * @param sessionContextDTO the sessionContextDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sessionContextDTO,
     * or with status {@code 400 (Bad Request)} if the sessionContextDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sessionContextDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SessionContextDTO> updateSessionContext(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SessionContextDTO sessionContextDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update SessionContext : {}, {}", id, sessionContextDTO);
        if (sessionContextDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sessionContextDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sessionContextRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        sessionContextDTO = sessionContextService.update(sessionContextDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sessionContextDTO.getId().toString()))
            .body(sessionContextDTO);
    }

    /**
     * {@code PATCH  /session-contexts/:id} : Partial updates given fields of an existing sessionContext, field will ignore if it is null
     *
     * @param id the id of the sessionContextDTO to save.
     * @param sessionContextDTO the sessionContextDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sessionContextDTO,
     * or with status {@code 400 (Bad Request)} if the sessionContextDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sessionContextDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sessionContextDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SessionContextDTO> partialUpdateSessionContext(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SessionContextDTO sessionContextDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SessionContext partially : {}, {}", id, sessionContextDTO);
        if (sessionContextDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sessionContextDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sessionContextRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SessionContextDTO> result = sessionContextService.partialUpdate(sessionContextDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sessionContextDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /session-contexts} : get all the sessionContexts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sessionContexts in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SessionContextDTO>> getAllSessionContexts(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of SessionContexts");
        Page<SessionContextDTO> page = sessionContextService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /session-contexts/:id} : get the "id" sessionContext.
     *
     * @param id the id of the sessionContextDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sessionContextDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SessionContextDTO> getSessionContext(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SessionContext : {}", id);
        Optional<SessionContextDTO> sessionContextDTO = sessionContextService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sessionContextDTO);
    }

    /**
     * {@code DELETE  /session-contexts/:id} : delete the "id" sessionContext.
     *
     * @param id the id of the sessionContextDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSessionContext(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SessionContext : {}", id);
        sessionContextService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
