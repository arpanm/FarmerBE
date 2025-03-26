package com.farmer.be.web.rest;

import com.farmer.be.repository.BuyerTargetAchivementRepository;
import com.farmer.be.service.BuyerTargetAchivementService;
import com.farmer.be.service.dto.BuyerTargetAchivementDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.BuyerTargetAchivement}.
 */
@RestController
@RequestMapping("/api/buyer-target-achivements")
public class BuyerTargetAchivementResource {

    private static final Logger LOG = LoggerFactory.getLogger(BuyerTargetAchivementResource.class);

    private static final String ENTITY_NAME = "buyerTargetAchivement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BuyerTargetAchivementService buyerTargetAchivementService;

    private final BuyerTargetAchivementRepository buyerTargetAchivementRepository;

    public BuyerTargetAchivementResource(
        BuyerTargetAchivementService buyerTargetAchivementService,
        BuyerTargetAchivementRepository buyerTargetAchivementRepository
    ) {
        this.buyerTargetAchivementService = buyerTargetAchivementService;
        this.buyerTargetAchivementRepository = buyerTargetAchivementRepository;
    }

    /**
     * {@code POST  /buyer-target-achivements} : Create a new buyerTargetAchivement.
     *
     * @param buyerTargetAchivementDTO the buyerTargetAchivementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new buyerTargetAchivementDTO, or with status {@code 400 (Bad Request)} if the buyerTargetAchivement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BuyerTargetAchivementDTO> createBuyerTargetAchivement(
        @Valid @RequestBody BuyerTargetAchivementDTO buyerTargetAchivementDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save BuyerTargetAchivement : {}", buyerTargetAchivementDTO);
        if (buyerTargetAchivementDTO.getId() != null) {
            throw new BadRequestAlertException("A new buyerTargetAchivement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        buyerTargetAchivementDTO = buyerTargetAchivementService.save(buyerTargetAchivementDTO);
        return ResponseEntity.created(new URI("/api/buyer-target-achivements/" + buyerTargetAchivementDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, buyerTargetAchivementDTO.getId().toString()))
            .body(buyerTargetAchivementDTO);
    }

    /**
     * {@code PUT  /buyer-target-achivements/:id} : Updates an existing buyerTargetAchivement.
     *
     * @param id the id of the buyerTargetAchivementDTO to save.
     * @param buyerTargetAchivementDTO the buyerTargetAchivementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buyerTargetAchivementDTO,
     * or with status {@code 400 (Bad Request)} if the buyerTargetAchivementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the buyerTargetAchivementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BuyerTargetAchivementDTO> updateBuyerTargetAchivement(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BuyerTargetAchivementDTO buyerTargetAchivementDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update BuyerTargetAchivement : {}, {}", id, buyerTargetAchivementDTO);
        if (buyerTargetAchivementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buyerTargetAchivementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buyerTargetAchivementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        buyerTargetAchivementDTO = buyerTargetAchivementService.update(buyerTargetAchivementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, buyerTargetAchivementDTO.getId().toString()))
            .body(buyerTargetAchivementDTO);
    }

    /**
     * {@code PATCH  /buyer-target-achivements/:id} : Partial updates given fields of an existing buyerTargetAchivement, field will ignore if it is null
     *
     * @param id the id of the buyerTargetAchivementDTO to save.
     * @param buyerTargetAchivementDTO the buyerTargetAchivementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated buyerTargetAchivementDTO,
     * or with status {@code 400 (Bad Request)} if the buyerTargetAchivementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the buyerTargetAchivementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the buyerTargetAchivementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BuyerTargetAchivementDTO> partialUpdateBuyerTargetAchivement(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BuyerTargetAchivementDTO buyerTargetAchivementDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update BuyerTargetAchivement partially : {}, {}", id, buyerTargetAchivementDTO);
        if (buyerTargetAchivementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, buyerTargetAchivementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!buyerTargetAchivementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BuyerTargetAchivementDTO> result = buyerTargetAchivementService.partialUpdate(buyerTargetAchivementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, buyerTargetAchivementDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /buyer-target-achivements} : get all the buyerTargetAchivements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of buyerTargetAchivements in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BuyerTargetAchivementDTO>> getAllBuyerTargetAchivements(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of BuyerTargetAchivements");
        Page<BuyerTargetAchivementDTO> page = buyerTargetAchivementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /buyer-target-achivements/:id} : get the "id" buyerTargetAchivement.
     *
     * @param id the id of the buyerTargetAchivementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the buyerTargetAchivementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BuyerTargetAchivementDTO> getBuyerTargetAchivement(@PathVariable("id") Long id) {
        LOG.debug("REST request to get BuyerTargetAchivement : {}", id);
        Optional<BuyerTargetAchivementDTO> buyerTargetAchivementDTO = buyerTargetAchivementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(buyerTargetAchivementDTO);
    }

    /**
     * {@code DELETE  /buyer-target-achivements/:id} : delete the "id" buyerTargetAchivement.
     *
     * @param id the id of the buyerTargetAchivementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuyerTargetAchivement(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete BuyerTargetAchivement : {}", id);
        buyerTargetAchivementService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
