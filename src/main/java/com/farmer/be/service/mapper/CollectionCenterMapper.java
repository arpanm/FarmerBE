package com.farmer.be.service.mapper;

import com.farmer.be.domain.CollectionCenter;
import com.farmer.be.domain.Crop;
import com.farmer.be.service.dto.CollectionCenterDTO;
import com.farmer.be.service.dto.CropDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CollectionCenter} and its DTO {@link CollectionCenterDTO}.
 */
@Mapper(componentModel = "spring")
public interface CollectionCenterMapper extends EntityMapper<CollectionCenterDTO, CollectionCenter> {
    @Mapping(target = "crops", source = "crops", qualifiedByName = "cropIdSet")
    CollectionCenterDTO toDto(CollectionCenter s);

    @Mapping(target = "removeCrop", ignore = true)
    CollectionCenter toEntity(CollectionCenterDTO collectionCenterDTO);

    @Named("cropId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CropDTO toDtoCropId(Crop crop);

    @Named("cropIdSet")
    default Set<CropDTO> toDtoCropIdSet(Set<Crop> crop) {
        return crop.stream().map(this::toDtoCropId).collect(Collectors.toSet());
    }
}
