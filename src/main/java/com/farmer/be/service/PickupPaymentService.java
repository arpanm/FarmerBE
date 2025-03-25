package com.farmer.be.service;

import com.farmer.be.service.dto.PickupPaymentDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.PickupPayment}.
 */
public interface PickupPaymentService {
    /**
     * Save a pickupPayment.
     *
     * @param pickupPaymentDTO the entity to save.
     * @return the persisted entity.
     */
    PickupPaymentDTO save(PickupPaymentDTO pickupPaymentDTO);

    /**
     * Updates a pickupPayment.
     *
     * @param pickupPaymentDTO the entity to update.
     * @return the persisted entity.
     */
    PickupPaymentDTO update(PickupPaymentDTO pickupPaymentDTO);

    /**
     * Partially updates a pickupPayment.
     *
     * @param pickupPaymentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PickupPaymentDTO> partialUpdate(PickupPaymentDTO pickupPaymentDTO);

    /**
     * Get all the pickupPayments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PickupPaymentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" pickupPayment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PickupPaymentDTO> findOne(Long id);

    /**
     * Delete the "id" pickupPayment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
