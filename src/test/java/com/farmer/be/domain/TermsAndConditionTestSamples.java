package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TermsAndConditionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TermsAndCondition getTermsAndConditionSample1() {
        return new TermsAndCondition().id(1L).termsLink("termsLink1").createddBy("createddBy1").updatedBy("updatedBy1");
    }

    public static TermsAndCondition getTermsAndConditionSample2() {
        return new TermsAndCondition().id(2L).termsLink("termsLink2").createddBy("createddBy2").updatedBy("updatedBy2");
    }

    public static TermsAndCondition getTermsAndConditionRandomSampleGenerator() {
        return new TermsAndCondition()
            .id(longCount.incrementAndGet())
            .termsLink(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
