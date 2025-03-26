package com.farmer.be.service.mapper;

import com.farmer.be.domain.CollectionCenter;
import com.farmer.be.domain.LocationMapping;
import com.farmer.be.service.dto.CollectionCenterDTO;
import com.farmer.be.service.dto.LocationMappingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LocationMapping} and its DTO {@link LocationMappingDTO}.
 */
@Mapper(componentModel = "spring")
public interface LocationMappingMapper extends EntityMapper<LocationMappingDTO, LocationMapping> {
    @Mapping(target = "collectionCenter", source = "collectionCenter", qualifiedByName = "collectionCenterId")
    LocationMappingDTO toDto(LocationMapping s);

    @Named("collectionCenterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CollectionCenterDTO toDtoCollectionCenterId(CollectionCenter collectionCenter);
}
