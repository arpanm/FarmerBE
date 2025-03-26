package com.farmer.be.service.mapper;

import static com.farmer.be.domain.BuyerAsserts.*;
import static com.farmer.be.domain.BuyerTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BuyerMapperTest {

    private BuyerMapper buyerMapper;

    @BeforeEach
    void setUp() {
        buyerMapper = new BuyerMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBuyerSample1();
        var actual = buyerMapper.toEntity(buyerMapper.toDto(expected));
        assertBuyerAllPropertiesEquals(expected, actual);
    }
}
