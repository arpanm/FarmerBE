package com.farmer.be.web.rest;

import com.farmer.be.repository.SupplyConfirmationRepository;
import com.farmer.be.service.SupplyConfirmationService;
import com.farmer.be.service.dto.SupplyConfirmationDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.SupplyConfirmation}.
 */
@RestController
@RequestMapping("/api/supply-confirmations")
public class SupplyConfirmationResource {

    private static final Logger LOG = LoggerFactory.getLogger(SupplyConfirmationResource.class);

    private static final String ENTITY_NAME = "supplyConfirmation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupplyConfirmationService supplyConfirmationService;

    private final SupplyConfirmationRepository supplyConfirmationRepository;

    public SupplyConfirmationResource(
        SupplyConfirmationService supplyConfirmationService,
        SupplyConfirmationRepository supplyConfirmationRepository
    ) {
        this.supplyConfirmationService = supplyConfirmationService;
        this.supplyConfirmationRepository = supplyConfirmationRepository;
    }

    /**
     * {@code POST  /supply-confirmations} : Create a new supplyConfirmation.
     *
     * @param supplyConfirmationDTO the supplyConfirmationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supplyConfirmationDTO, or with status {@code 400 (Bad Request)} if the supplyConfirmation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SupplyConfirmationDTO> createSupplyConfirmation(@Valid @RequestBody SupplyConfirmationDTO supplyConfirmationDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save SupplyConfirmation : {}", supplyConfirmationDTO);
        if (supplyConfirmationDTO.getId() != null) {
            throw new BadRequestAlertException("A new supplyConfirmation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        supplyConfirmationDTO = supplyConfirmationService.save(supplyConfirmationDTO);
        return ResponseEntity.created(new URI("/api/supply-confirmations/" + supplyConfirmationDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, supplyConfirmationDTO.getId().toString()))
            .body(supplyConfirmationDTO);
    }

    /**
     * {@code PUT  /supply-confirmations/:id} : Updates an existing supplyConfirmation.
     *
     * @param id the id of the supplyConfirmationDTO to save.
     * @param supplyConfirmationDTO the supplyConfirmationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supplyConfirmationDTO,
     * or with status {@code 400 (Bad Request)} if the supplyConfirmationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supplyConfirmationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SupplyConfirmationDTO> updateSupplyConfirmation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SupplyConfirmationDTO supplyConfirmationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update SupplyConfirmation : {}, {}", id, supplyConfirmationDTO);
        if (supplyConfirmationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, supplyConfirmationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!supplyConfirmationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        supplyConfirmationDTO = supplyConfirmationService.update(supplyConfirmationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supplyConfirmationDTO.getId().toString()))
            .body(supplyConfirmationDTO);
    }

    /**
     * {@code PATCH  /supply-confirmations/:id} : Partial updates given fields of an existing supplyConfirmation, field will ignore if it is null
     *
     * @param id the id of the supplyConfirmationDTO to save.
     * @param supplyConfirmationDTO the supplyConfirmationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supplyConfirmationDTO,
     * or with status {@code 400 (Bad Request)} if the supplyConfirmationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the supplyConfirmationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the supplyConfirmationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SupplyConfirmationDTO> partialUpdateSupplyConfirmation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SupplyConfirmationDTO supplyConfirmationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SupplyConfirmation partially : {}, {}", id, supplyConfirmationDTO);
        if (supplyConfirmationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, supplyConfirmationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!supplyConfirmationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SupplyConfirmationDTO> result = supplyConfirmationService.partialUpdate(supplyConfirmationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supplyConfirmationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /supply-confirmations} : get all the supplyConfirmations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supplyConfirmations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SupplyConfirmationDTO>> getAllSupplyConfirmations(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of SupplyConfirmations");
        Page<SupplyConfirmationDTO> page = supplyConfirmationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /supply-confirmations/:id} : get the "id" supplyConfirmation.
     *
     * @param id the id of the supplyConfirmationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supplyConfirmationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SupplyConfirmationDTO> getSupplyConfirmation(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SupplyConfirmation : {}", id);
        Optional<SupplyConfirmationDTO> supplyConfirmationDTO = supplyConfirmationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supplyConfirmationDTO);
    }

    /**
     * {@code DELETE  /supply-confirmations/:id} : delete the "id" supplyConfirmation.
     *
     * @param id the id of the supplyConfirmationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplyConfirmation(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SupplyConfirmation : {}", id);
        supplyConfirmationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
