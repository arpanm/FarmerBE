package com.farmer.be.web.rest;

import com.farmer.be.repository.HervestPlanRepository;
import com.farmer.be.service.HervestPlanService;
import com.farmer.be.service.dto.HervestPlanDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.HervestPlan}.
 */
@RestController
@RequestMapping("/api/hervest-plans")
public class HervestPlanResource {

    private static final Logger LOG = LoggerFactory.getLogger(HervestPlanResource.class);

    private static final String ENTITY_NAME = "hervestPlan";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HervestPlanService hervestPlanService;

    private final HervestPlanRepository hervestPlanRepository;

    public HervestPlanResource(HervestPlanService hervestPlanService, HervestPlanRepository hervestPlanRepository) {
        this.hervestPlanService = hervestPlanService;
        this.hervestPlanRepository = hervestPlanRepository;
    }

    /**
     * {@code POST  /hervest-plans} : Create a new hervestPlan.
     *
     * @param hervestPlanDTO the hervestPlanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hervestPlanDTO, or with status {@code 400 (Bad Request)} if the hervestPlan has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<HervestPlanDTO> createHervestPlan(@Valid @RequestBody HervestPlanDTO hervestPlanDTO) throws URISyntaxException {
        LOG.debug("REST request to save HervestPlan : {}", hervestPlanDTO);
        if (hervestPlanDTO.getId() != null) {
            throw new BadRequestAlertException("A new hervestPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        hervestPlanDTO = hervestPlanService.save(hervestPlanDTO);
        return ResponseEntity.created(new URI("/api/hervest-plans/" + hervestPlanDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, hervestPlanDTO.getId().toString()))
            .body(hervestPlanDTO);
    }

    /**
     * {@code PUT  /hervest-plans/:id} : Updates an existing hervestPlan.
     *
     * @param id the id of the hervestPlanDTO to save.
     * @param hervestPlanDTO the hervestPlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hervestPlanDTO,
     * or with status {@code 400 (Bad Request)} if the hervestPlanDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hervestPlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HervestPlanDTO> updateHervestPlan(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HervestPlanDTO hervestPlanDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update HervestPlan : {}, {}", id, hervestPlanDTO);
        if (hervestPlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hervestPlanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hervestPlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        hervestPlanDTO = hervestPlanService.update(hervestPlanDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hervestPlanDTO.getId().toString()))
            .body(hervestPlanDTO);
    }

    /**
     * {@code PATCH  /hervest-plans/:id} : Partial updates given fields of an existing hervestPlan, field will ignore if it is null
     *
     * @param id the id of the hervestPlanDTO to save.
     * @param hervestPlanDTO the hervestPlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hervestPlanDTO,
     * or with status {@code 400 (Bad Request)} if the hervestPlanDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hervestPlanDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hervestPlanDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HervestPlanDTO> partialUpdateHervestPlan(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HervestPlanDTO hervestPlanDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update HervestPlan partially : {}, {}", id, hervestPlanDTO);
        if (hervestPlanDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hervestPlanDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hervestPlanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HervestPlanDTO> result = hervestPlanService.partialUpdate(hervestPlanDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hervestPlanDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hervest-plans} : get all the hervestPlans.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hervestPlans in body.
     */
    @GetMapping("")
    public ResponseEntity<List<HervestPlanDTO>> getAllHervestPlans(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of HervestPlans");
        Page<HervestPlanDTO> page = hervestPlanService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hervest-plans/:id} : get the "id" hervestPlan.
     *
     * @param id the id of the hervestPlanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hervestPlanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HervestPlanDTO> getHervestPlan(@PathVariable("id") Long id) {
        LOG.debug("REST request to get HervestPlan : {}", id);
        Optional<HervestPlanDTO> hervestPlanDTO = hervestPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hervestPlanDTO);
    }

    /**
     * {@code DELETE  /hervest-plans/:id} : delete the "id" hervestPlan.
     *
     * @param id the id of the hervestPlanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHervestPlan(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete HervestPlan : {}", id);
        hervestPlanService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
