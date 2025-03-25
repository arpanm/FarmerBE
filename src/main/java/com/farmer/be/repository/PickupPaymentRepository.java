package com.farmer.be.repository;

import com.farmer.be.domain.PickupPayment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PickupPayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PickupPaymentRepository extends JpaRepository<PickupPayment, Long> {}
