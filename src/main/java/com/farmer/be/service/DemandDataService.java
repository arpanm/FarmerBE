package com.farmer.be.service;

import com.farmer.be.service.dto.DemandDataDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.DemandData}.
 */
public interface DemandDataService {
    /**
     * Save a demandData.
     *
     * @param demandDataDTO the entity to save.
     * @return the persisted entity.
     */
    DemandDataDTO save(DemandDataDTO demandDataDTO);

    /**
     * Updates a demandData.
     *
     * @param demandDataDTO the entity to update.
     * @return the persisted entity.
     */
    DemandDataDTO update(DemandDataDTO demandDataDTO);

    /**
     * Partially updates a demandData.
     *
     * @param demandDataDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DemandDataDTO> partialUpdate(DemandDataDTO demandDataDTO);

    /**
     * Get all the demandData.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DemandDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" demandData.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DemandDataDTO> findOne(Long id);

    /**
     * Delete the "id" demandData.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
