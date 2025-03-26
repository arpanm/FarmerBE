package com.farmer.be.service.mapper;

import com.farmer.be.domain.Buyer;
import com.farmer.be.domain.CollectionCenter;
import com.farmer.be.service.dto.BuyerDTO;
import com.farmer.be.service.dto.CollectionCenterDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Buyer} and its DTO {@link BuyerDTO}.
 */
@Mapper(componentModel = "spring")
public interface BuyerMapper extends EntityMapper<BuyerDTO, Buyer> {
    @Mapping(target = "collectionCenter", source = "collectionCenter", qualifiedByName = "collectionCenterId")
    BuyerDTO toDto(Buyer s);

    @Named("collectionCenterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CollectionCenterDTO toDtoCollectionCenterId(CollectionCenter collectionCenter);
}
