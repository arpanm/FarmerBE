package com.farmer.be.service.mapper;

import static com.farmer.be.domain.DemandAsserts.*;
import static com.farmer.be.domain.DemandTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DemandMapperTest {

    private DemandMapper demandMapper;

    @BeforeEach
    void setUp() {
        demandMapper = new DemandMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDemandSample1();
        var actual = demandMapper.toEntity(demandMapper.toDto(expected));
        assertDemandAllPropertiesEquals(expected, actual);
    }
}
