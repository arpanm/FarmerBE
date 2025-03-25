package com.farmer.be.service;

import com.farmer.be.service.dto.PickupGradationDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.PickupGradation}.
 */
public interface PickupGradationService {
    /**
     * Save a pickupGradation.
     *
     * @param pickupGradationDTO the entity to save.
     * @return the persisted entity.
     */
    PickupGradationDTO save(PickupGradationDTO pickupGradationDTO);

    /**
     * Updates a pickupGradation.
     *
     * @param pickupGradationDTO the entity to update.
     * @return the persisted entity.
     */
    PickupGradationDTO update(PickupGradationDTO pickupGradationDTO);

    /**
     * Partially updates a pickupGradation.
     *
     * @param pickupGradationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PickupGradationDTO> partialUpdate(PickupGradationDTO pickupGradationDTO);

    /**
     * Get all the pickupGradations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PickupGradationDTO> findAll(Pageable pageable);

    /**
     * Get all the PickupGradationDTO where PickupItem is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<PickupGradationDTO> findAllWherePickupItemIsNull();

    /**
     * Get the "id" pickupGradation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PickupGradationDTO> findOne(Long id);

    /**
     * Delete the "id" pickupGradation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
