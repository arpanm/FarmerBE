package com.farmer.be.service.mapper;

import static com.farmer.be.domain.HervestPlanRuleAsserts.*;
import static com.farmer.be.domain.HervestPlanRuleTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HervestPlanRuleMapperTest {

    private HervestPlanRuleMapper hervestPlanRuleMapper;

    @BeforeEach
    void setUp() {
        hervestPlanRuleMapper = new HervestPlanRuleMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getHervestPlanRuleSample1();
        var actual = hervestPlanRuleMapper.toEntity(hervestPlanRuleMapper.toDto(expected));
        assertHervestPlanRuleAllPropertiesEquals(expected, actual);
    }
}
