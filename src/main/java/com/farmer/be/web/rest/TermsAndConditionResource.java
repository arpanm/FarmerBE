package com.farmer.be.web.rest;

import com.farmer.be.repository.TermsAndConditionRepository;
import com.farmer.be.service.TermsAndConditionService;
import com.farmer.be.service.dto.TermsAndConditionDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.TermsAndCondition}.
 */
@RestController
@RequestMapping("/api/terms-and-conditions")
public class TermsAndConditionResource {

    private static final Logger LOG = LoggerFactory.getLogger(TermsAndConditionResource.class);

    private static final String ENTITY_NAME = "termsAndCondition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TermsAndConditionService termsAndConditionService;

    private final TermsAndConditionRepository termsAndConditionRepository;

    public TermsAndConditionResource(
        TermsAndConditionService termsAndConditionService,
        TermsAndConditionRepository termsAndConditionRepository
    ) {
        this.termsAndConditionService = termsAndConditionService;
        this.termsAndConditionRepository = termsAndConditionRepository;
    }

    /**
     * {@code POST  /terms-and-conditions} : Create a new termsAndCondition.
     *
     * @param termsAndConditionDTO the termsAndConditionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new termsAndConditionDTO, or with status {@code 400 (Bad Request)} if the termsAndCondition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TermsAndConditionDTO> createTermsAndCondition(@Valid @RequestBody TermsAndConditionDTO termsAndConditionDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save TermsAndCondition : {}", termsAndConditionDTO);
        if (termsAndConditionDTO.getId() != null) {
            throw new BadRequestAlertException("A new termsAndCondition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        termsAndConditionDTO = termsAndConditionService.save(termsAndConditionDTO);
        return ResponseEntity.created(new URI("/api/terms-and-conditions/" + termsAndConditionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, termsAndConditionDTO.getId().toString()))
            .body(termsAndConditionDTO);
    }

    /**
     * {@code PUT  /terms-and-conditions/:id} : Updates an existing termsAndCondition.
     *
     * @param id the id of the termsAndConditionDTO to save.
     * @param termsAndConditionDTO the termsAndConditionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated termsAndConditionDTO,
     * or with status {@code 400 (Bad Request)} if the termsAndConditionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the termsAndConditionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TermsAndConditionDTO> updateTermsAndCondition(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TermsAndConditionDTO termsAndConditionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update TermsAndCondition : {}, {}", id, termsAndConditionDTO);
        if (termsAndConditionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, termsAndConditionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!termsAndConditionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        termsAndConditionDTO = termsAndConditionService.update(termsAndConditionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, termsAndConditionDTO.getId().toString()))
            .body(termsAndConditionDTO);
    }

    /**
     * {@code PATCH  /terms-and-conditions/:id} : Partial updates given fields of an existing termsAndCondition, field will ignore if it is null
     *
     * @param id the id of the termsAndConditionDTO to save.
     * @param termsAndConditionDTO the termsAndConditionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated termsAndConditionDTO,
     * or with status {@code 400 (Bad Request)} if the termsAndConditionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the termsAndConditionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the termsAndConditionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TermsAndConditionDTO> partialUpdateTermsAndCondition(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TermsAndConditionDTO termsAndConditionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update TermsAndCondition partially : {}, {}", id, termsAndConditionDTO);
        if (termsAndConditionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, termsAndConditionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!termsAndConditionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TermsAndConditionDTO> result = termsAndConditionService.partialUpdate(termsAndConditionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, termsAndConditionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /terms-and-conditions} : get all the termsAndConditions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of termsAndConditions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TermsAndConditionDTO>> getAllTermsAndConditions(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of TermsAndConditions");
        Page<TermsAndConditionDTO> page = termsAndConditionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /terms-and-conditions/:id} : get the "id" termsAndCondition.
     *
     * @param id the id of the termsAndConditionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the termsAndConditionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TermsAndConditionDTO> getTermsAndCondition(@PathVariable("id") Long id) {
        LOG.debug("REST request to get TermsAndCondition : {}", id);
        Optional<TermsAndConditionDTO> termsAndConditionDTO = termsAndConditionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(termsAndConditionDTO);
    }

    /**
     * {@code DELETE  /terms-and-conditions/:id} : delete the "id" termsAndCondition.
     *
     * @param id the id of the termsAndConditionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTermsAndCondition(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete TermsAndCondition : {}", id);
        termsAndConditionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
