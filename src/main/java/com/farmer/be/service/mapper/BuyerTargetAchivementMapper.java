package com.farmer.be.service.mapper;

import com.farmer.be.domain.Buyer;
import com.farmer.be.domain.BuyerTargetAchivement;
import com.farmer.be.service.dto.BuyerDTO;
import com.farmer.be.service.dto.BuyerTargetAchivementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BuyerTargetAchivement} and its DTO {@link BuyerTargetAchivementDTO}.
 */
@Mapper(componentModel = "spring")
public interface BuyerTargetAchivementMapper extends EntityMapper<BuyerTargetAchivementDTO, BuyerTargetAchivement> {
    @Mapping(target = "buyer", source = "buyer", qualifiedByName = "buyerId")
    BuyerTargetAchivementDTO toDto(BuyerTargetAchivement s);

    @Named("buyerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BuyerDTO toDtoBuyerId(Buyer buyer);
}
