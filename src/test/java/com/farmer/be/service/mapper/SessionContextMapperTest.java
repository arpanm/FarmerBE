package com.farmer.be.service.mapper;

import static com.farmer.be.domain.SessionContextAsserts.*;
import static com.farmer.be.domain.SessionContextTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SessionContextMapperTest {

    private SessionContextMapper sessionContextMapper;

    @BeforeEach
    void setUp() {
        sessionContextMapper = new SessionContextMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSessionContextSample1();
        var actual = sessionContextMapper.toEntity(sessionContextMapper.toDto(expected));
        assertSessionContextAllPropertiesEquals(expected, actual);
    }
}
