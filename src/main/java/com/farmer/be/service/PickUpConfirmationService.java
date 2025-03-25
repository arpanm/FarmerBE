package com.farmer.be.service;

import com.farmer.be.service.dto.PickUpConfirmationDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.PickUpConfirmation}.
 */
public interface PickUpConfirmationService {
    /**
     * Save a pickUpConfirmation.
     *
     * @param pickUpConfirmationDTO the entity to save.
     * @return the persisted entity.
     */
    PickUpConfirmationDTO save(PickUpConfirmationDTO pickUpConfirmationDTO);

    /**
     * Updates a pickUpConfirmation.
     *
     * @param pickUpConfirmationDTO the entity to update.
     * @return the persisted entity.
     */
    PickUpConfirmationDTO update(PickUpConfirmationDTO pickUpConfirmationDTO);

    /**
     * Partially updates a pickUpConfirmation.
     *
     * @param pickUpConfirmationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PickUpConfirmationDTO> partialUpdate(PickUpConfirmationDTO pickUpConfirmationDTO);

    /**
     * Get all the pickUpConfirmations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PickUpConfirmationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" pickUpConfirmation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PickUpConfirmationDTO> findOne(Long id);

    /**
     * Delete the "id" pickUpConfirmation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
