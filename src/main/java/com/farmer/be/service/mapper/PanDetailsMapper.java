package com.farmer.be.service.mapper;

import com.farmer.be.domain.Farmer;
import com.farmer.be.domain.PanDetails;
import com.farmer.be.service.dto.FarmerDTO;
import com.farmer.be.service.dto.PanDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PanDetails} and its DTO {@link PanDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface PanDetailsMapper extends EntityMapper<PanDetailsDTO, PanDetails> {
    @Mapping(target = "farmer", source = "farmer", qualifiedByName = "farmerId")
    PanDetailsDTO toDto(PanDetails s);

    @Named("farmerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FarmerDTO toDtoFarmerId(Farmer farmer);
}
