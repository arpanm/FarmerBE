package com.farmer.be.repository;

import com.farmer.be.domain.HervestPlanRule;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HervestPlanRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HervestPlanRuleRepository extends JpaRepository<HervestPlanRule, Long> {}
