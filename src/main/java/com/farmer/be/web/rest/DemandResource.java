package com.farmer.be.web.rest;

import com.farmer.be.repository.DemandRepository;
import com.farmer.be.service.DemandService;
import com.farmer.be.service.dto.DemandDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.Demand}.
 */
@RestController
@RequestMapping("/api/demands")
public class DemandResource {

    private static final Logger LOG = LoggerFactory.getLogger(DemandResource.class);

    private static final String ENTITY_NAME = "demand";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemandService demandService;

    private final DemandRepository demandRepository;

    public DemandResource(DemandService demandService, DemandRepository demandRepository) {
        this.demandService = demandService;
        this.demandRepository = demandRepository;
    }

    /**
     * {@code POST  /demands} : Create a new demand.
     *
     * @param demandDTO the demandDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demandDTO, or with status {@code 400 (Bad Request)} if the demand has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DemandDTO> createDemand(@Valid @RequestBody DemandDTO demandDTO) throws URISyntaxException {
        LOG.debug("REST request to save Demand : {}", demandDTO);
        if (demandDTO.getId() != null) {
            throw new BadRequestAlertException("A new demand cannot already have an ID", ENTITY_NAME, "idexists");
        }
        demandDTO = demandService.save(demandDTO);
        return ResponseEntity.created(new URI("/api/demands/" + demandDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, demandDTO.getId().toString()))
            .body(demandDTO);
    }

    /**
     * {@code PUT  /demands/:id} : Updates an existing demand.
     *
     * @param id the id of the demandDTO to save.
     * @param demandDTO the demandDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandDTO,
     * or with status {@code 400 (Bad Request)} if the demandDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demandDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DemandDTO> updateDemand(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DemandDTO demandDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Demand : {}, {}", id, demandDTO);
        if (demandDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        demandDTO = demandService.update(demandDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandDTO.getId().toString()))
            .body(demandDTO);
    }

    /**
     * {@code PATCH  /demands/:id} : Partial updates given fields of an existing demand, field will ignore if it is null
     *
     * @param id the id of the demandDTO to save.
     * @param demandDTO the demandDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandDTO,
     * or with status {@code 400 (Bad Request)} if the demandDTO is not valid,
     * or with status {@code 404 (Not Found)} if the demandDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the demandDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DemandDTO> partialUpdateDemand(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DemandDTO demandDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Demand partially : {}, {}", id, demandDTO);
        if (demandDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DemandDTO> result = demandService.partialUpdate(demandDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /demands} : get all the demands.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demands in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DemandDTO>> getAllDemands(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Demands");
        Page<DemandDTO> page = demandService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /demands/:id} : get the "id" demand.
     *
     * @param id the id of the demandDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demandDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DemandDTO> getDemand(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Demand : {}", id);
        Optional<DemandDTO> demandDTO = demandService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demandDTO);
    }

    /**
     * {@code DELETE  /demands/:id} : delete the "id" demand.
     *
     * @param id the id of the demandDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemand(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Demand : {}", id);
        demandService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
