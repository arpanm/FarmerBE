package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PriceTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Price getPriceSample1() {
        return new Price().id(1L).createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static Price getPriceSample2() {
        return new Price().id(2L).createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static Price getPriceRandomSampleGenerator() {
        return new Price().id(longCount.incrementAndGet()).createddBy(UUID.randomUUID().toString()).updatedBy(UUID.randomUUID().toString());
    }
}
