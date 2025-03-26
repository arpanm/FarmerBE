package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CollectionCenterTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CollectionCenter getCollectionCenterSample1() {
        return new CollectionCenter()
            .id(1L)
            .name("name1")
            .ccId("ccId1")
            .ffdcCode("ffdcCode1")
            .ffdcName("ffdcName1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static CollectionCenter getCollectionCenterSample2() {
        return new CollectionCenter()
            .id(2L)
            .name("name2")
            .ccId("ccId2")
            .ffdcCode("ffdcCode2")
            .ffdcName("ffdcName2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static CollectionCenter getCollectionCenterRandomSampleGenerator() {
        return new CollectionCenter()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .ccId(UUID.randomUUID().toString())
            .ffdcCode(UUID.randomUUID().toString())
            .ffdcName(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
