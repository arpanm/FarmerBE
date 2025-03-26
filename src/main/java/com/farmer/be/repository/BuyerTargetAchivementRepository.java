package com.farmer.be.repository;

import com.farmer.be.domain.BuyerTargetAchivement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BuyerTargetAchivement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BuyerTargetAchivementRepository extends JpaRepository<BuyerTargetAchivement, Long> {}
