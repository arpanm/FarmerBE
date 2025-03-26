package com.farmer.be.service;

import com.farmer.be.service.dto.FieldVisitDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.FieldVisit}.
 */
public interface FieldVisitService {
    /**
     * Save a fieldVisit.
     *
     * @param fieldVisitDTO the entity to save.
     * @return the persisted entity.
     */
    FieldVisitDTO save(FieldVisitDTO fieldVisitDTO);

    /**
     * Updates a fieldVisit.
     *
     * @param fieldVisitDTO the entity to update.
     * @return the persisted entity.
     */
    FieldVisitDTO update(FieldVisitDTO fieldVisitDTO);

    /**
     * Partially updates a fieldVisit.
     *
     * @param fieldVisitDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FieldVisitDTO> partialUpdate(FieldVisitDTO fieldVisitDTO);

    /**
     * Get all the fieldVisits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FieldVisitDTO> findAll(Pageable pageable);

    /**
     * Get the "id" fieldVisit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FieldVisitDTO> findOne(Long id);

    /**
     * Delete the "id" fieldVisit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
