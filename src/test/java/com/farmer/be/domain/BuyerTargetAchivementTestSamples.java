package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BuyerTargetAchivementTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static BuyerTargetAchivement getBuyerTargetAchivementSample1() {
        return new BuyerTargetAchivement()
            .id(1L)
            .labour(1L)
            .farmVisit(1L)
            .achivementLabour(1L)
            .achivementFarmVisit(1L)
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static BuyerTargetAchivement getBuyerTargetAchivementSample2() {
        return new BuyerTargetAchivement()
            .id(2L)
            .labour(2L)
            .farmVisit(2L)
            .achivementLabour(2L)
            .achivementFarmVisit(2L)
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static BuyerTargetAchivement getBuyerTargetAchivementRandomSampleGenerator() {
        return new BuyerTargetAchivement()
            .id(longCount.incrementAndGet())
            .labour(longCount.incrementAndGet())
            .farmVisit(longCount.incrementAndGet())
            .achivementLabour(longCount.incrementAndGet())
            .achivementFarmVisit(longCount.incrementAndGet())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
