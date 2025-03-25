package com.farmer.be.web.rest;

import com.farmer.be.repository.PickUpConfirmationRepository;
import com.farmer.be.service.PickUpConfirmationService;
import com.farmer.be.service.dto.PickUpConfirmationDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.PickUpConfirmation}.
 */
@RestController
@RequestMapping("/api/pick-up-confirmations")
public class PickUpConfirmationResource {

    private static final Logger LOG = LoggerFactory.getLogger(PickUpConfirmationResource.class);

    private static final String ENTITY_NAME = "pickUpConfirmation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PickUpConfirmationService pickUpConfirmationService;

    private final PickUpConfirmationRepository pickUpConfirmationRepository;

    public PickUpConfirmationResource(
        PickUpConfirmationService pickUpConfirmationService,
        PickUpConfirmationRepository pickUpConfirmationRepository
    ) {
        this.pickUpConfirmationService = pickUpConfirmationService;
        this.pickUpConfirmationRepository = pickUpConfirmationRepository;
    }

    /**
     * {@code POST  /pick-up-confirmations} : Create a new pickUpConfirmation.
     *
     * @param pickUpConfirmationDTO the pickUpConfirmationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pickUpConfirmationDTO, or with status {@code 400 (Bad Request)} if the pickUpConfirmation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PickUpConfirmationDTO> createPickUpConfirmation(@Valid @RequestBody PickUpConfirmationDTO pickUpConfirmationDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save PickUpConfirmation : {}", pickUpConfirmationDTO);
        if (pickUpConfirmationDTO.getId() != null) {
            throw new BadRequestAlertException("A new pickUpConfirmation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pickUpConfirmationDTO = pickUpConfirmationService.save(pickUpConfirmationDTO);
        return ResponseEntity.created(new URI("/api/pick-up-confirmations/" + pickUpConfirmationDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, pickUpConfirmationDTO.getId().toString()))
            .body(pickUpConfirmationDTO);
    }

    /**
     * {@code PUT  /pick-up-confirmations/:id} : Updates an existing pickUpConfirmation.
     *
     * @param id the id of the pickUpConfirmationDTO to save.
     * @param pickUpConfirmationDTO the pickUpConfirmationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pickUpConfirmationDTO,
     * or with status {@code 400 (Bad Request)} if the pickUpConfirmationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pickUpConfirmationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PickUpConfirmationDTO> updatePickUpConfirmation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PickUpConfirmationDTO pickUpConfirmationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PickUpConfirmation : {}, {}", id, pickUpConfirmationDTO);
        if (pickUpConfirmationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pickUpConfirmationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pickUpConfirmationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        pickUpConfirmationDTO = pickUpConfirmationService.update(pickUpConfirmationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pickUpConfirmationDTO.getId().toString()))
            .body(pickUpConfirmationDTO);
    }

    /**
     * {@code PATCH  /pick-up-confirmations/:id} : Partial updates given fields of an existing pickUpConfirmation, field will ignore if it is null
     *
     * @param id the id of the pickUpConfirmationDTO to save.
     * @param pickUpConfirmationDTO the pickUpConfirmationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pickUpConfirmationDTO,
     * or with status {@code 400 (Bad Request)} if the pickUpConfirmationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pickUpConfirmationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pickUpConfirmationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PickUpConfirmationDTO> partialUpdatePickUpConfirmation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PickUpConfirmationDTO pickUpConfirmationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PickUpConfirmation partially : {}, {}", id, pickUpConfirmationDTO);
        if (pickUpConfirmationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pickUpConfirmationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pickUpConfirmationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PickUpConfirmationDTO> result = pickUpConfirmationService.partialUpdate(pickUpConfirmationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pickUpConfirmationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /pick-up-confirmations} : get all the pickUpConfirmations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pickUpConfirmations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PickUpConfirmationDTO>> getAllPickUpConfirmations(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of PickUpConfirmations");
        Page<PickUpConfirmationDTO> page = pickUpConfirmationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pick-up-confirmations/:id} : get the "id" pickUpConfirmation.
     *
     * @param id the id of the pickUpConfirmationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pickUpConfirmationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PickUpConfirmationDTO> getPickUpConfirmation(@PathVariable("id") Long id) {
        LOG.debug("REST request to get PickUpConfirmation : {}", id);
        Optional<PickUpConfirmationDTO> pickUpConfirmationDTO = pickUpConfirmationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pickUpConfirmationDTO);
    }

    /**
     * {@code DELETE  /pick-up-confirmations/:id} : delete the "id" pickUpConfirmation.
     *
     * @param id the id of the pickUpConfirmationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePickUpConfirmation(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete PickUpConfirmation : {}", id);
        pickUpConfirmationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
