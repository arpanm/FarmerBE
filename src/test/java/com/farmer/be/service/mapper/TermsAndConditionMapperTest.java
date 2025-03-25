package com.farmer.be.service.mapper;

import static com.farmer.be.domain.TermsAndConditionAsserts.*;
import static com.farmer.be.domain.TermsAndConditionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TermsAndConditionMapperTest {

    private TermsAndConditionMapper termsAndConditionMapper;

    @BeforeEach
    void setUp() {
        termsAndConditionMapper = new TermsAndConditionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTermsAndConditionSample1();
        var actual = termsAndConditionMapper.toEntity(termsAndConditionMapper.toDto(expected));
        assertTermsAndConditionAllPropertiesEquals(expected, actual);
    }
}
