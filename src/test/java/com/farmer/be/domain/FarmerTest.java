package com.farmer.be.domain;

import static com.farmer.be.domain.AddressTestSamples.*;
import static com.farmer.be.domain.DocumentTestSamples.*;
import static com.farmer.be.domain.FarmerTestSamples.*;
import static com.farmer.be.domain.OtpTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FarmerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Farmer.class);
        Farmer farmer1 = getFarmerSample1();
        Farmer farmer2 = new Farmer();
        assertThat(farmer1).isNotEqualTo(farmer2);

        farmer2.setId(farmer1.getId());
        assertThat(farmer1).isEqualTo(farmer2);

        farmer2 = getFarmerSample2();
        assertThat(farmer1).isNotEqualTo(farmer2);
    }

    @Test
    void addressTest() {
        Farmer farmer = getFarmerRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        farmer.addAddress(addressBack);
        assertThat(farmer.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getFarmer()).isEqualTo(farmer);

        farmer.removeAddress(addressBack);
        assertThat(farmer.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getFarmer()).isNull();

        farmer.addresses(new HashSet<>(Set.of(addressBack)));
        assertThat(farmer.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getFarmer()).isEqualTo(farmer);

        farmer.setAddresses(new HashSet<>());
        assertThat(farmer.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getFarmer()).isNull();
    }

    @Test
    void documentTest() {
        Farmer farmer = getFarmerRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        farmer.addDocument(documentBack);
        assertThat(farmer.getDocuments()).containsOnly(documentBack);
        assertThat(documentBack.getFarmer()).isEqualTo(farmer);

        farmer.removeDocument(documentBack);
        assertThat(farmer.getDocuments()).doesNotContain(documentBack);
        assertThat(documentBack.getFarmer()).isNull();

        farmer.documents(new HashSet<>(Set.of(documentBack)));
        assertThat(farmer.getDocuments()).containsOnly(documentBack);
        assertThat(documentBack.getFarmer()).isEqualTo(farmer);

        farmer.setDocuments(new HashSet<>());
        assertThat(farmer.getDocuments()).doesNotContain(documentBack);
        assertThat(documentBack.getFarmer()).isNull();
    }

    @Test
    void otpTest() {
        Farmer farmer = getFarmerRandomSampleGenerator();
        Otp otpBack = getOtpRandomSampleGenerator();

        farmer.addOtp(otpBack);
        assertThat(farmer.getOtps()).containsOnly(otpBack);
        assertThat(otpBack.getFarmer()).isEqualTo(farmer);

        farmer.removeOtp(otpBack);
        assertThat(farmer.getOtps()).doesNotContain(otpBack);
        assertThat(otpBack.getFarmer()).isNull();

        farmer.otps(new HashSet<>(Set.of(otpBack)));
        assertThat(farmer.getOtps()).containsOnly(otpBack);
        assertThat(otpBack.getFarmer()).isEqualTo(farmer);

        farmer.setOtps(new HashSet<>());
        assertThat(farmer.getOtps()).doesNotContain(otpBack);
        assertThat(otpBack.getFarmer()).isNull();
    }
}
