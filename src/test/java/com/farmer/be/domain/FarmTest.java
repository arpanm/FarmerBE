package com.farmer.be.domain;

import static com.farmer.be.domain.AccessoriesTestSamples.*;
import static com.farmer.be.domain.AddressTestSamples.*;
import static com.farmer.be.domain.CropTestSamples.*;
import static com.farmer.be.domain.DocumentTestSamples.*;
import static com.farmer.be.domain.FarmTestSamples.*;
import static com.farmer.be.domain.FarmerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FarmTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Farm.class);
        Farm farm1 = getFarmSample1();
        Farm farm2 = new Farm();
        assertThat(farm1).isNotEqualTo(farm2);

        farm2.setId(farm1.getId());
        assertThat(farm1).isEqualTo(farm2);

        farm2 = getFarmSample2();
        assertThat(farm1).isNotEqualTo(farm2);
    }

    @Test
    void addressTest() {
        Farm farm = getFarmRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        farm.addAddress(addressBack);
        assertThat(farm.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getFarm()).isEqualTo(farm);

        farm.removeAddress(addressBack);
        assertThat(farm.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getFarm()).isNull();

        farm.addresses(new HashSet<>(Set.of(addressBack)));
        assertThat(farm.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getFarm()).isEqualTo(farm);

        farm.setAddresses(new HashSet<>());
        assertThat(farm.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getFarm()).isNull();
    }

    @Test
    void documentTest() {
        Farm farm = getFarmRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        farm.addDocument(documentBack);
        assertThat(farm.getDocuments()).containsOnly(documentBack);
        assertThat(documentBack.getFarm()).isEqualTo(farm);

        farm.removeDocument(documentBack);
        assertThat(farm.getDocuments()).doesNotContain(documentBack);
        assertThat(documentBack.getFarm()).isNull();

        farm.documents(new HashSet<>(Set.of(documentBack)));
        assertThat(farm.getDocuments()).containsOnly(documentBack);
        assertThat(documentBack.getFarm()).isEqualTo(farm);

        farm.setDocuments(new HashSet<>());
        assertThat(farm.getDocuments()).doesNotContain(documentBack);
        assertThat(documentBack.getFarm()).isNull();
    }

    @Test
    void accessoriesTest() {
        Farm farm = getFarmRandomSampleGenerator();
        Accessories accessoriesBack = getAccessoriesRandomSampleGenerator();

        farm.addAccessories(accessoriesBack);
        assertThat(farm.getAccessories()).containsOnly(accessoriesBack);

        farm.removeAccessories(accessoriesBack);
        assertThat(farm.getAccessories()).doesNotContain(accessoriesBack);

        farm.accessories(new HashSet<>(Set.of(accessoriesBack)));
        assertThat(farm.getAccessories()).containsOnly(accessoriesBack);

        farm.setAccessories(new HashSet<>());
        assertThat(farm.getAccessories()).doesNotContain(accessoriesBack);
    }

    @Test
    void cropTest() {
        Farm farm = getFarmRandomSampleGenerator();
        Crop cropBack = getCropRandomSampleGenerator();

        farm.addCrop(cropBack);
        assertThat(farm.getCrops()).containsOnly(cropBack);

        farm.removeCrop(cropBack);
        assertThat(farm.getCrops()).doesNotContain(cropBack);

        farm.crops(new HashSet<>(Set.of(cropBack)));
        assertThat(farm.getCrops()).containsOnly(cropBack);

        farm.setCrops(new HashSet<>());
        assertThat(farm.getCrops()).doesNotContain(cropBack);
    }

    @Test
    void farmerTest() {
        Farm farm = getFarmRandomSampleGenerator();
        Farmer farmerBack = getFarmerRandomSampleGenerator();

        farm.setFarmer(farmerBack);
        assertThat(farm.getFarmer()).isEqualTo(farmerBack);

        farm.farmer(null);
        assertThat(farm.getFarmer()).isNull();
    }
}
