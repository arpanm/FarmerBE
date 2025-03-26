package com.farmer.be.service.mapper;

import com.farmer.be.domain.CollectionCenter;
import com.farmer.be.domain.Crop;
import com.farmer.be.domain.DemandData;
import com.farmer.be.domain.DemandDataFile;
import com.farmer.be.service.dto.CollectionCenterDTO;
import com.farmer.be.service.dto.CropDTO;
import com.farmer.be.service.dto.DemandDataDTO;
import com.farmer.be.service.dto.DemandDataFileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DemandData} and its DTO {@link DemandDataDTO}.
 */
@Mapper(componentModel = "spring")
public interface DemandDataMapper extends EntityMapper<DemandDataDTO, DemandData> {
    @Mapping(target = "crop", source = "crop", qualifiedByName = "cropId")
    @Mapping(target = "collectionCenter", source = "collectionCenter", qualifiedByName = "collectionCenterId")
    @Mapping(target = "file", source = "file", qualifiedByName = "demandDataFileId")
    DemandDataDTO toDto(DemandData s);

    @Named("cropId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CropDTO toDtoCropId(Crop crop);

    @Named("collectionCenterId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CollectionCenterDTO toDtoCollectionCenterId(CollectionCenter collectionCenter);

    @Named("demandDataFileId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DemandDataFileDTO toDtoDemandDataFileId(DemandDataFile demandDataFile);
}
