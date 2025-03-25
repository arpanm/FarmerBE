package com.farmer.be.service;

import com.farmer.be.service.dto.SupplyConfirmationDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.SupplyConfirmation}.
 */
public interface SupplyConfirmationService {
    /**
     * Save a supplyConfirmation.
     *
     * @param supplyConfirmationDTO the entity to save.
     * @return the persisted entity.
     */
    SupplyConfirmationDTO save(SupplyConfirmationDTO supplyConfirmationDTO);

    /**
     * Updates a supplyConfirmation.
     *
     * @param supplyConfirmationDTO the entity to update.
     * @return the persisted entity.
     */
    SupplyConfirmationDTO update(SupplyConfirmationDTO supplyConfirmationDTO);

    /**
     * Partially updates a supplyConfirmation.
     *
     * @param supplyConfirmationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SupplyConfirmationDTO> partialUpdate(SupplyConfirmationDTO supplyConfirmationDTO);

    /**
     * Get all the supplyConfirmations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SupplyConfirmationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" supplyConfirmation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SupplyConfirmationDTO> findOne(Long id);

    /**
     * Delete the "id" supplyConfirmation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
