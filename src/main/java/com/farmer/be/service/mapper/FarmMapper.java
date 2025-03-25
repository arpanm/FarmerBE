package com.farmer.be.service.mapper;

import com.farmer.be.domain.Farm;
import com.farmer.be.domain.Farmer;
import com.farmer.be.service.dto.FarmDTO;
import com.farmer.be.service.dto.FarmerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Farm} and its DTO {@link FarmDTO}.
 */
@Mapper(componentModel = "spring")
public interface FarmMapper extends EntityMapper<FarmDTO, Farm> {
    @Mapping(target = "farmer", source = "farmer", qualifiedByName = "farmerId")
    FarmDTO toDto(Farm s);

    @Named("farmerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FarmerDTO toDtoFarmerId(Farmer farmer);
}
