package com.farmer.be.service.mapper;

import static com.farmer.be.domain.AccessoriesAsserts.*;
import static com.farmer.be.domain.AccessoriesTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccessoriesMapperTest {

    private AccessoriesMapper accessoriesMapper;

    @BeforeEach
    void setUp() {
        accessoriesMapper = new AccessoriesMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAccessoriesSample1();
        var actual = accessoriesMapper.toEntity(accessoriesMapper.toDto(expected));
        assertAccessoriesAllPropertiesEquals(expected, actual);
    }
}
