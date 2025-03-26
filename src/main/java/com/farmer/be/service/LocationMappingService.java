package com.farmer.be.service;

import com.farmer.be.service.dto.LocationMappingDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.LocationMapping}.
 */
public interface LocationMappingService {
    /**
     * Save a locationMapping.
     *
     * @param locationMappingDTO the entity to save.
     * @return the persisted entity.
     */
    LocationMappingDTO save(LocationMappingDTO locationMappingDTO);

    /**
     * Updates a locationMapping.
     *
     * @param locationMappingDTO the entity to update.
     * @return the persisted entity.
     */
    LocationMappingDTO update(LocationMappingDTO locationMappingDTO);

    /**
     * Partially updates a locationMapping.
     *
     * @param locationMappingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LocationMappingDTO> partialUpdate(LocationMappingDTO locationMappingDTO);

    /**
     * Get all the locationMappings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LocationMappingDTO> findAll(Pageable pageable);

    /**
     * Get the "id" locationMapping.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LocationMappingDTO> findOne(Long id);

    /**
     * Delete the "id" locationMapping.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
