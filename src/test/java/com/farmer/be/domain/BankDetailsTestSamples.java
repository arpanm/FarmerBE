package com.farmer.be.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BankDetailsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static BankDetails getBankDetailsSample1() {
        return new BankDetails()
            .id(1L)
            .name("name1")
            .accountNumber("accountNumber1")
            .ifsC("ifsC1")
            .bankName("bankName1")
            .branchName("branchName1")
            .createddBy("createddBy1")
            .updatedBy("updatedBy1");
    }

    public static BankDetails getBankDetailsSample2() {
        return new BankDetails()
            .id(2L)
            .name("name2")
            .accountNumber("accountNumber2")
            .ifsC("ifsC2")
            .bankName("bankName2")
            .branchName("branchName2")
            .createddBy("createddBy2")
            .updatedBy("updatedBy2");
    }

    public static BankDetails getBankDetailsRandomSampleGenerator() {
        return new BankDetails()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .accountNumber(UUID.randomUUID().toString())
            .ifsC(UUID.randomUUID().toString())
            .bankName(UUID.randomUUID().toString())
            .branchName(UUID.randomUUID().toString())
            .createddBy(UUID.randomUUID().toString())
            .updatedBy(UUID.randomUUID().toString());
    }
}
