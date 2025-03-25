package com.farmer.be.service.mapper;

import com.farmer.be.domain.Document;
import com.farmer.be.domain.Farm;
import com.farmer.be.domain.Farmer;
import com.farmer.be.service.dto.DocumentDTO;
import com.farmer.be.service.dto.FarmDTO;
import com.farmer.be.service.dto.FarmerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Document} and its DTO {@link DocumentDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentMapper extends EntityMapper<DocumentDTO, Document> {
    @Mapping(target = "farmer", source = "farmer", qualifiedByName = "farmerId")
    @Mapping(target = "farm", source = "farm", qualifiedByName = "farmId")
    DocumentDTO toDto(Document s);

    @Named("farmerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FarmerDTO toDtoFarmerId(Farmer farmer);

    @Named("farmId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FarmDTO toDtoFarmId(Farm farm);
}
