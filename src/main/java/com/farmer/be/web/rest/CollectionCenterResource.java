package com.farmer.be.web.rest;

import com.farmer.be.repository.CollectionCenterRepository;
import com.farmer.be.service.CollectionCenterService;
import com.farmer.be.service.dto.CollectionCenterDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.CollectionCenter}.
 */
@RestController
@RequestMapping("/api/collection-centers")
public class CollectionCenterResource {

    private static final Logger LOG = LoggerFactory.getLogger(CollectionCenterResource.class);

    private static final String ENTITY_NAME = "collectionCenter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollectionCenterService collectionCenterService;

    private final CollectionCenterRepository collectionCenterRepository;

    public CollectionCenterResource(
        CollectionCenterService collectionCenterService,
        CollectionCenterRepository collectionCenterRepository
    ) {
        this.collectionCenterService = collectionCenterService;
        this.collectionCenterRepository = collectionCenterRepository;
    }

    /**
     * {@code POST  /collection-centers} : Create a new collectionCenter.
     *
     * @param collectionCenterDTO the collectionCenterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new collectionCenterDTO, or with status {@code 400 (Bad Request)} if the collectionCenter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CollectionCenterDTO> createCollectionCenter(@Valid @RequestBody CollectionCenterDTO collectionCenterDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save CollectionCenter : {}", collectionCenterDTO);
        if (collectionCenterDTO.getId() != null) {
            throw new BadRequestAlertException("A new collectionCenter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        collectionCenterDTO = collectionCenterService.save(collectionCenterDTO);
        return ResponseEntity.created(new URI("/api/collection-centers/" + collectionCenterDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, collectionCenterDTO.getId().toString()))
            .body(collectionCenterDTO);
    }

    /**
     * {@code PUT  /collection-centers/:id} : Updates an existing collectionCenter.
     *
     * @param id the id of the collectionCenterDTO to save.
     * @param collectionCenterDTO the collectionCenterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collectionCenterDTO,
     * or with status {@code 400 (Bad Request)} if the collectionCenterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collectionCenterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CollectionCenterDTO> updateCollectionCenter(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CollectionCenterDTO collectionCenterDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update CollectionCenter : {}, {}", id, collectionCenterDTO);
        if (collectionCenterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collectionCenterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collectionCenterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        collectionCenterDTO = collectionCenterService.update(collectionCenterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collectionCenterDTO.getId().toString()))
            .body(collectionCenterDTO);
    }

    /**
     * {@code PATCH  /collection-centers/:id} : Partial updates given fields of an existing collectionCenter, field will ignore if it is null
     *
     * @param id the id of the collectionCenterDTO to save.
     * @param collectionCenterDTO the collectionCenterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collectionCenterDTO,
     * or with status {@code 400 (Bad Request)} if the collectionCenterDTO is not valid,
     * or with status {@code 404 (Not Found)} if the collectionCenterDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the collectionCenterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CollectionCenterDTO> partialUpdateCollectionCenter(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CollectionCenterDTO collectionCenterDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CollectionCenter partially : {}, {}", id, collectionCenterDTO);
        if (collectionCenterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collectionCenterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collectionCenterRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CollectionCenterDTO> result = collectionCenterService.partialUpdate(collectionCenterDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collectionCenterDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /collection-centers} : get all the collectionCenters.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of collectionCenters in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CollectionCenterDTO>> getAllCollectionCenters(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of CollectionCenters");
        Page<CollectionCenterDTO> page;
        if (eagerload) {
            page = collectionCenterService.findAllWithEagerRelationships(pageable);
        } else {
            page = collectionCenterService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /collection-centers/:id} : get the "id" collectionCenter.
     *
     * @param id the id of the collectionCenterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the collectionCenterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CollectionCenterDTO> getCollectionCenter(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CollectionCenter : {}", id);
        Optional<CollectionCenterDTO> collectionCenterDTO = collectionCenterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(collectionCenterDTO);
    }

    /**
     * {@code DELETE  /collection-centers/:id} : delete the "id" collectionCenter.
     *
     * @param id the id of the collectionCenterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCollectionCenter(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CollectionCenter : {}", id);
        collectionCenterService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
