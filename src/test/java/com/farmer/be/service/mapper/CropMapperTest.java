package com.farmer.be.service.mapper;

import static com.farmer.be.domain.CropAsserts.*;
import static com.farmer.be.domain.CropTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CropMapperTest {

    private CropMapper cropMapper;

    @BeforeEach
    void setUp() {
        cropMapper = new CropMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCropSample1();
        var actual = cropMapper.toEntity(cropMapper.toDto(expected));
        assertCropAllPropertiesEquals(expected, actual);
    }
}
