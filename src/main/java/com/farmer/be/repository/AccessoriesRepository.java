package com.farmer.be.repository;

import com.farmer.be.domain.Accessories;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Accessories entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AccessoriesRepository extends JpaRepository<Accessories, Long> {}
