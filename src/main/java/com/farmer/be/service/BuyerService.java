package com.farmer.be.service;

import com.farmer.be.service.dto.BuyerDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.Buyer}.
 */
public interface BuyerService {
    /**
     * Save a buyer.
     *
     * @param buyerDTO the entity to save.
     * @return the persisted entity.
     */
    BuyerDTO save(BuyerDTO buyerDTO);

    /**
     * Updates a buyer.
     *
     * @param buyerDTO the entity to update.
     * @return the persisted entity.
     */
    BuyerDTO update(BuyerDTO buyerDTO);

    /**
     * Partially updates a buyer.
     *
     * @param buyerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BuyerDTO> partialUpdate(BuyerDTO buyerDTO);

    /**
     * Get all the buyers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BuyerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" buyer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BuyerDTO> findOne(Long id);

    /**
     * Delete the "id" buyer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
