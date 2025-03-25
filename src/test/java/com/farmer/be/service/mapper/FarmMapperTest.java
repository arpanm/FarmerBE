package com.farmer.be.service.mapper;

import static com.farmer.be.domain.FarmAsserts.*;
import static com.farmer.be.domain.FarmTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FarmMapperTest {

    private FarmMapper farmMapper;

    @BeforeEach
    void setUp() {
        farmMapper = new FarmMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFarmSample1();
        var actual = farmMapper.toEntity(farmMapper.toDto(expected));
        assertFarmAllPropertiesEquals(expected, actual);
    }
}
