package com.farmer.be.service.mapper;

import static com.farmer.be.domain.DemandDataAsserts.*;
import static com.farmer.be.domain.DemandDataTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DemandDataMapperTest {

    private DemandDataMapper demandDataMapper;

    @BeforeEach
    void setUp() {
        demandDataMapper = new DemandDataMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDemandDataSample1();
        var actual = demandDataMapper.toEntity(demandDataMapper.toDto(expected));
        assertDemandDataAllPropertiesEquals(expected, actual);
    }
}
