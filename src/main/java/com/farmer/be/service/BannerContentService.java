package com.farmer.be.service;

import com.farmer.be.service.dto.BannerContentDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.farmer.be.domain.BannerContent}.
 */
public interface BannerContentService {
    /**
     * Save a bannerContent.
     *
     * @param bannerContentDTO the entity to save.
     * @return the persisted entity.
     */
    BannerContentDTO save(BannerContentDTO bannerContentDTO);

    /**
     * Updates a bannerContent.
     *
     * @param bannerContentDTO the entity to update.
     * @return the persisted entity.
     */
    BannerContentDTO update(BannerContentDTO bannerContentDTO);

    /**
     * Partially updates a bannerContent.
     *
     * @param bannerContentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BannerContentDTO> partialUpdate(BannerContentDTO bannerContentDTO);

    /**
     * Get all the bannerContents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BannerContentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bannerContent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BannerContentDTO> findOne(Long id);

    /**
     * Delete the "id" bannerContent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
