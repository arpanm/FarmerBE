package com.farmer.be.service;

import com.farmer.be.service.dto.CropDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.Crop}.
 */
public interface CropService {
    /**
     * Save a crop.
     *
     * @param cropDTO the entity to save.
     * @return the persisted entity.
     */
    CropDTO save(CropDTO cropDTO);

    /**
     * Updates a crop.
     *
     * @param cropDTO the entity to update.
     * @return the persisted entity.
     */
    CropDTO update(CropDTO cropDTO);

    /**
     * Partially updates a crop.
     *
     * @param cropDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CropDTO> partialUpdate(CropDTO cropDTO);

    /**
     * Get all the crops.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CropDTO> findAll(Pageable pageable);

    /**
     * Get all the CropDTO where DemandData is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<CropDTO> findAllWhereDemandDataIsNull();

    /**
     * Get the "id" crop.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CropDTO> findOne(Long id);

    /**
     * Delete the "id" crop.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
