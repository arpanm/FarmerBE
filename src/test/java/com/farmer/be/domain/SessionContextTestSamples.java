package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SessionContextTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SessionContext getSessionContextSample1() {
        return new SessionContext().id(1L).sessionId("sessionId1").farmerId("farmerId1").createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static SessionContext getSessionContextSample2() {
        return new SessionContext().id(2L).sessionId("sessionId2").farmerId("farmerId2").createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static SessionContext getSessionContextRandomSampleGenerator() {
        return new SessionContext()
            .id(longCount.incrementAndGet())
            .sessionId(UUID.randomUUID().toString())
            .farmerId(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
