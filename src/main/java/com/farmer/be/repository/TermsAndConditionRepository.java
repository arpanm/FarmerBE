package com.farmer.be.repository;

import com.farmer.be.domain.TermsAndCondition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TermsAndCondition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TermsAndConditionRepository extends JpaRepository<TermsAndCondition, Long> {}
