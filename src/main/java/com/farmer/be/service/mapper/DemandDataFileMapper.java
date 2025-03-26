package com.farmer.be.service.mapper;

import com.farmer.be.domain.DemandDataFile;
import com.farmer.be.service.dto.DemandDataFileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DemandDataFile} and its DTO {@link DemandDataFileDTO}.
 */
@Mapper(componentModel = "spring")
public interface DemandDataFileMapper extends EntityMapper<DemandDataFileDTO, DemandDataFile> {}
