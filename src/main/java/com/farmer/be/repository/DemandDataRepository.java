package com.farmer.be.repository;

import com.farmer.be.domain.DemandData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DemandData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandDataRepository extends JpaRepository<DemandData, Long> {}
