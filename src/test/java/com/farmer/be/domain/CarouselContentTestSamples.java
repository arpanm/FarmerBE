package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CarouselContentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CarouselContent getCarouselContentSample1() {
        return new CarouselContent()
            .id(1L)
            .carouselTag("carouselTag1")
            .viewMoreLink("viewMoreLink1")
            .viewMoreUtm("viewMoreUtm1")
            .pixelLink("pixelLink1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static CarouselContent getCarouselContentSample2() {
        return new CarouselContent()
            .id(2L)
            .carouselTag("carouselTag2")
            .viewMoreLink("viewMoreLink2")
            .viewMoreUtm("viewMoreUtm2")
            .pixelLink("pixelLink2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static CarouselContent getCarouselContentRandomSampleGenerator() {
        return new CarouselContent()
            .id(longCount.incrementAndGet())
            .carouselTag(UUID.randomUUID().toString())
            .viewMoreLink(UUID.randomUUID().toString())
            .viewMoreUtm(UUID.randomUUID().toString())
            .pixelLink(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
