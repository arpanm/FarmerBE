package com.farmer.be.service;

import com.farmer.be.service.dto.SessionContextDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.SessionContext}.
 */
public interface SessionContextService {
    /**
     * Save a sessionContext.
     *
     * @param sessionContextDTO the entity to save.
     * @return the persisted entity.
     */
    SessionContextDTO save(SessionContextDTO sessionContextDTO);

    /**
     * Updates a sessionContext.
     *
     * @param sessionContextDTO the entity to update.
     * @return the persisted entity.
     */
    SessionContextDTO update(SessionContextDTO sessionContextDTO);

    /**
     * Partially updates a sessionContext.
     *
     * @param sessionContextDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SessionContextDTO> partialUpdate(SessionContextDTO sessionContextDTO);

    /**
     * Get all the sessionContexts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SessionContextDTO> findAll(Pageable pageable);

    /**
     * Get the "id" sessionContext.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SessionContextDTO> findOne(Long id);

    /**
     * Delete the "id" sessionContext.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
