package com.farmer.be.domain;

import static com.farmer.be.domain.AddressTestSamples.*;
import static com.farmer.be.domain.FarmerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AddressTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Address.class);
        Address address1 = getAddressSample1();
        Address address2 = new Address();
        assertThat(address1).isNotEqualTo(address2);

        address2.setId(address1.getId());
        assertThat(address1).isEqualTo(address2);

        address2 = getAddressSample2();
        assertThat(address1).isNotEqualTo(address2);
    }

    @Test
    void farmerTest() {
        Address address = getAddressRandomSampleGenerator();
        Farmer farmerBack = getFarmerRandomSampleGenerator();

        address.setFarmer(farmerBack);
        assertThat(address.getFarmer()).isEqualTo(farmerBack);

        address.farmer(null);
        assertThat(address.getFarmer()).isNull();
    }
}
