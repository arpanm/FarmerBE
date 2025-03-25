package com.farmer.be.service;

import com.farmer.be.service.dto.HervestPlanRuleDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.HervestPlanRule}.
 */
public interface HervestPlanRuleService {
    /**
     * Save a hervestPlanRule.
     *
     * @param hervestPlanRuleDTO the entity to save.
     * @return the persisted entity.
     */
    HervestPlanRuleDTO save(HervestPlanRuleDTO hervestPlanRuleDTO);

    /**
     * Updates a hervestPlanRule.
     *
     * @param hervestPlanRuleDTO the entity to update.
     * @return the persisted entity.
     */
    HervestPlanRuleDTO update(HervestPlanRuleDTO hervestPlanRuleDTO);

    /**
     * Partially updates a hervestPlanRule.
     *
     * @param hervestPlanRuleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<HervestPlanRuleDTO> partialUpdate(HervestPlanRuleDTO hervestPlanRuleDTO);

    /**
     * Get all the hervestPlanRules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<HervestPlanRuleDTO> findAll(Pageable pageable);

    /**
     * Get the "id" hervestPlanRule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<HervestPlanRuleDTO> findOne(Long id);

    /**
     * Delete the "id" hervestPlanRule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
