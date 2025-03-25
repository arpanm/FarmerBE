package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SupplyConfirmationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SupplyConfirmation getSupplyConfirmationSample1() {
        return new SupplyConfirmation().id(1L).createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static SupplyConfirmation getSupplyConfirmationSample2() {
        return new SupplyConfirmation().id(2L).createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static SupplyConfirmation getSupplyConfirmationRandomSampleGenerator() {
        return new SupplyConfirmation()
            .id(longCount.incrementAndGet())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
