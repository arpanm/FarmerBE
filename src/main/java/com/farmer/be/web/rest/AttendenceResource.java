package com.farmer.be.web.rest;

import com.farmer.be.repository.AttendenceRepository;
import com.farmer.be.service.AttendenceService;
import com.farmer.be.service.dto.AttendenceDTO;
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
 * REST controller for managing {@link com.farmer.be.domain.Attendence}.
 */
@RestController
@RequestMapping("/api/attendences")
public class AttendenceResource {

    private static final Logger LOG = LoggerFactory.getLogger(AttendenceResource.class);

    private static final String ENTITY_NAME = "attendence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttendenceService attendenceService;

    private final AttendenceRepository attendenceRepository;

    public AttendenceResource(AttendenceService attendenceService, AttendenceRepository attendenceRepository) {
        this.attendenceService = attendenceService;
        this.attendenceRepository = attendenceRepository;
    }

    /**
     * {@code POST  /attendences} : Create a new attendence.
     *
     * @param attendenceDTO the attendenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attendenceDTO, or with status {@code 400 (Bad Request)} if the attendence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AttendenceDTO> createAttendence(@Valid @RequestBody AttendenceDTO attendenceDTO) throws URISyntaxException {
        LOG.debug("REST request to save Attendence : {}", attendenceDTO);
        if (attendenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new attendence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        attendenceDTO = attendenceService.save(attendenceDTO);
        return ResponseEntity.created(new URI("/api/attendences/" + attendenceDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, attendenceDTO.getId().toString()))
            .body(attendenceDTO);
    }

    /**
     * {@code PUT  /attendences/:id} : Updates an existing attendence.
     *
     * @param id the id of the attendenceDTO to save.
     * @param attendenceDTO the attendenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attendenceDTO,
     * or with status {@code 400 (Bad Request)} if the attendenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attendenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AttendenceDTO> updateAttendence(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AttendenceDTO attendenceDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Attendence : {}, {}", id, attendenceDTO);
        if (attendenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attendenceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attendenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        attendenceDTO = attendenceService.update(attendenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attendenceDTO.getId().toString()))
            .body(attendenceDTO);
    }

    /**
     * {@code PATCH  /attendences/:id} : Partial updates given fields of an existing attendence, field will ignore if it is null
     *
     * @param id the id of the attendenceDTO to save.
     * @param attendenceDTO the attendenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attendenceDTO,
     * or with status {@code 400 (Bad Request)} if the attendenceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the attendenceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the attendenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AttendenceDTO> partialUpdateAttendence(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AttendenceDTO attendenceDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Attendence partially : {}, {}", id, attendenceDTO);
        if (attendenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, attendenceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!attendenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AttendenceDTO> result = attendenceService.partialUpdate(attendenceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, attendenceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /attendences} : get all the attendences.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attendences in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AttendenceDTO>> getAllAttendences(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Attendences");
        Page<AttendenceDTO> page = attendenceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attendences/:id} : get the "id" attendence.
     *
     * @param id the id of the attendenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attendenceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AttendenceDTO> getAttendence(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Attendence : {}", id);
        Optional<AttendenceDTO> attendenceDTO = attendenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attendenceDTO);
    }

    /**
     * {@code DELETE  /attendences/:id} : delete the "id" attendence.
     *
     * @param id the id of the attendenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendence(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Attendence : {}", id);
        attendenceService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
