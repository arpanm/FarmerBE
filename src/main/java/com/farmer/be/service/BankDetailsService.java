package com.farmer.be.service;

import com.farmer.be.service.dto.BankDetailsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.BankDetails}.
 */
public interface BankDetailsService {
    /**
     * Save a bankDetails.
     *
     * @param bankDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    BankDetailsDTO save(BankDetailsDTO bankDetailsDTO);

    /**
     * Updates a bankDetails.
     *
     * @param bankDetailsDTO the entity to update.
     * @return the persisted entity.
     */
    BankDetailsDTO update(BankDetailsDTO bankDetailsDTO);

    /**
     * Partially updates a bankDetails.
     *
     * @param bankDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BankDetailsDTO> partialUpdate(BankDetailsDTO bankDetailsDTO);

    /**
     * Get all the bankDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BankDetailsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bankDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BankDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" bankDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
