package com.farmer.be.service.mapper;

import com.farmer.be.domain.Buyer;
import com.farmer.be.domain.Farm;
import com.farmer.be.domain.FieldVisit;
import com.farmer.be.service.dto.BuyerDTO;
import com.farmer.be.service.dto.FarmDTO;
import com.farmer.be.service.dto.FieldVisitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FieldVisit} and its DTO {@link FieldVisitDTO}.
 */
@Mapper(componentModel = "spring")
public interface FieldVisitMapper extends EntityMapper<FieldVisitDTO, FieldVisit> {
    @Mapping(target = "buyer", source = "buyer", qualifiedByName = "buyerId")
    @Mapping(target = "farm", source = "farm", qualifiedByName = "farmId")
    FieldVisitDTO toDto(FieldVisit s);

    @Named("buyerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BuyerDTO toDtoBuyerId(Buyer buyer);

    @Named("farmId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FarmDTO toDtoFarmId(Farm farm);
}
