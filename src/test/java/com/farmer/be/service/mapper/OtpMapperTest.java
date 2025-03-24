package com.farmer.be.service.mapper;

import static com.farmer.be.domain.OtpAsserts.*;
import static com.farmer.be.domain.OtpTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OtpMapperTest {

    private OtpMapper otpMapper;

    @BeforeEach
    void setUp() {
        otpMapper = new OtpMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOtpSample1();
        var actual = otpMapper.toEntity(otpMapper.toDto(expected));
        assertOtpAllPropertiesEquals(expected, actual);
    }
}
