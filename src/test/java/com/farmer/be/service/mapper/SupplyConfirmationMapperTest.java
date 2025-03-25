package com.farmer.be.service.mapper;

import static com.farmer.be.domain.SupplyConfirmationAsserts.*;
import static com.farmer.be.domain.SupplyConfirmationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyConfirmationMapperTest {

    private SupplyConfirmationMapper supplyConfirmationMapper;

    @BeforeEach
    void setUp() {
        supplyConfirmationMapper = new SupplyConfirmationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSupplyConfirmationSample1();
        var actual = supplyConfirmationMapper.toEntity(supplyConfirmationMapper.toDto(expected));
        assertSupplyConfirmationAllPropertiesEquals(expected, actual);
    }
}
