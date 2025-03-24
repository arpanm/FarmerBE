package com.farmer.be.domain;

import static com.farmer.be.domain.AddressTestSamples.*;
import static com.farmer.be.domain.FarmerTestSamples.*;
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
}
