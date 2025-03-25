package com.farmer.be.service;

import com.farmer.be.service.dto.HervestPlanDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.HervestPlan}.
 */
public interface HervestPlanService {
    /**
     * Save a hervestPlan.
     *
     * @param hervestPlanDTO the entity to save.
     * @return the persisted entity.
     */
    HervestPlanDTO save(HervestPlanDTO hervestPlanDTO);

    /**
     * Updates a hervestPlan.
     *
     * @param hervestPlanDTO the entity to update.
     * @return the persisted entity.
     */
    HervestPlanDTO update(HervestPlanDTO hervestPlanDTO);

    /**
     * Partially updates a hervestPlan.
     *
     * @param hervestPlanDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HervestPlanDTO> partialUpdate(HervestPlanDTO hervestPlanDTO);

    /**
     * Get all the hervestPlans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HervestPlanDTO> findAll(Pageable pageable);

    /**
     * Get the "id" hervestPlan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HervestPlanDTO> findOne(Long id);

    /**
     * Delete the "id" hervestPlan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
