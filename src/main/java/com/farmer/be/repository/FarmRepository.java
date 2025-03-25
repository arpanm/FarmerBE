package com.farmer.be.repository;

import com.farmer.be.domain.Farm;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Farm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {}
