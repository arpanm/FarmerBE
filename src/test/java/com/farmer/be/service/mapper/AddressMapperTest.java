package com.farmer.be.service.mapper;

import static com.farmer.be.domain.AddressAsserts.*;
import static com.farmer.be.domain.AddressTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddressMapperTest {

    private AddressMapper addressMapper;

    @BeforeEach
    void setUp() {
        addressMapper = new AddressMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAddressSample1();
        var actual = addressMapper.toEntity(addressMapper.toDto(expected));
        assertAddressAllPropertiesEquals(expected, actual);
    }
}
