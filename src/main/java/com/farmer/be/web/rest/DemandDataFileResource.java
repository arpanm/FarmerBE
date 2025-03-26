package com.farmer.be.web.rest;

import com.farmer.be.repository.DemandDataFileRepository;
import com.farmer.be.service.DemandDataFileService;
import com.farmer.be.service.dto.DemandDataFileDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.DemandDataFile}.
 */
@RestController
@RequestMapping("/api/demand-data-files")
public class DemandDataFileResource {

    private static final Logger LOG = LoggerFactory.getLogger(DemandDataFileResource.class);

    private static final String ENTITY_NAME = "demandDataFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemandDataFileService demandDataFileService;

    private final DemandDataFileRepository demandDataFileRepository;

    public DemandDataFileResource(DemandDataFileService demandDataFileService, DemandDataFileRepository demandDataFileRepository) {
        this.demandDataFileService = demandDataFileService;
        this.demandDataFileRepository = demandDataFileRepository;
    }

    /**
     * {@code POST  /demand-data-files} : Create a new demandDataFile.
     *
     * @param demandDataFileDTO the demandDataFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demandDataFileDTO, or with status {@code 400 (Bad Request)} if the demandDataFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DemandDataFileDTO> createDemandDataFile(@Valid @RequestBody DemandDataFileDTO demandDataFileDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save DemandDataFile : {}", demandDataFileDTO);
        if (demandDataFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new demandDataFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        demandDataFileDTO = demandDataFileService.save(demandDataFileDTO);
        return ResponseEntity.created(new URI("/api/demand-data-files/" + demandDataFileDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, demandDataFileDTO.getId().toString()))
            .body(demandDataFileDTO);
    }

    /**
     * {@code PUT  /demand-data-files/:id} : Updates an existing demandDataFile.
     *
     * @param id the id of the demandDataFileDTO to save.
     * @param demandDataFileDTO the demandDataFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandDataFileDTO,
     * or with status {@code 400 (Bad Request)} if the demandDataFileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demandDataFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DemandDataFileDTO> updateDemandDataFile(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DemandDataFileDTO demandDataFileDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update DemandDataFile : {}, {}", id, demandDataFileDTO);
        if (demandDataFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandDataFileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandDataFileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        demandDataFileDTO = demandDataFileService.update(demandDataFileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandDataFileDTO.getId().toString()))
            .body(demandDataFileDTO);
    }

    /**
     * {@code PATCH  /demand-data-files/:id} : Partial updates given fields of an existing demandDataFile, field will ignore if it is null
     *
     * @param id the id of the demandDataFileDTO to save.
     * @param demandDataFileDTO the demandDataFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandDataFileDTO,
     * or with status {@code 400 (Bad Request)} if the demandDataFileDTO is not valid,
     * or with status {@code 404 (Not Found)} if the demandDataFileDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the demandDataFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DemandDataFileDTO> partialUpdateDemandDataFile(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DemandDataFileDTO demandDataFileDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update DemandDataFile partially : {}, {}", id, demandDataFileDTO);
        if (demandDataFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandDataFileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandDataFileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DemandDataFileDTO> result = demandDataFileService.partialUpdate(demandDataFileDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandDataFileDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /demand-data-files} : get all the demandDataFiles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandDataFiles in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DemandDataFileDTO>> getAllDemandDataFiles(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of DemandDataFiles");
        Page<DemandDataFileDTO> page = demandDataFileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /demand-data-files/:id} : get the "id" demandDataFile.
     *
     * @param id the id of the demandDataFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demandDataFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DemandDataFileDTO> getDemandDataFile(@PathVariable("id") Long id) {
        LOG.debug("REST request to get DemandDataFile : {}", id);
        Optional<DemandDataFileDTO> demandDataFileDTO = demandDataFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demandDataFileDTO);
    }

    /**
     * {@code DELETE  /demand-data-files/:id} : delete the "id" demandDataFile.
     *
     * @param id the id of the demandDataFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemandDataFile(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete DemandDataFile : {}", id);
        demandDataFileService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
