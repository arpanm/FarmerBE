package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DemandDataTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DemandData getDemandDataSample1() {
        return new DemandData()
            .id(1L)
            .fromCPC("fromCPC1")
            .toCC("toCC1")
            .pCode("pCode1")
            .article("article1")
            .description("description1")
            .uom("uom1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static DemandData getDemandDataSample2() {
        return new DemandData()
            .id(2L)
            .fromCPC("fromCPC2")
            .toCC("toCC2")
            .pCode("pCode2")
            .article("article2")
            .description("description2")
            .uom("uom2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static DemandData getDemandDataRandomSampleGenerator() {
        return new DemandData()
            .id(longCount.incrementAndGet())
            .fromCPC(UUID.randomUUID().toString())
            .toCC(UUID.randomUUID().toString())
            .pCode(UUID.randomUUID().toString())
            .article(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .uom(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
