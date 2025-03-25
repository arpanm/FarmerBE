package com.farmer.be.domain;

import static com.farmer.be.domain.BannerContentTestSamples.*;
import static com.farmer.be.domain.CarouselContentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BannerContentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BannerContent.class);
        BannerContent bannerContent1 = getBannerContentSample1();
        BannerContent bannerContent2 = new BannerContent();
        assertThat(bannerContent1).isNotEqualTo(bannerContent2);

        bannerContent2.setId(bannerContent1.getId());
        assertThat(bannerContent1).isEqualTo(bannerContent2);

        bannerContent2 = getBannerContentSample2();
        assertThat(bannerContent1).isNotEqualTo(bannerContent2);
    }

    @Test
    void holdingCarouselTest() {
        BannerContent bannerContent = getBannerContentRandomSampleGenerator();
        CarouselContent carouselContentBack = getCarouselContentRandomSampleGenerator();

        bannerContent.setHoldingCarousel(carouselContentBack);
        assertThat(bannerContent.getHoldingCarousel()).isEqualTo(carouselContentBack);

        bannerContent.holdingCarousel(null);
        assertThat(bannerContent.getHoldingCarousel()).isNull();
    }
}
