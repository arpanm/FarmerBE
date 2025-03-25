package com.farmer.be.service;

import com.farmer.be.service.dto.AccessoriesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.Accessories}.
 */
public interface AccessoriesService {
    /**
     * Save a accessories.
     *
     * @param accessoriesDTO the entity to save.
     * @return the persisted entity.
     */
    AccessoriesDTO save(AccessoriesDTO accessoriesDTO);

    /**
     * Updates a accessories.
     *
     * @param accessoriesDTO the entity to update.
     * @return the persisted entity.
     */
    AccessoriesDTO update(AccessoriesDTO accessoriesDTO);

    /**
     * Partially updates a accessories.
     *
     * @param accessoriesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AccessoriesDTO> partialUpdate(AccessoriesDTO accessoriesDTO);

    /**
     * Get all the accessories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AccessoriesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" accessories.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AccessoriesDTO> findOne(Long id);

    /**
     * Delete the "id" accessories.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
