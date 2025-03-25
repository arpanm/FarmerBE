package com.farmer.be.service.mapper;

import static com.farmer.be.domain.BankDetailsAsserts.*;
import static com.farmer.be.domain.BankDetailsTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankDetailsMapperTest {

    private BankDetailsMapper bankDetailsMapper;

    @BeforeEach
    void setUp() {
        bankDetailsMapper = new BankDetailsMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBankDetailsSample1();
        var actual = bankDetailsMapper.toEntity(bankDetailsMapper.toDto(expected));
        assertBankDetailsAllPropertiesEquals(expected, actual);
    }
}
