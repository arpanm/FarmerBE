package com.farmer.be.web.rest;

import com.farmer.be.repository.PickupPaymentRepository;
import com.farmer.be.service.PickupPaymentService;
import com.farmer.be.service.dto.PickupPaymentDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.PickupPayment}.
 */
@RestController
@RequestMapping("/api/pickup-payments")
public class PickupPaymentResource {

    private static final Logger LOG = LoggerFactory.getLogger(PickupPaymentResource.class);

    private static final String ENTITY_NAME = "pickupPayment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PickupPaymentService pickupPaymentService;

    private final PickupPaymentRepository pickupPaymentRepository;

    public PickupPaymentResource(PickupPaymentService pickupPaymentService, PickupPaymentRepository pickupPaymentRepository) {
        this.pickupPaymentService = pickupPaymentService;
        this.pickupPaymentRepository = pickupPaymentRepository;
    }

    /**
     * {@code POST  /pickup-payments} : Create a new pickupPayment.
     *
     * @param pickupPaymentDTO the pickupPaymentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pickupPaymentDTO, or with status {@code 400 (Bad Request)} if the pickupPayment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PickupPaymentDTO> createPickupPayment(@Valid @RequestBody PickupPaymentDTO pickupPaymentDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save PickupPayment : {}", pickupPaymentDTO);
        if (pickupPaymentDTO.getId() != null) {
            throw new BadRequestAlertException("A new pickupPayment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        pickupPaymentDTO = pickupPaymentService.save(pickupPaymentDTO);
        return ResponseEntity.created(new URI("/api/pickup-payments/" + pickupPaymentDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, pickupPaymentDTO.getId().toString()))
            .body(pickupPaymentDTO);
    }

    /**
     * {@code PUT  /pickup-payments/:id} : Updates an existing pickupPayment.
     *
     * @param id the id of the pickupPaymentDTO to save.
     * @param pickupPaymentDTO the pickupPaymentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pickupPaymentDTO,
     * or with status {@code 400 (Bad Request)} if the pickupPaymentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pickupPaymentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PickupPaymentDTO> updatePickupPayment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PickupPaymentDTO pickupPaymentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update PickupPayment : {}, {}", id, pickupPaymentDTO);
        if (pickupPaymentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pickupPaymentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pickupPaymentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        pickupPaymentDTO = pickupPaymentService.update(pickupPaymentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pickupPaymentDTO.getId().toString()))
            .body(pickupPaymentDTO);
    }

    /**
     * {@code PATCH  /pickup-payments/:id} : Partial updates given fields of an existing pickupPayment, field will ignore if it is null
     *
     * @param id the id of the pickupPaymentDTO to save.
     * @param pickupPaymentDTO the pickupPaymentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pickupPaymentDTO,
     * or with status {@code 400 (Bad Request)} if the pickupPaymentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pickupPaymentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pickupPaymentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PickupPaymentDTO> partialUpdatePickupPayment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PickupPaymentDTO pickupPaymentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PickupPayment partially : {}, {}", id, pickupPaymentDTO);
        if (pickupPaymentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pickupPaymentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pickupPaymentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PickupPaymentDTO> result = pickupPaymentService.partialUpdate(pickupPaymentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pickupPaymentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /pickup-payments} : get all the pickupPayments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pickupPayments in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PickupPaymentDTO>> getAllPickupPayments(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of PickupPayments");
        Page<PickupPaymentDTO> page = pickupPaymentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pickup-payments/:id} : get the "id" pickupPayment.
     *
     * @param id the id of the pickupPaymentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pickupPaymentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PickupPaymentDTO> getPickupPayment(@PathVariable("id") Long id) {
        LOG.debug("REST request to get PickupPayment : {}", id);
        Optional<PickupPaymentDTO> pickupPaymentDTO = pickupPaymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pickupPaymentDTO);
    }

    /**
     * {@code DELETE  /pickup-payments/:id} : delete the "id" pickupPayment.
     *
     * @param id the id of the pickupPaymentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePickupPayment(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete PickupPayment : {}", id);
        pickupPaymentService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
