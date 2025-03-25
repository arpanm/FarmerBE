package com.farmer.be.repository;

import com.farmer.be.domain.PanDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PanDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PanDetailsRepository extends JpaRepository<PanDetails, Long> {}
