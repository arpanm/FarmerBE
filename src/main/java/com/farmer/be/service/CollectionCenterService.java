package com.farmer.be.service;

import com.farmer.be.service.dto.CollectionCenterDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.CollectionCenter}.
 */
public interface CollectionCenterService {
    /**
     * Save a collectionCenter.
     *
     * @param collectionCenterDTO the entity to save.
     * @return the persisted entity.
     */
    CollectionCenterDTO save(CollectionCenterDTO collectionCenterDTO);

    /**
     * Updates a collectionCenter.
     *
     * @param collectionCenterDTO the entity to update.
     * @return the persisted entity.
     */
    CollectionCenterDTO update(CollectionCenterDTO collectionCenterDTO);

    /**
     * Partially updates a collectionCenter.
     *
     * @param collectionCenterDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CollectionCenterDTO> partialUpdate(CollectionCenterDTO collectionCenterDTO);

    /**
     * Get all the collectionCenters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CollectionCenterDTO> findAll(Pageable pageable);

    /**
     * Get all the collectionCenters with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CollectionCenterDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" collectionCenter.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CollectionCenterDTO> findOne(Long id);

    /**
     * Delete the "id" collectionCenter.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
