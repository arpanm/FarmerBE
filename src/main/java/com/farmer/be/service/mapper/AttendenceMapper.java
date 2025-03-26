package com.farmer.be.service.mapper;

import com.farmer.be.domain.Attendence;
import com.farmer.be.domain.Buyer;
import com.farmer.be.service.dto.AttendenceDTO;
import com.farmer.be.service.dto.BuyerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attendence} and its DTO {@link AttendenceDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttendenceMapper extends EntityMapper<AttendenceDTO, Attendence> {
    @Mapping(target = "buyer", source = "buyer", qualifiedByName = "buyerId")
    AttendenceDTO toDto(Attendence s);

    @Named("buyerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BuyerDTO toDtoBuyerId(Buyer buyer);
}
