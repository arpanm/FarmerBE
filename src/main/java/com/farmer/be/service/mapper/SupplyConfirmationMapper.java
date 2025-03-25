package com.farmer.be.service.mapper;

import com.farmer.be.domain.Crop;
import com.farmer.be.domain.Farm;
import com.farmer.be.domain.SupplyConfirmation;
import com.farmer.be.service.dto.CropDTO;
import com.farmer.be.service.dto.FarmDTO;
import com.farmer.be.service.dto.SupplyConfirmationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SupplyConfirmation} and its DTO {@link SupplyConfirmationDTO}.
 */
@Mapper(componentModel = "spring")
public interface SupplyConfirmationMapper extends EntityMapper<SupplyConfirmationDTO, SupplyConfirmation> {
    @Mapping(target = "farm", source = "farm", qualifiedByName = "farmId")
    @Mapping(target = "crop", source = "crop", qualifiedByName = "cropId")
    SupplyConfirmationDTO toDto(SupplyConfirmation s);

    @Named("farmId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FarmDTO toDtoFarmId(Farm farm);

    @Named("cropId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CropDTO toDtoCropId(Crop crop);
}
