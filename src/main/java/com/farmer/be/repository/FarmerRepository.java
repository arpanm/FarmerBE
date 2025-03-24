package com.farmer.be.repository;

import com.farmer.be.domain.Farmer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Farmer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FarmerRepository extends JpaRepository<Farmer, Long> {}
