package com.farmer.be.repository;

import com.farmer.be.domain.CollectionCenter;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CollectionCenterRepositoryWithBagRelationships {
    Optional<CollectionCenter> fetchBagRelationships(Optional<CollectionCenter> collectionCenter);

    List<CollectionCenter> fetchBagRelationships(List<CollectionCenter> collectionCenters);

    Page<CollectionCenter> fetchBagRelationships(Page<CollectionCenter> collectionCenters);
}
