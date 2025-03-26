package com.farmer.be.service.mapper;

import static com.farmer.be.domain.CollectionCenterAsserts.*;
import static com.farmer.be.domain.CollectionCenterTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CollectionCenterMapperTest {

    private CollectionCenterMapper collectionCenterMapper;

    @BeforeEach
    void setUp() {
        collectionCenterMapper = new CollectionCenterMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCollectionCenterSample1();
        var actual = collectionCenterMapper.toEntity(collectionCenterMapper.toDto(expected));
        assertCollectionCenterAllPropertiesEquals(expected, actual);
    }
}
