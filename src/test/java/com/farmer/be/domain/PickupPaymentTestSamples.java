package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PickupPaymentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PickupPayment getPickupPaymentSample1() {
        return new PickupPayment()
            .id(1L)
            .transactionId("transactionId1")
            .details("details1")
            .paymentUpdatedBy("paymentUpdatedBy1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static PickupPayment getPickupPaymentSample2() {
        return new PickupPayment()
            .id(2L)
            .transactionId("transactionId2")
            .details("details2")
            .paymentUpdatedBy("paymentUpdatedBy2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static PickupPayment getPickupPaymentRandomSampleGenerator() {
        return new PickupPayment()
            .id(longCount.incrementAndGet())
            .transactionId(UUID.randomUUID().toString())
            .details(UUID.randomUUID().toString())
            .paymentUpdatedBy(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
