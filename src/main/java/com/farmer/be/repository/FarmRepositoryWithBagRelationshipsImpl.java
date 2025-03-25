package com.farmer.be.repository;

import com.farmer.be.domain.Farm;
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
public class FarmRepositoryWithBagRelationshipsImpl implements FarmRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String FARMS_PARAMETER = "farms";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Farm> fetchBagRelationships(Optional<Farm> farm) {
        return farm.map(this::fetchAccessories).map(this::fetchCrops);
    }

    @Override
    public Page<Farm> fetchBagRelationships(Page<Farm> farms) {
        return new PageImpl<>(fetchBagRelationships(farms.getContent()), farms.getPageable(), farms.getTotalElements());
    }

    @Override
    public List<Farm> fetchBagRelationships(List<Farm> farms) {
        return Optional.of(farms).map(this::fetchAccessories).map(this::fetchCrops).orElse(Collections.emptyList());
    }

    Farm fetchAccessories(Farm result) {
        return entityManager
            .createQuery("select farm from Farm farm left join fetch farm.accessories where farm.id = :id", Farm.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Farm> fetchAccessories(List<Farm> farms) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, farms.size()).forEach(index -> order.put(farms.get(index).getId(), index));
        List<Farm> result = entityManager
            .createQuery("select farm from Farm farm left join fetch farm.accessories where farm in :farms", Farm.class)
            .setParameter(FARMS_PARAMETER, farms)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Farm fetchCrops(Farm result) {
        return entityManager
            .createQuery("select farm from Farm farm left join fetch farm.crops where farm.id = :id", Farm.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Farm> fetchCrops(List<Farm> farms) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, farms.size()).forEach(index -> order.put(farms.get(index).getId(), index));
        List<Farm> result = entityManager
            .createQuery("select farm from Farm farm left join fetch farm.crops where farm in :farms", Farm.class)
            .setParameter(FARMS_PARAMETER, farms)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
