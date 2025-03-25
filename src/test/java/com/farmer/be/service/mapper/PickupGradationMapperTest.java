package com.farmer.be.service.mapper;

import static com.farmer.be.domain.PickupGradationAsserts.*;
import static com.farmer.be.domain.PickupGradationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PickupGradationMapperTest {

    private PickupGradationMapper pickupGradationMapper;

    @BeforeEach
    void setUp() {
        pickupGradationMapper = new PickupGradationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPickupGradationSample1();
        var actual = pickupGradationMapper.toEntity(pickupGradationMapper.toDto(expected));
        assertPickupGradationAllPropertiesEquals(expected, actual);
    }
}
