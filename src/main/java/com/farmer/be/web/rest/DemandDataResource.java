package com.farmer.be.web.rest;

import com.farmer.be.repository.DemandDataRepository;
import com.farmer.be.service.DemandDataService;
import com.farmer.be.service.dto.DemandDataDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.DemandData}.
 */
@RestController
@RequestMapping("/api/demand-data")
public class DemandDataResource {

    private static final Logger LOG = LoggerFactory.getLogger(DemandDataResource.class);

    private static final String ENTITY_NAME = "demandData";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemandDataService demandDataService;

    private final DemandDataRepository demandDataRepository;

    public DemandDataResource(DemandDataService demandDataService, DemandDataRepository demandDataRepository) {
        this.demandDataService = demandDataService;
        this.demandDataRepository = demandDataRepository;
    }

    /**
     * {@code POST  /demand-data} : Create a new demandData.
     *
     * @param demandDataDTO the demandDataDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demandDataDTO, or with status {@code 400 (Bad Request)} if the demandData has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DemandDataDTO> createDemandData(@Valid @RequestBody DemandDataDTO demandDataDTO) throws URISyntaxException {
        LOG.debug("REST request to save DemandData : {}", demandDataDTO);
        if (demandDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new demandData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        demandDataDTO = demandDataService.save(demandDataDTO);
        return ResponseEntity.created(new URI("/api/demand-data/" + demandDataDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, demandDataDTO.getId().toString()))
            .body(demandDataDTO);
    }

    /**
     * {@code PUT  /demand-data/:id} : Updates an existing demandData.
     *
     * @param id the id of the demandDataDTO to save.
     * @param demandDataDTO the demandDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandDataDTO,
     * or with status {@code 400 (Bad Request)} if the demandDataDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demandDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DemandDataDTO> updateDemandData(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DemandDataDTO demandDataDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update DemandData : {}, {}", id, demandDataDTO);
        if (demandDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        demandDataDTO = demandDataService.update(demandDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandDataDTO.getId().toString()))
            .body(demandDataDTO);
    }

    /**
     * {@code PATCH  /demand-data/:id} : Partial updates given fields of an existing demandData, field will ignore if it is null
     *
     * @param id the id of the demandDataDTO to save.
     * @param demandDataDTO the demandDataDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandDataDTO,
     * or with status {@code 400 (Bad Request)} if the demandDataDTO is not valid,
     * or with status {@code 404 (Not Found)} if the demandDataDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the demandDataDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DemandDataDTO> partialUpdateDemandData(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DemandDataDTO demandDataDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update DemandData partially : {}, {}", id, demandDataDTO);
        if (demandDataDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandDataDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandDataRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DemandDataDTO> result = demandDataService.partialUpdate(demandDataDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandDataDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /demand-data} : get all the demandData.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandData in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DemandDataDTO>> getAllDemandData(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of DemandData");
        Page<DemandDataDTO> page = demandDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /demand-data/:id} : get the "id" demandData.
     *
     * @param id the id of the demandDataDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demandDataDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DemandDataDTO> getDemandData(@PathVariable("id") Long id) {
        LOG.debug("REST request to get DemandData : {}", id);
        Optional<DemandDataDTO> demandDataDTO = demandDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demandDataDTO);
    }

    /**
     * {@code DELETE  /demand-data/:id} : delete the "id" demandData.
     *
     * @param id the id of the demandDataDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemandData(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete DemandData : {}", id);
        demandDataService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
