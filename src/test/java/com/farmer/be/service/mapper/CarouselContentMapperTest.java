package com.farmer.be.service.mapper;

import static com.farmer.be.domain.CarouselContentAsserts.*;
import static com.farmer.be.domain.CarouselContentTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CarouselContentMapperTest {

    private CarouselContentMapper carouselContentMapper;

    @BeforeEach
    void setUp() {
        carouselContentMapper = new CarouselContentMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCarouselContentSample1();
        var actual = carouselContentMapper.toEntity(carouselContentMapper.toDto(expected));
        assertCarouselContentAllPropertiesEquals(expected, actual);
    }
}
