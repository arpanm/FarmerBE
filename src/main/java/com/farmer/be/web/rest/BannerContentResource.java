package com.farmer.be.web.rest;

import com.farmer.be.repository.BannerContentRepository;
import com.farmer.be.service.BannerContentService;
import com.farmer.be.service.dto.BannerContentDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.BannerContent}.
 */
@RestController
@RequestMapping("/api/banner-contents")
public class BannerContentResource {

    private static final Logger LOG = LoggerFactory.getLogger(BannerContentResource.class);

    private static final String ENTITY_NAME = "bannerContent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BannerContentService bannerContentService;

    private final BannerContentRepository bannerContentRepository;

    public BannerContentResource(BannerContentService bannerContentService, BannerContentRepository bannerContentRepository) {
        this.bannerContentService = bannerContentService;
        this.bannerContentRepository = bannerContentRepository;
    }

    /**
     * {@code POST  /banner-contents} : Create a new bannerContent.
     *
     * @param bannerContentDTO the bannerContentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bannerContentDTO, or with status {@code 400 (Bad Request)} if the bannerContent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BannerContentDTO> createBannerContent(@Valid @RequestBody BannerContentDTO bannerContentDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save BannerContent : {}", bannerContentDTO);
        if (bannerContentDTO.getId() != null) {
            throw new BadRequestAlertException("A new bannerContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bannerContentDTO = bannerContentService.save(bannerContentDTO);
        return ResponseEntity.created(new URI("/api/banner-contents/" + bannerContentDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, bannerContentDTO.getId().toString()))
            .body(bannerContentDTO);
    }

    /**
     * {@code PUT  /banner-contents/:id} : Updates an existing bannerContent.
     *
     * @param id the id of the bannerContentDTO to save.
     * @param bannerContentDTO the bannerContentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bannerContentDTO,
     * or with status {@code 400 (Bad Request)} if the bannerContentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bannerContentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BannerContentDTO> updateBannerContent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BannerContentDTO bannerContentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update BannerContent : {}, {}", id, bannerContentDTO);
        if (bannerContentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bannerContentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bannerContentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bannerContentDTO = bannerContentService.update(bannerContentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bannerContentDTO.getId().toString()))
            .body(bannerContentDTO);
    }

    /**
     * {@code PATCH  /banner-contents/:id} : Partial updates given fields of an existing bannerContent, field will ignore if it is null
     *
     * @param id the id of the bannerContentDTO to save.
     * @param bannerContentDTO the bannerContentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bannerContentDTO,
     * or with status {@code 400 (Bad Request)} if the bannerContentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bannerContentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bannerContentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BannerContentDTO> partialUpdateBannerContent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BannerContentDTO bannerContentDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update BannerContent partially : {}, {}", id, bannerContentDTO);
        if (bannerContentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bannerContentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bannerContentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BannerContentDTO> result = bannerContentService.partialUpdate(bannerContentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bannerContentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /banner-contents} : get all the bannerContents.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bannerContents in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BannerContentDTO>> getAllBannerContents(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of BannerContents");
        Page<BannerContentDTO> page = bannerContentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /banner-contents/:id} : get the "id" bannerContent.
     *
     * @param id the id of the bannerContentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bannerContentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BannerContentDTO> getBannerContent(@PathVariable("id") Long id) {
        LOG.debug("REST request to get BannerContent : {}", id);
        Optional<BannerContentDTO> bannerContentDTO = bannerContentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bannerContentDTO);
    }

    /**
     * {@code DELETE  /banner-contents/:id} : delete the "id" bannerContent.
     *
     * @param id the id of the bannerContentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBannerContent(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete BannerContent : {}", id);
        bannerContentService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
