package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PickupGradationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PickupGradation getPickupGradationSample1() {
        return new PickupGradation().id(1L).gradedBy("gradedBy1").createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static PickupGradation getPickupGradationSample2() {
        return new PickupGradation().id(2L).gradedBy("gradedBy2").createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static PickupGradation getPickupGradationRandomSampleGenerator() {
        return new PickupGradation()
            .id(longCount.incrementAndGet())
            .gradedBy(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
