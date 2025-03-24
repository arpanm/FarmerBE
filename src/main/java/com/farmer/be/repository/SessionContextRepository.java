package com.farmer.be.repository;

import com.farmer.be.domain.SessionContext;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SessionContext entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SessionContextRepository extends JpaRepository<SessionContext, Long> {}
