package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FieldVisitTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FieldVisit getFieldVisitSample1() {
        return new FieldVisit().id(1L).createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static FieldVisit getFieldVisitSample2() {
        return new FieldVisit().id(2L).createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static FieldVisit getFieldVisitRandomSampleGenerator() {
        return new FieldVisit()
            .id(longCount.incrementAndGet())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
