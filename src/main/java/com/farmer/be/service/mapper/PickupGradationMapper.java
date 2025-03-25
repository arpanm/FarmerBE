package com.farmer.be.service.mapper;

import com.farmer.be.domain.PickupGradation;
import com.farmer.be.service.dto.PickupGradationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PickupGradation} and its DTO {@link PickupGradationDTO}.
 */
@Mapper(componentModel = "spring")
public interface PickupGradationMapper extends EntityMapper<PickupGradationDTO, PickupGradation> {}
