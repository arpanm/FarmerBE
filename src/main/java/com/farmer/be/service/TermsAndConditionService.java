package com.farmer.be.service;

import com.farmer.be.service.dto.TermsAndConditionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.TermsAndCondition}.
 */
public interface TermsAndConditionService {
    /**
     * Save a termsAndCondition.
     *
     * @param termsAndConditionDTO the entity to save.
     * @return the persisted entity.
     */
    TermsAndConditionDTO save(TermsAndConditionDTO termsAndConditionDTO);

    /**
     * Updates a termsAndCondition.
     *
     * @param termsAndConditionDTO the entity to update.
     * @return the persisted entity.
     */
    TermsAndConditionDTO update(TermsAndConditionDTO termsAndConditionDTO);

    /**
     * Partially updates a termsAndCondition.
     *
     * @param termsAndConditionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TermsAndConditionDTO> partialUpdate(TermsAndConditionDTO termsAndConditionDTO);

    /**
     * Get all the termsAndConditions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TermsAndConditionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" termsAndCondition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TermsAndConditionDTO> findOne(Long id);

    /**
     * Delete the "id" termsAndCondition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
