package com.farmer.be.web.rest;

import com.farmer.be.repository.CropRepository;
import com.farmer.be.service.CropService;
import com.farmer.be.service.dto.CropDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.Crop}.
 */
@RestController
@RequestMapping("/api/crops")
public class CropResource {

    private static final Logger LOG = LoggerFactory.getLogger(CropResource.class);

    private static final String ENTITY_NAME = "crop";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CropService cropService;

    private final CropRepository cropRepository;

    public CropResource(CropService cropService, CropRepository cropRepository) {
        this.cropService = cropService;
        this.cropRepository = cropRepository;
    }

    /**
     * {@code POST  /crops} : Create a new crop.
     *
     * @param cropDTO the cropDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cropDTO, or with status {@code 400 (Bad Request)} if the crop has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CropDTO> createCrop(@Valid @RequestBody CropDTO cropDTO) throws URISyntaxException {
        LOG.debug("REST request to save Crop : {}", cropDTO);
        if (cropDTO.getId() != null) {
            throw new BadRequestAlertException("A new crop cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cropDTO = cropService.save(cropDTO);
        return ResponseEntity.created(new URI("/api/crops/" + cropDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cropDTO.getId().toString()))
            .body(cropDTO);
    }

    /**
     * {@code PUT  /crops/:id} : Updates an existing crop.
     *
     * @param id the id of the cropDTO to save.
     * @param cropDTO the cropDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cropDTO,
     * or with status {@code 400 (Bad Request)} if the cropDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cropDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CropDTO> updateCrop(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CropDTO cropDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Crop : {}, {}", id, cropDTO);
        if (cropDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cropDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cropRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cropDTO = cropService.update(cropDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cropDTO.getId().toString()))
            .body(cropDTO);
    }

    /**
     * {@code PATCH  /crops/:id} : Partial updates given fields of an existing crop, field will ignore if it is null
     *
     * @param id the id of the cropDTO to save.
     * @param cropDTO the cropDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cropDTO,
     * or with status {@code 400 (Bad Request)} if the cropDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cropDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cropDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CropDTO> partialUpdateCrop(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CropDTO cropDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Crop partially : {}, {}", id, cropDTO);
        if (cropDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cropDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cropRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CropDTO> result = cropService.partialUpdate(cropDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cropDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /crops} : get all the crops.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of crops in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CropDTO>> getAllCrops(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Crops");
        Page<CropDTO> page = cropService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /crops/:id} : get the "id" crop.
     *
     * @param id the id of the cropDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cropDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CropDTO> getCrop(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Crop : {}", id);
        Optional<CropDTO> cropDTO = cropService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cropDTO);
    }

    /**
     * {@code DELETE  /crops/:id} : delete the "id" crop.
     *
     * @param id the id of the cropDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrop(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Crop : {}", id);
        cropService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
