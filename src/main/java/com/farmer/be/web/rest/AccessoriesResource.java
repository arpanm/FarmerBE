package com.farmer.be.web.rest;

import com.farmer.be.repository.AccessoriesRepository;
import com.farmer.be.service.AccessoriesService;
import com.farmer.be.service.dto.AccessoriesDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.Accessories}.
 */
@RestController
@RequestMapping("/api/accessories")
public class AccessoriesResource {

    private static final Logger LOG = LoggerFactory.getLogger(AccessoriesResource.class);

    private static final String ENTITY_NAME = "accessories";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AccessoriesService accessoriesService;

    private final AccessoriesRepository accessoriesRepository;

    public AccessoriesResource(AccessoriesService accessoriesService, AccessoriesRepository accessoriesRepository) {
        this.accessoriesService = accessoriesService;
        this.accessoriesRepository = accessoriesRepository;
    }

    /**
     * {@code POST  /accessories} : Create a new accessories.
     *
     * @param accessoriesDTO the accessoriesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new accessoriesDTO, or with status {@code 400 (Bad Request)} if the accessories has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AccessoriesDTO> createAccessories(@Valid @RequestBody AccessoriesDTO accessoriesDTO) throws URISyntaxException {
        LOG.debug("REST request to save Accessories : {}", accessoriesDTO);
        if (accessoriesDTO.getId() != null) {
            throw new BadRequestAlertException("A new accessories cannot already have an ID", ENTITY_NAME, "idexists");
        }
        accessoriesDTO = accessoriesService.save(accessoriesDTO);
        return ResponseEntity.created(new URI("/api/accessories/" + accessoriesDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, accessoriesDTO.getId().toString()))
            .body(accessoriesDTO);
    }

    /**
     * {@code PUT  /accessories/:id} : Updates an existing accessories.
     *
     * @param id the id of the accessoriesDTO to save.
     * @param accessoriesDTO the accessoriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accessoriesDTO,
     * or with status {@code 400 (Bad Request)} if the accessoriesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the accessoriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AccessoriesDTO> updateAccessories(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AccessoriesDTO accessoriesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Accessories : {}, {}", id, accessoriesDTO);
        if (accessoriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accessoriesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accessoriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        accessoriesDTO = accessoriesService.update(accessoriesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accessoriesDTO.getId().toString()))
            .body(accessoriesDTO);
    }

    /**
     * {@code PATCH  /accessories/:id} : Partial updates given fields of an existing accessories, field will ignore if it is null
     *
     * @param id the id of the accessoriesDTO to save.
     * @param accessoriesDTO the accessoriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated accessoriesDTO,
     * or with status {@code 400 (Bad Request)} if the accessoriesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the accessoriesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the accessoriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AccessoriesDTO> partialUpdateAccessories(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AccessoriesDTO accessoriesDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Accessories partially : {}, {}", id, accessoriesDTO);
        if (accessoriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, accessoriesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!accessoriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AccessoriesDTO> result = accessoriesService.partialUpdate(accessoriesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, accessoriesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /accessories} : get all the accessories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of accessories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AccessoriesDTO>> getAllAccessories(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Accessories");
        Page<AccessoriesDTO> page = accessoriesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /accessories/:id} : get the "id" accessories.
     *
     * @param id the id of the accessoriesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the accessoriesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AccessoriesDTO> getAccessories(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Accessories : {}", id);
        Optional<AccessoriesDTO> accessoriesDTO = accessoriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(accessoriesDTO);
    }

    /**
     * {@code DELETE  /accessories/:id} : delete the "id" accessories.
     *
     * @param id the id of the accessoriesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccessories(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Accessories : {}", id);
        accessoriesService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
