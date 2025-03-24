package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DocumentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Document getDocumentSample1() {
        return new Document().id(1L).docPath("docPath1").createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static Document getDocumentSample2() {
        return new Document().id(2L).docPath("docPath2").createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static Document getDocumentRandomSampleGenerator() {
        return new Document()
            .id(longCount.incrementAndGet())
            .docPath(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
