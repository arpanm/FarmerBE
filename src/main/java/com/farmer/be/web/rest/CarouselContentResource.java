package com.farmer.be.web.rest;

import com.farmer.be.repository.CarouselContentRepository;
import com.farmer.be.service.CarouselContentService;
import com.farmer.be.service.dto.CarouselContentDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.CarouselContent}.
 */
@RestController
@RequestMapping("/api/carousel-contents")
public class CarouselContentResource {

    private static final Logger LOG = LoggerFactory.getLogger(CarouselContentResource.class);

    private static final String ENTITY_NAME = "carouselContent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarouselContentService carouselContentService;

    private final CarouselContentRepository carouselContentRepository;

    public CarouselContentResource(CarouselContentService carouselContentService, CarouselContentRepository carouselContentRepository) {
        this.carouselContentService = carouselContentService;
        this.carouselContentRepository = carouselContentRepository;
    }

    /**
     * {@code POST  /carousel-contents} : Create a new carouselContent.
     *
     * @param carouselContentDTO the carouselContentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carouselContentDTO, or with status {@code 400 (Bad Request)} if the carouselContent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CarouselContentDTO> createCarouselContent(@Valid @RequestBody CarouselContentDTO carouselContentDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save CarouselContent : {}", carouselContentDTO);
        if (carouselContentDTO.getId() != null) {
            throw new BadRequestAlertException("A new carouselContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        carouselContentDTO = carouselContentService.save(carouselContentDTO);
        return ResponseEntity.created(new URI("/api/carousel-contents/" + carouselContentDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, carouselContentDTO.getId().toString()))
            .body(carouselContentDTO);
    }

    /**
     * {@code PUT  /carousel-contents/:id} : Updates an existing carouselContent.
     *
     * @param id the id of the carouselContentDTO to save.
     * @param carouselContentDTO the carouselContentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carouselContentDTO,
     * or with status {@code 400 (Bad Request)} if the carouselContentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carouselContentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CarouselContentDTO> updateCarouselContent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CarouselContentDTO carouselContentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update CarouselContent : {}, {}", id, carouselContentDTO);
        if (carouselContentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carouselContentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carouselContentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        carouselContentDTO = carouselContentService.update(carouselContentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carouselContentDTO.getId().toString()))
            .body(carouselContentDTO);
    }

    /**
     * {@code PATCH  /carousel-contents/:id} : Partial updates given fields of an existing carouselContent, field will ignore if it is null
     *
     * @param id the id of the carouselContentDTO to save.
     * @param carouselContentDTO the carouselContentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carouselContentDTO,
     * or with status {@code 400 (Bad Request)} if the carouselContentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the carouselContentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the carouselContentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CarouselContentDTO> partialUpdateCarouselContent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CarouselContentDTO carouselContentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CarouselContent partially : {}, {}", id, carouselContentDTO);
        if (carouselContentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carouselContentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carouselContentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CarouselContentDTO> result = carouselContentService.partialUpdate(carouselContentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carouselContentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /carousel-contents} : get all the carouselContents.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carouselContents in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CarouselContentDTO>> getAllCarouselContents(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of CarouselContents");
        Page<CarouselContentDTO> page = carouselContentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /carousel-contents/:id} : get the "id" carouselContent.
     *
     * @param id the id of the carouselContentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carouselContentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CarouselContentDTO> getCarouselContent(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CarouselContent : {}", id);
        Optional<CarouselContentDTO> carouselContentDTO = carouselContentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carouselContentDTO);
    }

    /**
     * {@code DELETE  /carousel-contents/:id} : delete the "id" carouselContent.
     *
     * @param id the id of the carouselContentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarouselContent(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CarouselContent : {}", id);
        carouselContentService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
