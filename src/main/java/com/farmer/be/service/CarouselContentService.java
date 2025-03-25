package com.farmer.be.service;

import com.farmer.be.service.dto.CarouselContentDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.CarouselContent}.
 */
public interface CarouselContentService {
    /**
     * Save a carouselContent.
     *
     * @param carouselContentDTO the entity to save.
     * @return the persisted entity.
     */
    CarouselContentDTO save(CarouselContentDTO carouselContentDTO);

    /**
     * Updates a carouselContent.
     *
     * @param carouselContentDTO the entity to update.
     * @return the persisted entity.
     */
    CarouselContentDTO update(CarouselContentDTO carouselContentDTO);

    /**
     * Partially updates a carouselContent.
     *
     * @param carouselContentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CarouselContentDTO> partialUpdate(CarouselContentDTO carouselContentDTO);

    /**
     * Get all the carouselContents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CarouselContentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" carouselContent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CarouselContentDTO> findOne(Long id);

    /**
     * Delete the "id" carouselContent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
