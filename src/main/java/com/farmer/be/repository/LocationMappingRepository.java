package com.farmer.be.repository;

import com.farmer.be.domain.LocationMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LocationMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationMappingRepository extends JpaRepository<LocationMapping, Long> {}
