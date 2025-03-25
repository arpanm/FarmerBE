package com.farmer.be.service.mapper;

import com.farmer.be.domain.Crop;
import com.farmer.be.domain.Price;
import com.farmer.be.service.dto.CropDTO;
import com.farmer.be.service.dto.PriceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Price} and its DTO {@link PriceDTO}.
 */
@Mapper(componentModel = "spring")
public interface PriceMapper extends EntityMapper<PriceDTO, Price> {
    @Mapping(target = "crop", source = "crop", qualifiedByName = "cropId")
    PriceDTO toDto(Price s);

    @Named("cropId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CropDTO toDtoCropId(Crop crop);
}
