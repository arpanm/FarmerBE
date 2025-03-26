package com.farmer.be.service;

import com.farmer.be.service.dto.AttendenceDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.Attendence}.
 */
public interface AttendenceService {
    /**
     * Save a attendence.
     *
     * @param attendenceDTO the entity to save.
     * @return the persisted entity.
     */
    AttendenceDTO save(AttendenceDTO attendenceDTO);

    /**
     * Updates a attendence.
     *
     * @param attendenceDTO the entity to update.
     * @return the persisted entity.
     */
    AttendenceDTO update(AttendenceDTO attendenceDTO);

    /**
     * Partially updates a attendence.
     *
     * @param attendenceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AttendenceDTO> partialUpdate(AttendenceDTO attendenceDTO);

    /**
     * Get all the attendences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AttendenceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" attendence.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AttendenceDTO> findOne(Long id);

    /**
     * Delete the "id" attendence.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
