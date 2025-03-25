package com.farmer.be.repository;

import com.farmer.be.domain.BankDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BankDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankDetailsRepository extends JpaRepository<BankDetails, Long> {}
