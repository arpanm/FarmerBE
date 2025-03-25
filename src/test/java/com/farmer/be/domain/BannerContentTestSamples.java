package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BannerContentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static BannerContent getBannerContentSample1() {
        return new BannerContent()
            .id(1L)
            .bannerTag("bannerTag1")
            .logoPath("logoPath1")
            .imagePath("imagePath1")
            .heading("heading1")
            .subHeading("subHeading1")
            .description("description1")
            .landingLink("landingLink1")
            .landingUtm("landingUtm1")
            .pixelLink("pixelLink1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static BannerContent getBannerContentSample2() {
        return new BannerContent()
            .id(2L)
            .bannerTag("bannerTag2")
            .logoPath("logoPath2")
            .imagePath("imagePath2")
            .heading("heading2")
            .subHeading("subHeading2")
            .description("description2")
            .landingLink("landingLink2")
            .landingUtm("landingUtm2")
            .pixelLink("pixelLink2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static BannerContent getBannerContentRandomSampleGenerator() {
        return new BannerContent()
            .id(longCount.incrementAndGet())
            .bannerTag(UUID.randomUUID().toString())
            .logoPath(UUID.randomUUID().toString())
            .imagePath(UUID.randomUUID().toString())
            .heading(UUID.randomUUID().toString())
            .subHeading(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .landingLink(UUID.randomUUID().toString())
            .landingUtm(UUID.randomUUID().toString())
            .pixelLink(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
