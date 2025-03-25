package com.farmer.be.service.mapper;

import static com.farmer.be.domain.PanDetailsAsserts.*;
import static com.farmer.be.domain.PanDetailsTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PanDetailsMapperTest {

    private PanDetailsMapper panDetailsMapper;

    @BeforeEach
    void setUp() {
        panDetailsMapper = new PanDetailsMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPanDetailsSample1();
        var actual = panDetailsMapper.toEntity(panDetailsMapper.toDto(expected));
        assertPanDetailsAllPropertiesEquals(expected, actual);
    }
}
