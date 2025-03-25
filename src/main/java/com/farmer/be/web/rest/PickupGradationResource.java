package com.farmer.be.web.rest;

import com.farmer.be.repository.PickupGradationRepository;
import com.farmer.be.service.PickupGradationService;
import com.farmer.be.service.dto.PickupGradationDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.farmer.be.domain.PickupGradation}.
 */
@RestController
@RequestMapping("/api/pickup-gradations")
public class PickupGradationResource {

    private static final Logger LOG = LoggerFactory.getLogger(PickupGradationResource.class);

    private static final String ENTITY_NAME = "pickupGradation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PickupGradationService pickupGradationService;

    private final PickupGradationRepository pickupGradationRepository;

    public PickupGradationResource(PickupGradationService pickupGradationService, PickupGradationRepository pickupGradationRepository) {
        this.pickupGradationService = pickupGradationService;
        this.pickupGradationRepository = pickupGradationRepository;
    }

    /**
     * {@code POST  /pickup-gradations} : Create a new pickupGradation.
     *
     * @param pickupGradationDTO the pickupGradationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pickupGradationDTO, or with status {@code 400 (Bad Request)} if the pickupGradation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PickupGradationDTO> createPickupGradation(@Valid @RequestBody PickupGradationDTO pickupGradationDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save PickupGradation : {}", pickupGradationDTO);
        if (pickupGradationDTO.getId() != null) {
            throw new BadRequestAlertException("A new pickupGradation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pickupGradationDTO = pickupGradationService.save(pickupGradationDTO);
        return ResponseEntity.created(new URI("/api/pickup-gradations/" + pickupGradationDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, pickupGradationDTO.getId().toString()))
            .body(pickupGradationDTO);
    }

    /**
     * {@code PUT  /pickup-gradations/:id} : Updates an existing pickupGradation.
     *
     * @param id the id of the pickupGradationDTO to save.
     * @param pickupGradationDTO the pickupGradationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pickupGradationDTO,
     * or with status {@code 400 (Bad Request)} if the pickupGradationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pickupGradationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PickupGradationDTO> updatePickupGradation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PickupGradationDTO pickupGradationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PickupGradation : {}, {}", id, pickupGradationDTO);
        if (pickupGradationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pickupGradationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pickupGradationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        pickupGradationDTO = pickupGradationService.update(pickupGradationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pickupGradationDTO.getId().toString()))
            .body(pickupGradationDTO);
    }

    /**
     * {@code PATCH  /pickup-gradations/:id} : Partial updates given fields of an existing pickupGradation, field will ignore if it is null
     *
     * @param id the id of the pickupGradationDTO to save.
     * @param pickupGradationDTO the pickupGradationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pickupGradationDTO,
     * or with status {@code 400 (Bad Request)} if the pickupGradationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pickupGradationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pickupGradationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PickupGradationDTO> partialUpdatePickupGradation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PickupGradationDTO pickupGradationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PickupGradation partially : {}, {}", id, pickupGradationDTO);
        if (pickupGradationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pickupGradationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pickupGradationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PickupGradationDTO> result = pickupGradationService.partialUpdate(pickupGradationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pickupGradationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /pickup-gradations} : get all the pickupGradations.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pickupGradations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PickupGradationDTO>> getAllPickupGradations(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("pickupitem-is-null".equals(filter)) {
            LOG.debug("REST request to get all PickupGradations where pickupItem is null");
            return new ResponseEntity<>(pickupGradationService.findAllWherePickupItemIsNull(), HttpStatus.OK);
        }
        LOG.debug("REST request to get a page of PickupGradations");
        Page<PickupGradationDTO> page = pickupGradationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pickup-gradations/:id} : get the "id" pickupGradation.
     *
     * @param id the id of the pickupGradationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pickupGradationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PickupGradationDTO> getPickupGradation(@PathVariable("id") Long id) {
        LOG.debug("REST request to get PickupGradation : {}", id);
        Optional<PickupGradationDTO> pickupGradationDTO = pickupGradationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pickupGradationDTO);
    }

    /**
     * {@code DELETE  /pickup-gradations/:id} : delete the "id" pickupGradation.
     *
     * @param id the id of the pickupGradationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePickupGradation(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete PickupGradation : {}", id);
        pickupGradationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
