package com.farmer.be.repository;

import com.farmer.be.domain.CollectionCenter;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CollectionCenter entity.
 *
 * When extending this class, extend CollectionCenterRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface CollectionCenterRepository extends CollectionCenterRepositoryWithBagRelationships, JpaRepository<CollectionCenter, Long> {
    default Optional<CollectionCenter> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<CollectionCenter> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<CollectionCenter> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
