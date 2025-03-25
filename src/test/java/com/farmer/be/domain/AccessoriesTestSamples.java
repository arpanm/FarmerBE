package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AccessoriesTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Accessories getAccessoriesSample1() {
        return new Accessories()
            .id(1L)
            .name("name1")
            .imagePath("imagePath1")
            .description("description1")
            .orderNo(1L)
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static Accessories getAccessoriesSample2() {
        return new Accessories()
            .id(2L)
            .name("name2")
            .imagePath("imagePath2")
            .description("description2")
            .orderNo(2L)
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static Accessories getAccessoriesRandomSampleGenerator() {
        return new Accessories()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .imagePath(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .orderNo(longCount.incrementAndGet())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
