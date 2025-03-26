package com.farmer.be.service;

import com.farmer.be.service.dto.DemandDataFileDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.DemandDataFile}.
 */
public interface DemandDataFileService {
    /**
     * Save a demandDataFile.
     *
     * @param demandDataFileDTO the entity to save.
     * @return the persisted entity.
     */
    DemandDataFileDTO save(DemandDataFileDTO demandDataFileDTO);

    /**
     * Updates a demandDataFile.
     *
     * @param demandDataFileDTO the entity to update.
     * @return the persisted entity.
     */
    DemandDataFileDTO update(DemandDataFileDTO demandDataFileDTO);

    /**
     * Partially updates a demandDataFile.
     *
     * @param demandDataFileDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DemandDataFileDTO> partialUpdate(DemandDataFileDTO demandDataFileDTO);

    /**
     * Get all the demandDataFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DemandDataFileDTO> findAll(Pageable pageable);

    /**
     * Get the "id" demandDataFile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DemandDataFileDTO> findOne(Long id);

    /**
     * Delete the "id" demandDataFile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
