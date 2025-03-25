package com.farmer.be.service.mapper;

import com.farmer.be.domain.Crop;
import com.farmer.be.domain.Farm;
import com.farmer.be.domain.PickUpConfirmation;
import com.farmer.be.domain.PickupGradation;
import com.farmer.be.service.dto.CropDTO;
import com.farmer.be.service.dto.FarmDTO;
import com.farmer.be.service.dto.PickUpConfirmationDTO;
import com.farmer.be.service.dto.PickupGradationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PickUpConfirmation} and its DTO {@link PickUpConfirmationDTO}.
 */
@Mapper(componentModel = "spring")
public interface PickUpConfirmationMapper extends EntityMapper<PickUpConfirmationDTO, PickUpConfirmation> {
    @Mapping(target = "grade", source = "grade", qualifiedByName = "pickupGradationId")
    @Mapping(target = "farm", source = "farm", qualifiedByName = "farmId")
    @Mapping(target = "crop", source = "crop", qualifiedByName = "cropId")
    PickUpConfirmationDTO toDto(PickUpConfirmation s);

    @Named("pickupGradationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PickupGradationDTO toDtoPickupGradationId(PickupGradation pickupGradation);

    @Named("farmId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FarmDTO toDtoFarmId(Farm farm);

    @Named("cropId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CropDTO toDtoCropId(Crop crop);
}
