package com.farmer.be.service.mapper;

import com.farmer.be.domain.Farmer;
import com.farmer.be.domain.TermsAndCondition;
import com.farmer.be.service.dto.FarmerDTO;
import com.farmer.be.service.dto.TermsAndConditionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TermsAndCondition} and its DTO {@link TermsAndConditionDTO}.
 */
@Mapper(componentModel = "spring")
public interface TermsAndConditionMapper extends EntityMapper<TermsAndConditionDTO, TermsAndCondition> {
    @Mapping(target = "farmer", source = "farmer", qualifiedByName = "farmerId")
    TermsAndConditionDTO toDto(TermsAndCondition s);

    @Named("farmerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FarmerDTO toDtoFarmerId(Farmer farmer);
}
