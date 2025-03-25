package com.farmer.be.service.mapper;

import static com.farmer.be.domain.PickupPaymentAsserts.*;
import static com.farmer.be.domain.PickupPaymentTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PickupPaymentMapperTest {

    private PickupPaymentMapper pickupPaymentMapper;

    @BeforeEach
    void setUp() {
        pickupPaymentMapper = new PickupPaymentMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPickupPaymentSample1();
        var actual = pickupPaymentMapper.toEntity(pickupPaymentMapper.toDto(expected));
        assertPickupPaymentAllPropertiesEquals(expected, actual);
    }
}
