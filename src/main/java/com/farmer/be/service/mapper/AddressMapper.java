package com.farmer.be.service.mapper;

import com.farmer.be.domain.Address;
import com.farmer.be.domain.Farmer;
import com.farmer.be.service.dto.AddressDTO;
import com.farmer.be.service.dto.FarmerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
    @Mapping(target = "farmer", source = "farmer", qualifiedByName = "farmerId")
    AddressDTO toDto(Address s);

    @Named("farmerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FarmerDTO toDtoFarmerId(Farmer farmer);
}
