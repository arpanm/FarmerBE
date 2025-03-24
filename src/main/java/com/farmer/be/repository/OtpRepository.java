package com.farmer.be.repository;

import com.farmer.be.domain.Otp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Otp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {}
