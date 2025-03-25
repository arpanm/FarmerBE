package com.farmer.be.service.mapper;

import static com.farmer.be.domain.BannerContentAsserts.*;
import static com.farmer.be.domain.BannerContentTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BannerContentMapperTest {

    private BannerContentMapper bannerContentMapper;

    @BeforeEach
    void setUp() {
        bannerContentMapper = new BannerContentMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBannerContentSample1();
        var actual = bannerContentMapper.toEntity(bannerContentMapper.toDto(expected));
        assertBannerContentAllPropertiesEquals(expected, actual);
    }
}
