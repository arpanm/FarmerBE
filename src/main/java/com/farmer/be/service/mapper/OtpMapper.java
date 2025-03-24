package com.farmer.be.service.mapper;

import com.farmer.be.domain.Farmer;
import com.farmer.be.domain.Otp;
import com.farmer.be.service.dto.FarmerDTO;
import com.farmer.be.service.dto.OtpDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Otp} and its DTO {@link OtpDTO}.
 */
@Mapper(componentModel = "spring")
public interface OtpMapper extends EntityMapper<OtpDTO, Otp> {
    @Mapping(target = "farmer", source = "farmer", qualifiedByName = "farmerId")
    OtpDTO toDto(Otp s);

    @Named("farmerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FarmerDTO toDtoFarmerId(Farmer farmer);
}
