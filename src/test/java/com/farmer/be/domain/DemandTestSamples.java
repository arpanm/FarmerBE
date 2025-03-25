package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DemandTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Demand getDemandSample1() {
        return new Demand().id(1L).createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static Demand getDemandSample2() {
        return new Demand().id(2L).createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static Demand getDemandRandomSampleGenerator() {
        return new Demand()
            .id(longCount.incrementAndGet())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
