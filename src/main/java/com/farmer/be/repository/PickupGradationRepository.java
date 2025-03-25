package com.farmer.be.repository;

import com.farmer.be.domain.PickupGradation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PickupGradation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PickupGradationRepository extends JpaRepository<PickupGradation, Long> {}
