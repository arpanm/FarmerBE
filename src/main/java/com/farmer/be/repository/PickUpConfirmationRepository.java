package com.farmer.be.repository;

import com.farmer.be.domain.PickUpConfirmation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PickUpConfirmation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PickUpConfirmationRepository extends JpaRepository<PickUpConfirmation, Long> {}
