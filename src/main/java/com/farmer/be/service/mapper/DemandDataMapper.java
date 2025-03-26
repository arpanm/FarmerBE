package com.farmer.be.service.mapper;

import com.farmer.be.domain.CollectionCenter;
import com.farmer.be.domain.DemandData;
import com.farmer.be.domain.DemandDataFile;
import com.farmer.be.service.dto.CollectionCenterDTO;
import com.farmer.be.service.dto.DemandDataDTO;
import com.farmer.be.service.dto.DemandDataFileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DemandData} and its DTO {@link DemandDataDTO}.
 */
@Mapper(componentModel = "spring")
public interface DemandDataMapper extends EntityMapper<DemandDataDTO, DemandData> {
    @Mapping(target = "file", source = "file", qualifiedByName = "demandDataFileId")
    @Mapping(target = "collectionCenter", source = "collectionCenter", qualifiedByName = "collectionCenterId")
    DemandDataDTO toDto(DemandData s);

    @Named("demandDataFileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DemandDataFileDTO toDtoDemandDataFileId(DemandDataFile demandDataFile);

    @Named("collectionCenterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CollectionCenterDTO toDtoCollectionCenterId(CollectionCenter collectionCenter);
}
