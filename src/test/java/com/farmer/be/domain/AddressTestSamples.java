package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AddressTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Address getAddressSample1() {
        return new Address()
            .id(1L)
            .line1("line11")
            .line2("line21")
            .landmark("landmark1")
            .city("city1")
            .state("state1")
            .country("country1")
            .pincode(1L)
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static Address getAddressSample2() {
        return new Address()
            .id(2L)
            .line1("line12")
            .line2("line22")
            .landmark("landmark2")
            .city("city2")
            .state("state2")
            .country("country2")
            .pincode(2L)
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static Address getAddressRandomSampleGenerator() {
        return new Address()
            .id(longCount.incrementAndGet())
            .line1(UUID.randomUUID().toString())
            .line2(UUID.randomUUID().toString())
            .landmark(UUID.randomUUID().toString())
            .city(UUID.randomUUID().toString())
            .state(UUID.randomUUID().toString())
            .country(UUID.randomUUID().toString())
            .pincode(longCount.incrementAndGet())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
