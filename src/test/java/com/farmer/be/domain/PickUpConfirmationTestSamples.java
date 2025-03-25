package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PickUpConfirmationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PickUpConfirmation getPickUpConfirmationSample1() {
        return new PickUpConfirmation()
            .id(1L)
            .pickupBy("pickupBy1")
            .pickupTime("pickupTime1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static PickUpConfirmation getPickUpConfirmationSample2() {
        return new PickUpConfirmation()
            .id(2L)
            .pickupBy("pickupBy2")
            .pickupTime("pickupTime2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static PickUpConfirmation getPickUpConfirmationRandomSampleGenerator() {
        return new PickUpConfirmation()
            .id(longCount.incrementAndGet())
            .pickupBy(UUID.randomUUID().toString())
            .pickupTime(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
