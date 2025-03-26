package com.farmer.be.service.mapper;

import com.farmer.be.domain.Category;
import com.farmer.be.domain.CollectionCenter;
import com.farmer.be.domain.Crop;
import com.farmer.be.domain.Farm;
import com.farmer.be.service.dto.CategoryDTO;
import com.farmer.be.service.dto.CollectionCenterDTO;
import com.farmer.be.service.dto.CropDTO;
import com.farmer.be.service.dto.FarmDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Crop} and its DTO {@link CropDTO}.
 */
@Mapper(componentModel = "spring")
public interface CropMapper extends EntityMapper<CropDTO, Crop> {
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryId")
    @Mapping(target = "farms", source = "farms", qualifiedByName = "farmIdSet")
    @Mapping(target = "collectionCenters", source = "collectionCenters", qualifiedByName = "collectionCenterIdSet")
    CropDTO toDto(Crop s);

    @Mapping(target = "farms", ignore = true)
    @Mapping(target = "removeFarm", ignore = true)
    @Mapping(target = "collectionCenters", ignore = true)
    @Mapping(target = "removeCollectionCenter", ignore = true)
    Crop toEntity(CropDTO cropDTO);

    @Named("categoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoryDTO toDtoCategoryId(Category category);

    @Named("farmId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FarmDTO toDtoFarmId(Farm farm);

    @Named("farmIdSet")
    default Set<FarmDTO> toDtoFarmIdSet(Set<Farm> farm) {
        return farm.stream().map(this::toDtoFarmId).collect(Collectors.toSet());
    }

    @Named("collectionCenterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CollectionCenterDTO toDtoCollectionCenterId(CollectionCenter collectionCenter);

    @Named("collectionCenterIdSet")
    default Set<CollectionCenterDTO> toDtoCollectionCenterIdSet(Set<CollectionCenter> collectionCenter) {
        return collectionCenter.stream().map(this::toDtoCollectionCenterId).collect(Collectors.toSet());
    }
}
