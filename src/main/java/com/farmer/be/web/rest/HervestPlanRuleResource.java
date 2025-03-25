package com.farmer.be.web.rest;

import com.farmer.be.repository.HervestPlanRuleRepository;
import com.farmer.be.service.HervestPlanRuleService;
import com.farmer.be.service.dto.HervestPlanRuleDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.HervestPlanRule}.
 */
@RestController
@RequestMapping("/api/hervest-plan-rules")
public class HervestPlanRuleResource {

    private static final Logger LOG = LoggerFactory.getLogger(HervestPlanRuleResource.class);

    private static final String ENTITY_NAME = "hervestPlanRule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HervestPlanRuleService hervestPlanRuleService;

    private final HervestPlanRuleRepository hervestPlanRuleRepository;

    public HervestPlanRuleResource(HervestPlanRuleService hervestPlanRuleService, HervestPlanRuleRepository hervestPlanRuleRepository) {
        this.hervestPlanRuleService = hervestPlanRuleService;
        this.hervestPlanRuleRepository = hervestPlanRuleRepository;
    }

    /**
     * {@code POST  /hervest-plan-rules} : Create a new hervestPlanRule.
     *
     * @param hervestPlanRuleDTO the hervestPlanRuleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hervestPlanRuleDTO, or with status {@code 400 (Bad Request)} if the hervestPlanRule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<HervestPlanRuleDTO> createHervestPlanRule(@Valid @RequestBody HervestPlanRuleDTO hervestPlanRuleDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save HervestPlanRule : {}", hervestPlanRuleDTO);
        if (hervestPlanRuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new hervestPlanRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        hervestPlanRuleDTO = hervestPlanRuleService.save(hervestPlanRuleDTO);
        return ResponseEntity.created(new URI("/api/hervest-plan-rules/" + hervestPlanRuleDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, hervestPlanRuleDTO.getId().toString()))
            .body(hervestPlanRuleDTO);
    }

    /**
     * {@code PUT  /hervest-plan-rules/:id} : Updates an existing hervestPlanRule.
     *
     * @param id the id of the hervestPlanRuleDTO to save.
     * @param hervestPlanRuleDTO the hervestPlanRuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hervestPlanRuleDTO,
     * or with status {@code 400 (Bad Request)} if the hervestPlanRuleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hervestPlanRuleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HervestPlanRuleDTO> updateHervestPlanRule(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HervestPlanRuleDTO hervestPlanRuleDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update HervestPlanRule : {}, {}", id, hervestPlanRuleDTO);
        if (hervestPlanRuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hervestPlanRuleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hervestPlanRuleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        hervestPlanRuleDTO = hervestPlanRuleService.update(hervestPlanRuleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hervestPlanRuleDTO.getId().toString()))
            .body(hervestPlanRuleDTO);
    }

    /**
     * {@code PATCH  /hervest-plan-rules/:id} : Partial updates given fields of an existing hervestPlanRule, field will ignore if it is null
     *
     * @param id the id of the hervestPlanRuleDTO to save.
     * @param hervestPlanRuleDTO the hervestPlanRuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hervestPlanRuleDTO,
     * or with status {@code 400 (Bad Request)} if the hervestPlanRuleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the hervestPlanRuleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the hervestPlanRuleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<HervestPlanRuleDTO> partialUpdateHervestPlanRule(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HervestPlanRuleDTO hervestPlanRuleDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update HervestPlanRule partially : {}, {}", id, hervestPlanRuleDTO);
        if (hervestPlanRuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hervestPlanRuleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hervestPlanRuleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HervestPlanRuleDTO> result = hervestPlanRuleService.partialUpdate(hervestPlanRuleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hervestPlanRuleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /hervest-plan-rules} : get all the hervestPlanRules.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hervestPlanRules in body.
     */
    @GetMapping("")
    public ResponseEntity<List<HervestPlanRuleDTO>> getAllHervestPlanRules(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of HervestPlanRules");
        Page<HervestPlanRuleDTO> page = hervestPlanRuleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /hervest-plan-rules/:id} : get the "id" hervestPlanRule.
     *
     * @param id the id of the hervestPlanRuleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hervestPlanRuleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HervestPlanRuleDTO> getHervestPlanRule(@PathVariable("id") Long id) {
        LOG.debug("REST request to get HervestPlanRule : {}", id);
        Optional<HervestPlanRuleDTO> hervestPlanRuleDTO = hervestPlanRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(hervestPlanRuleDTO);
    }

    /**
     * {@code DELETE  /hervest-plan-rules/:id} : delete the "id" hervestPlanRule.
     *
     * @param id the id of the hervestPlanRuleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHervestPlanRule(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete HervestPlanRule : {}", id);
        hervestPlanRuleService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
