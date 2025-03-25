package com.farmer.be.domain;

import static com.farmer.be.domain.AddressTestSamples.*;
import static com.farmer.be.domain.DocumentTestSamples.*;
import static com.farmer.be.domain.FarmTestSamples.*;
import static com.farmer.be.domain.FarmerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
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
    void documentTest() {
        Address address = getAddressRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        address.addDocument(documentBack);
        assertThat(address.getDocuments()).containsOnly(documentBack);
        assertThat(documentBack.getAddress()).isEqualTo(address);

        address.removeDocument(documentBack);
        assertThat(address.getDocuments()).doesNotContain(documentBack);
        assertThat(documentBack.getAddress()).isNull();

        address.documents(new HashSet<>(Set.of(documentBack)));
        assertThat(address.getDocuments()).containsOnly(documentBack);
        assertThat(documentBack.getAddress()).isEqualTo(address);

        address.setDocuments(new HashSet<>());
        assertThat(address.getDocuments()).doesNotContain(documentBack);
        assertThat(documentBack.getAddress()).isNull();
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

    @Test
    void farmTest() {
        Address address = getAddressRandomSampleGenerator();
        Farm farmBack = getFarmRandomSampleGenerator();

        address.setFarm(farmBack);
        assertThat(address.getFarm()).isEqualTo(farmBack);

        address.farm(null);
        assertThat(address.getFarm()).isNull();
    }
}
