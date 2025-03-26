package com.farmer.be.service.mapper;

import static com.farmer.be.domain.DemandDataFileAsserts.*;
import static com.farmer.be.domain.DemandDataFileTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DemandDataFileMapperTest {

    private DemandDataFileMapper demandDataFileMapper;

    @BeforeEach
    void setUp() {
        demandDataFileMapper = new DemandDataFileMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDemandDataFileSample1();
        var actual = demandDataFileMapper.toEntity(demandDataFileMapper.toDto(expected));
        assertDemandDataFileAllPropertiesEquals(expected, actual);
    }
}
