package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FarmTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Farm getFarmSample1() {
        return new Farm()
            .id(1L)
            .ownerName("ownerName1")
            .relationshipWithOwner("relationshipWithOwner1")
            .farmDocumentNo("farmDocumentNo1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static Farm getFarmSample2() {
        return new Farm()
            .id(2L)
            .ownerName("ownerName2")
            .relationshipWithOwner("relationshipWithOwner2")
            .farmDocumentNo("farmDocumentNo2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static Farm getFarmRandomSampleGenerator() {
        return new Farm()
            .id(longCount.incrementAndGet())
            .ownerName(UUID.randomUUID().toString())
            .relationshipWithOwner(UUID.randomUUID().toString())
            .farmDocumentNo(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
