package com.farmer.be.service.mapper;

import com.farmer.be.domain.Farmer;
import com.farmer.be.service.dto.FarmerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Farmer} and its DTO {@link FarmerDTO}.
 */
@Mapper(componentModel = "spring")
public interface FarmerMapper extends EntityMapper<FarmerDTO, Farmer> {}
