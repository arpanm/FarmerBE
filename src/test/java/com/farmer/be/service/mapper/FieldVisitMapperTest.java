package com.farmer.be.service.mapper;

import static com.farmer.be.domain.FieldVisitAsserts.*;
import static com.farmer.be.domain.FieldVisitTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FieldVisitMapperTest {

    private FieldVisitMapper fieldVisitMapper;

    @BeforeEach
    void setUp() {
        fieldVisitMapper = new FieldVisitMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFieldVisitSample1();
        var actual = fieldVisitMapper.toEntity(fieldVisitMapper.toDto(expected));
        assertFieldVisitAllPropertiesEquals(expected, actual);
    }
}
