package com.farmer.be.repository;

import com.farmer.be.domain.DemandDataFile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DemandDataFile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandDataFileRepository extends JpaRepository<DemandDataFile, Long> {}
