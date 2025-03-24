package com.farmer.be.service.mapper;

import com.farmer.be.domain.Document;
import com.farmer.be.domain.Farmer;
import com.farmer.be.service.dto.DocumentDTO;
import com.farmer.be.service.dto.FarmerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Document} and its DTO {@link DocumentDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentMapper extends EntityMapper<DocumentDTO, Document> {
    @Mapping(target = "farmer", source = "farmer", qualifiedByName = "farmerId")
    DocumentDTO toDto(Document s);

    @Named("farmerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FarmerDTO toDtoFarmerId(Farmer farmer);
}
