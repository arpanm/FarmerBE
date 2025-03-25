package com.farmer.be.service;

import com.farmer.be.service.dto.PanDetailsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.PanDetails}.
 */
public interface PanDetailsService {
    /**
     * Save a panDetails.
     *
     * @param panDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    PanDetailsDTO save(PanDetailsDTO panDetailsDTO);

    /**
     * Updates a panDetails.
     *
     * @param panDetailsDTO the entity to update.
     * @return the persisted entity.
     */
    PanDetailsDTO update(PanDetailsDTO panDetailsDTO);

    /**
     * Partially updates a panDetails.
     *
     * @param panDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PanDetailsDTO> partialUpdate(PanDetailsDTO panDetailsDTO);

    /**
     * Get all the panDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PanDetailsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" panDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PanDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" panDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
