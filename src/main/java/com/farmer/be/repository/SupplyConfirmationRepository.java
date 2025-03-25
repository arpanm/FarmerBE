package com.farmer.be.repository;

import com.farmer.be.domain.SupplyConfirmation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SupplyConfirmation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupplyConfirmationRepository extends JpaRepository<SupplyConfirmation, Long> {}
