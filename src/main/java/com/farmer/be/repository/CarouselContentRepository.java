package com.farmer.be.repository;

import com.farmer.be.domain.CarouselContent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CarouselContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarouselContentRepository extends JpaRepository<CarouselContent, Long> {}
