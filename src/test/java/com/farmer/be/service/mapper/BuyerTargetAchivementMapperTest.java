package com.farmer.be.service.mapper;

import static com.farmer.be.domain.BuyerTargetAchivementAsserts.*;
import static com.farmer.be.domain.BuyerTargetAchivementTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BuyerTargetAchivementMapperTest {

    private BuyerTargetAchivementMapper buyerTargetAchivementMapper;

    @BeforeEach
    void setUp() {
        buyerTargetAchivementMapper = new BuyerTargetAchivementMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBuyerTargetAchivementSample1();
        var actual = buyerTargetAchivementMapper.toEntity(buyerTargetAchivementMapper.toDto(expected));
        assertBuyerTargetAchivementAllPropertiesEquals(expected, actual);
    }
}
