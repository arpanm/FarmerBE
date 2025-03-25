package com.farmer.be.service.mapper;

import com.farmer.be.domain.Accessories;
import com.farmer.be.domain.Crop;
import com.farmer.be.domain.Farm;
import com.farmer.be.domain.Farmer;
import com.farmer.be.service.dto.AccessoriesDTO;
import com.farmer.be.service.dto.CropDTO;
import com.farmer.be.service.dto.FarmDTO;
import com.farmer.be.service.dto.FarmerDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Farm} and its DTO {@link FarmDTO}.
 */
@Mapper(componentModel = "spring")
public interface FarmMapper extends EntityMapper<FarmDTO, Farm> {
    @Mapping(target = "crops", source = "crops", qualifiedByName = "cropIdSet")
    @Mapping(target = "accessories", source = "accessories", qualifiedByName = "accessoriesIdSet")
    @Mapping(target = "farmer", source = "farmer", qualifiedByName = "farmerId")
    FarmDTO toDto(Farm s);

    @Mapping(target = "removeCrop", ignore = true)
    @Mapping(target = "removeAccessories", ignore = true)
    Farm toEntity(FarmDTO farmDTO);

    @Named("cropId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CropDTO toDtoCropId(Crop crop);

    @Named("cropIdSet")
    default Set<CropDTO> toDtoCropIdSet(Set<Crop> crop) {
        return crop.stream().map(this::toDtoCropId).collect(Collectors.toSet());
    }

    @Named("accessoriesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AccessoriesDTO toDtoAccessoriesId(Accessories accessories);

    @Named("accessoriesIdSet")
    default Set<AccessoriesDTO> toDtoAccessoriesIdSet(Set<Accessories> accessories) {
        return accessories.stream().map(this::toDtoAccessoriesId).collect(Collectors.toSet());
    }

    @Named("farmerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FarmerDTO toDtoFarmerId(Farmer farmer);
}
