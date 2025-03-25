package com.farmer.be.service.mapper;

import static com.farmer.be.domain.HervestPlanAsserts.*;
import static com.farmer.be.domain.HervestPlanTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HervestPlanMapperTest {

    private HervestPlanMapper hervestPlanMapper;

    @BeforeEach
    void setUp() {
        hervestPlanMapper = new HervestPlanMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getHervestPlanSample1();
        var actual = hervestPlanMapper.toEntity(hervestPlanMapper.toDto(expected));
        assertHervestPlanAllPropertiesEquals(expected, actual);
    }
}
