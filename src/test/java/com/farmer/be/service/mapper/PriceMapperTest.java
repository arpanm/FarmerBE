package com.farmer.be.service.mapper;

import static com.farmer.be.domain.PriceAsserts.*;
import static com.farmer.be.domain.PriceTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PriceMapperTest {

    private PriceMapper priceMapper;

    @BeforeEach
    void setUp() {
        priceMapper = new PriceMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPriceSample1();
        var actual = priceMapper.toEntity(priceMapper.toDto(expected));
        assertPriceAllPropertiesEquals(expected, actual);
    }
}
