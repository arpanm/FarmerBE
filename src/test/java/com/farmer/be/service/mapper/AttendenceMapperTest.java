package com.farmer.be.service.mapper;

import static com.farmer.be.domain.AttendenceAsserts.*;
import static com.farmer.be.domain.AttendenceTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttendenceMapperTest {

    private AttendenceMapper attendenceMapper;

    @BeforeEach
    void setUp() {
        attendenceMapper = new AttendenceMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAttendenceSample1();
        var actual = attendenceMapper.toEntity(attendenceMapper.toDto(expected));
        assertAttendenceAllPropertiesEquals(expected, actual);
    }
}
