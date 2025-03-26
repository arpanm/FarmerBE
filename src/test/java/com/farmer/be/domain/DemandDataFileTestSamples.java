package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DemandDataFileTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DemandDataFile getDemandDataFileSample1() {
        return new DemandDataFile()
            .id(1L)
            .fileName("fileName1")
            .uploadedBy("uploadedBy1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static DemandDataFile getDemandDataFileSample2() {
        return new DemandDataFile()
            .id(2L)
            .fileName("fileName2")
            .uploadedBy("uploadedBy2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static DemandDataFile getDemandDataFileRandomSampleGenerator() {
        return new DemandDataFile()
            .id(longCount.incrementAndGet())
            .fileName(UUID.randomUUID().toString())
            .uploadedBy(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
