package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BuyerTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Buyer getBuyerSample1() {
        return new Buyer().id(1L).name("name1").email("email1").phone(1L).createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static Buyer getBuyerSample2() {
        return new Buyer().id(2L).name("name2").email("email2").phone(2L).createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static Buyer getBuyerRandomSampleGenerator() {
        return new Buyer()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .phone(longCount.incrementAndGet())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
