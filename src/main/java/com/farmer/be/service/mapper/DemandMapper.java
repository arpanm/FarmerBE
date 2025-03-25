package com.farmer.be.service.mapper;

import com.farmer.be.domain.Crop;
import com.farmer.be.domain.Demand;
import com.farmer.be.service.dto.CropDTO;
import com.farmer.be.service.dto.DemandDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Demand} and its DTO {@link DemandDTO}.
 */
@Mapper(componentModel = "spring")
public interface DemandMapper extends EntityMapper<DemandDTO, Demand> {
    @Mapping(target = "crop", source = "crop", qualifiedByName = "cropId")
    DemandDTO toDto(Demand s);

    @Named("cropId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CropDTO toDtoCropId(Crop crop);
}
