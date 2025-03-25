package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class HervestPlanRuleTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static HervestPlanRule getHervestPlanRuleSample1() {
        return new HervestPlanRule()
            .id(1L)
            .daysOfWeek("daysOfWeek1")
            .daysOfMonth("daysOfMonth1")
            .daysOfYear("daysOfYear1")
            .alternateXdays(1L)
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static HervestPlanRule getHervestPlanRuleSample2() {
        return new HervestPlanRule()
            .id(2L)
            .daysOfWeek("daysOfWeek2")
            .daysOfMonth("daysOfMonth2")
            .daysOfYear("daysOfYear2")
            .alternateXdays(2L)
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static HervestPlanRule getHervestPlanRuleRandomSampleGenerator() {
        return new HervestPlanRule()
            .id(longCount.incrementAndGet())
            .daysOfWeek(UUID.randomUUID().toString())
            .daysOfMonth(UUID.randomUUID().toString())
            .daysOfYear(UUID.randomUUID().toString())
            .alternateXdays(longCount.incrementAndGet())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
