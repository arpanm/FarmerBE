package com.farmer.be.service;

import com.farmer.be.service.dto.BuyerTargetAchivementDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.BuyerTargetAchivement}.
 */
public interface BuyerTargetAchivementService {
    /**
     * Save a buyerTargetAchivement.
     *
     * @param buyerTargetAchivementDTO the entity to save.
     * @return the persisted entity.
     */
    BuyerTargetAchivementDTO save(BuyerTargetAchivementDTO buyerTargetAchivementDTO);

    /**
     * Updates a buyerTargetAchivement.
     *
     * @param buyerTargetAchivementDTO the entity to update.
     * @return the persisted entity.
     */
    BuyerTargetAchivementDTO update(BuyerTargetAchivementDTO buyerTargetAchivementDTO);

    /**
     * Partially updates a buyerTargetAchivement.
     *
     * @param buyerTargetAchivementDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BuyerTargetAchivementDTO> partialUpdate(BuyerTargetAchivementDTO buyerTargetAchivementDTO);

    /**
     * Get all the buyerTargetAchivements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BuyerTargetAchivementDTO> findAll(Pageable pageable);

    /**
     * Get the "id" buyerTargetAchivement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BuyerTargetAchivementDTO> findOne(Long id);

    /**
     * Delete the "id" buyerTargetAchivement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
