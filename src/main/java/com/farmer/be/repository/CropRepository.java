package com.farmer.be.repository;

import com.farmer.be.domain.Crop;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Crop entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CropRepository extends JpaRepository<Crop, Long> {}
