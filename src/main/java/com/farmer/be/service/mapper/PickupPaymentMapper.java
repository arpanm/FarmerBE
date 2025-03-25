package com.farmer.be.service.mapper;

import com.farmer.be.domain.PickupPayment;
import com.farmer.be.service.dto.PickupPaymentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PickupPayment} and its DTO {@link PickupPaymentDTO}.
 */
@Mapper(componentModel = "spring")
public interface PickupPaymentMapper extends EntityMapper<PickupPaymentDTO, PickupPayment> {}
