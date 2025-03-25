package com.farmer.be.repository;

import com.farmer.be.domain.HervestPlan;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HervestPlan entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HervestPlanRepository extends JpaRepository<HervestPlan, Long> {}
