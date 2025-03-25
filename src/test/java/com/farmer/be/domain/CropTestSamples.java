package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CropTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Crop getCropSample1() {
        return new Crop()
            .id(1L)
            .name("name1")
            .imagePath("imagePath1")
            .description("description1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static Crop getCropSample2() {
        return new Crop()
            .id(2L)
            .name("name2")
            .imagePath("imagePath2")
            .description("description2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static Crop getCropRandomSampleGenerator() {
        return new Crop()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .imagePath(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
