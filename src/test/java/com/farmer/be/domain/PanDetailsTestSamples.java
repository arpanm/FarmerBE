package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PanDetailsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PanDetails getPanDetailsSample1() {
        return new PanDetails().id(1L).pan("pan1").name("name1").country("country1").createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static PanDetails getPanDetailsSample2() {
        return new PanDetails().id(2L).pan("pan2").name("name2").country("country2").createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static PanDetails getPanDetailsRandomSampleGenerator() {
        return new PanDetails()
            .id(longCount.incrementAndGet())
            .pan(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .country(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
