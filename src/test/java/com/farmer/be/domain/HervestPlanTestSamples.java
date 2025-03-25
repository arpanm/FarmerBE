package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class HervestPlanTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static HervestPlan getHervestPlanSample1() {
        return new HervestPlan().id(1L).createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static HervestPlan getHervestPlanSample2() {
        return new HervestPlan().id(2L).createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static HervestPlan getHervestPlanRandomSampleGenerator() {
        return new HervestPlan()
            .id(longCount.incrementAndGet())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
