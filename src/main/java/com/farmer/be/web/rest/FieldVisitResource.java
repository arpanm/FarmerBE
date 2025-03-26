package com.farmer.be.web.rest;

import com.farmer.be.repository.FieldVisitRepository;
import com.farmer.be.service.FieldVisitService;
import com.farmer.be.service.dto.FieldVisitDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.FieldVisit}.
 */
@RestController
@RequestMapping("/api/field-visits")
public class FieldVisitResource {

    private static final Logger LOG = LoggerFactory.getLogger(FieldVisitResource.class);

    private static final String ENTITY_NAME = "fieldVisit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldVisitService fieldVisitService;

    private final FieldVisitRepository fieldVisitRepository;

    public FieldVisitResource(FieldVisitService fieldVisitService, FieldVisitRepository fieldVisitRepository) {
        this.fieldVisitService = fieldVisitService;
        this.fieldVisitRepository = fieldVisitRepository;
    }

    /**
     * {@code POST  /field-visits} : Create a new fieldVisit.
     *
     * @param fieldVisitDTO the fieldVisitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldVisitDTO, or with status {@code 400 (Bad Request)} if the fieldVisit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FieldVisitDTO> createFieldVisit(@Valid @RequestBody FieldVisitDTO fieldVisitDTO) throws URISyntaxException {
        LOG.debug("REST request to save FieldVisit : {}", fieldVisitDTO);
        if (fieldVisitDTO.getId() != null) {
            throw new BadRequestAlertException("A new fieldVisit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fieldVisitDTO = fieldVisitService.save(fieldVisitDTO);
        return ResponseEntity.created(new URI("/api/field-visits/" + fieldVisitDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, fieldVisitDTO.getId().toString()))
            .body(fieldVisitDTO);
    }

    /**
     * {@code PUT  /field-visits/:id} : Updates an existing fieldVisit.
     *
     * @param id the id of the fieldVisitDTO to save.
     * @param fieldVisitDTO the fieldVisitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldVisitDTO,
     * or with status {@code 400 (Bad Request)} if the fieldVisitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldVisitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FieldVisitDTO> updateFieldVisit(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FieldVisitDTO fieldVisitDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update FieldVisit : {}, {}", id, fieldVisitDTO);
        if (fieldVisitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldVisitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldVisitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fieldVisitDTO = fieldVisitService.update(fieldVisitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldVisitDTO.getId().toString()))
            .body(fieldVisitDTO);
    }

    /**
     * {@code PATCH  /field-visits/:id} : Partial updates given fields of an existing fieldVisit, field will ignore if it is null
     *
     * @param id the id of the fieldVisitDTO to save.
     * @param fieldVisitDTO the fieldVisitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldVisitDTO,
     * or with status {@code 400 (Bad Request)} if the fieldVisitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fieldVisitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fieldVisitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FieldVisitDTO> partialUpdateFieldVisit(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FieldVisitDTO fieldVisitDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FieldVisit partially : {}, {}", id, fieldVisitDTO);
        if (fieldVisitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fieldVisitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fieldVisitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FieldVisitDTO> result = fieldVisitService.partialUpdate(fieldVisitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldVisitDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /field-visits} : get all the fieldVisits.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fieldVisits in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FieldVisitDTO>> getAllFieldVisits(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of FieldVisits");
        Page<FieldVisitDTO> page = fieldVisitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /field-visits/:id} : get the "id" fieldVisit.
     *
     * @param id the id of the fieldVisitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldVisitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FieldVisitDTO> getFieldVisit(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FieldVisit : {}", id);
        Optional<FieldVisitDTO> fieldVisitDTO = fieldVisitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fieldVisitDTO);
    }

    /**
     * {@code DELETE  /field-visits/:id} : delete the "id" fieldVisit.
     *
     * @param id the id of the fieldVisitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFieldVisit(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete FieldVisit : {}", id);
        fieldVisitService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
