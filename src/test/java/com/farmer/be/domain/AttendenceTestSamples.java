package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AttendenceTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Attendence getAttendenceSample1() {
        return new Attendence().id(1L).createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static Attendence getAttendenceSample2() {
        return new Attendence().id(2L).createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static Attendence getAttendenceRandomSampleGenerator() {
        return new Attendence()
            .id(longCount.incrementAndGet())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
