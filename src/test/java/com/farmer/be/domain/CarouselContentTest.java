package com.farmer.be.domain;

import static com.farmer.be.domain.BannerContentTestSamples.*;
import static com.farmer.be.domain.CarouselContentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CarouselContentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarouselContent.class);
        CarouselContent carouselContent1 = getCarouselContentSample1();
        CarouselContent carouselContent2 = new CarouselContent();
        assertThat(carouselContent1).isNotEqualTo(carouselContent2);

        carouselContent2.setId(carouselContent1.getId());
        assertThat(carouselContent1).isEqualTo(carouselContent2);

        carouselContent2 = getCarouselContentSample2();
        assertThat(carouselContent1).isNotEqualTo(carouselContent2);
    }

    @Test
    void bannersTest() {
        CarouselContent carouselContent = getCarouselContentRandomSampleGenerator();
        BannerContent bannerContentBack = getBannerContentRandomSampleGenerator();

        carouselContent.addBanners(bannerContentBack);
        assertThat(carouselContent.getBanners()).containsOnly(bannerContentBack);
        assertThat(bannerContentBack.getHoldingCarousel()).isEqualTo(carouselContent);

        carouselContent.removeBanners(bannerContentBack);
        assertThat(carouselContent.getBanners()).doesNotContain(bannerContentBack);
        assertThat(bannerContentBack.getHoldingCarousel()).isNull();

        carouselContent.banners(new HashSet<>(Set.of(bannerContentBack)));
        assertThat(carouselContent.getBanners()).containsOnly(bannerContentBack);
        assertThat(bannerContentBack.getHoldingCarousel()).isEqualTo(carouselContent);

        carouselContent.setBanners(new HashSet<>());
        assertThat(carouselContent.getBanners()).doesNotContain(bannerContentBack);
        assertThat(bannerContentBack.getHoldingCarousel()).isNull();
    }
}
