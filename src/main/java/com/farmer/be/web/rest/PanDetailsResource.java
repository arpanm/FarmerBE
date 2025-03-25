package com.farmer.be.web.rest;

import com.farmer.be.repository.PanDetailsRepository;
import com.farmer.be.service.PanDetailsService;
import com.farmer.be.service.dto.PanDetailsDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.PanDetails}.
 */
@RestController
@RequestMapping("/api/pan-details")
public class PanDetailsResource {

    private static final Logger LOG = LoggerFactory.getLogger(PanDetailsResource.class);

    private static final String ENTITY_NAME = "panDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PanDetailsService panDetailsService;

    private final PanDetailsRepository panDetailsRepository;

    public PanDetailsResource(PanDetailsService panDetailsService, PanDetailsRepository panDetailsRepository) {
        this.panDetailsService = panDetailsService;
        this.panDetailsRepository = panDetailsRepository;
    }

    /**
     * {@code POST  /pan-details} : Create a new panDetails.
     *
     * @param panDetailsDTO the panDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new panDetailsDTO, or with status {@code 400 (Bad Request)} if the panDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PanDetailsDTO> createPanDetails(@Valid @RequestBody PanDetailsDTO panDetailsDTO) throws URISyntaxException {
        LOG.debug("REST request to save PanDetails : {}", panDetailsDTO);
        if (panDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new panDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        panDetailsDTO = panDetailsService.save(panDetailsDTO);
        return ResponseEntity.created(new URI("/api/pan-details/" + panDetailsDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, panDetailsDTO.getId().toString()))
            .body(panDetailsDTO);
    }

    /**
     * {@code PUT  /pan-details/:id} : Updates an existing panDetails.
     *
     * @param id the id of the panDetailsDTO to save.
     * @param panDetailsDTO the panDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated panDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the panDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the panDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PanDetailsDTO> updatePanDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PanDetailsDTO panDetailsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PanDetails : {}, {}", id, panDetailsDTO);
        if (panDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, panDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!panDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        panDetailsDTO = panDetailsService.update(panDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, panDetailsDTO.getId().toString()))
            .body(panDetailsDTO);
    }

    /**
     * {@code PATCH  /pan-details/:id} : Partial updates given fields of an existing panDetails, field will ignore if it is null
     *
     * @param id the id of the panDetailsDTO to save.
     * @param panDetailsDTO the panDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated panDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the panDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the panDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the panDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PanDetailsDTO> partialUpdatePanDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PanDetailsDTO panDetailsDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PanDetails partially : {}, {}", id, panDetailsDTO);
        if (panDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, panDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!panDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PanDetailsDTO> result = panDetailsService.partialUpdate(panDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, panDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /pan-details} : get all the panDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of panDetails in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PanDetailsDTO>> getAllPanDetails(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of PanDetails");
        Page<PanDetailsDTO> page = panDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pan-details/:id} : get the "id" panDetails.
     *
     * @param id the id of the panDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the panDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PanDetailsDTO> getPanDetails(@PathVariable("id") Long id) {
        LOG.debug("REST request to get PanDetails : {}", id);
        Optional<PanDetailsDTO> panDetailsDTO = panDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(panDetailsDTO);
    }

    /**
     * {@code DELETE  /pan-details/:id} : delete the "id" panDetails.
     *
     * @param id the id of the panDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePanDetails(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete PanDetails : {}", id);
        panDetailsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
