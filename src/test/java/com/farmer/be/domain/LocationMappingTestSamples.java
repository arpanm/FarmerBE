package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LocationMappingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static LocationMapping getLocationMappingSample1() {
        return new LocationMapping().id(1L).areaName("areaName1").pincode(1L).createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static LocationMapping getLocationMappingSample2() {
        return new LocationMapping().id(2L).areaName("areaName2").pincode(2L).createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static LocationMapping getLocationMappingRandomSampleGenerator() {
        return new LocationMapping()
            .id(longCount.incrementAndGet())
            .areaName(UUID.randomUUID().toString())
            .pincode(longCount.incrementAndGet())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
