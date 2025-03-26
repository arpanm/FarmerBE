package com.farmer.be.repository;

import com.farmer.be.domain.CollectionCenter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class CollectionCenterRepositoryWithBagRelationshipsImpl implements CollectionCenterRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String COLLECTIONCENTERS_PARAMETER = "collectionCenters";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<CollectionCenter> fetchBagRelationships(Optional<CollectionCenter> collectionCenter) {
        return collectionCenter.map(this::fetchCrops);
    }

    @Override
    public Page<CollectionCenter> fetchBagRelationships(Page<CollectionCenter> collectionCenters) {
        return new PageImpl<>(
            fetchBagRelationships(collectionCenters.getContent()),
            collectionCenters.getPageable(),
            collectionCenters.getTotalElements()
        );
    }

    @Override
    public List<CollectionCenter> fetchBagRelationships(List<CollectionCenter> collectionCenters) {
        return Optional.of(collectionCenters).map(this::fetchCrops).orElse(Collections.emptyList());
    }

    CollectionCenter fetchCrops(CollectionCenter result) {
        return entityManager
            .createQuery(
                "select collectionCenter from CollectionCenter collectionCenter left join fetch collectionCenter.crops where collectionCenter.id = :id",
                CollectionCenter.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<CollectionCenter> fetchCrops(List<CollectionCenter> collectionCenters) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, collectionCenters.size()).forEach(index -> order.put(collectionCenters.get(index).getId(), index));
        List<CollectionCenter> result = entityManager
            .createQuery(
                "select collectionCenter from CollectionCenter collectionCenter left join fetch collectionCenter.crops where collectionCenter in :collectionCenters",
                CollectionCenter.class
            )
            .setParameter(COLLECTIONCENTERS_PARAMETER, collectionCenters)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
