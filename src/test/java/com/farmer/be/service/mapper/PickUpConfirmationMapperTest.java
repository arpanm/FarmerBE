package com.farmer.be.service.mapper;

import static com.farmer.be.domain.PickUpConfirmationAsserts.*;
import static com.farmer.be.domain.PickUpConfirmationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PickUpConfirmationMapperTest {

    private PickUpConfirmationMapper pickUpConfirmationMapper;

    @BeforeEach
    void setUp() {
        pickUpConfirmationMapper = new PickUpConfirmationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPickUpConfirmationSample1();
        var actual = pickUpConfirmationMapper.toEntity(pickUpConfirmationMapper.toDto(expected));
        assertPickUpConfirmationAllPropertiesEquals(expected, actual);
    }
}
