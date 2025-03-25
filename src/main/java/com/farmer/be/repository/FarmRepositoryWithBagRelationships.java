package com.farmer.be.repository;

import com.farmer.be.domain.Farm;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface FarmRepositoryWithBagRelationships {
    Optional<Farm> fetchBagRelationships(Optional<Farm> farm);

    List<Farm> fetchBagRelationships(List<Farm> farms);

    Page<Farm> fetchBagRelationships(Page<Farm> farms);
}
