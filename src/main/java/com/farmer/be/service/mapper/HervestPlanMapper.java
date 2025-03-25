package com.farmer.be.service.mapper;

import com.farmer.be.domain.Crop;
import com.farmer.be.domain.Farm;
import com.farmer.be.domain.HervestPlan;
import com.farmer.be.service.dto.CropDTO;
import com.farmer.be.service.dto.FarmDTO;
import com.farmer.be.service.dto.HervestPlanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HervestPlan} and its DTO {@link HervestPlanDTO}.
 */
@Mapper(componentModel = "spring")
public interface HervestPlanMapper extends EntityMapper<HervestPlanDTO, HervestPlan> {
    @Mapping(target = "farm", source = "farm", qualifiedByName = "farmId")
    @Mapping(target = "crop", source = "crop", qualifiedByName = "cropId")
    HervestPlanDTO toDto(HervestPlan s);

    @Named("farmId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FarmDTO toDtoFarmId(Farm farm);

    @Named("cropId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CropDTO toDtoCropId(Crop crop);
}
