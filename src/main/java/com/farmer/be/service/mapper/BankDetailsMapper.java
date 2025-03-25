package com.farmer.be.service.mapper;

import com.farmer.be.domain.BankDetails;
import com.farmer.be.domain.Farmer;
import com.farmer.be.service.dto.BankDetailsDTO;
import com.farmer.be.service.dto.FarmerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankDetails} and its DTO {@link BankDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface BankDetailsMapper extends EntityMapper<BankDetailsDTO, BankDetails> {
    @Mapping(target = "farmer", source = "farmer", qualifiedByName = "farmerId")
    BankDetailsDTO toDto(BankDetails s);

    @Named("farmerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FarmerDTO toDtoFarmerId(Farmer farmer);
}
