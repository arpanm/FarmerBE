package com.farmer.be.repository;

import com.farmer.be.domain.BannerContent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BannerContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BannerContentRepository extends JpaRepository<BannerContent, Long> {}
