package com.farmer.be.service.mapper;

import com.farmer.be.domain.Crop;
import com.farmer.be.domain.Farm;
import com.farmer.be.domain.HervestPlanRule;
import com.farmer.be.service.dto.CropDTO;
import com.farmer.be.service.dto.FarmDTO;
import com.farmer.be.service.dto.HervestPlanRuleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link HervestPlanRule} and its DTO {@link HervestPlanRuleDTO}.
 */
@Mapper(componentModel = "spring")
public interface HervestPlanRuleMapper extends EntityMapper<HervestPlanRuleDTO, HervestPlanRule> {
    @Mapping(target = "farm", source = "farm", qualifiedByName = "farmId")
    @Mapping(target = "crop", source = "crop", qualifiedByName = "cropId")
    HervestPlanRuleDTO toDto(HervestPlanRule s);

    @Named("farmId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FarmDTO toDtoFarmId(Farm farm);

    @Named("cropId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CropDTO toDtoCropId(Crop crop);
}
