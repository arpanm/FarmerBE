package com.farmer.be.domain;

import static com.farmer.be.domain.AccessoriesTestSamples.*;
import static com.farmer.be.domain.AddressTestSamples.*;
import static com.farmer.be.domain.CropTestSamples.*;
import static com.farmer.be.domain.DocumentTestSamples.*;
import static com.farmer.be.domain.FarmTestSamples.*;
import static com.farmer.be.domain.FarmerTestSamples.*;
import static com.farmer.be.domain.HervestPlanTestSamples.*;
import static com.farmer.be.domain.PickUpConfirmationTestSamples.*;
import static com.farmer.be.domain.SupplyConfirmationTestSamples.*;
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
    void hervestPlanTest() {
        Farm farm = getFarmRandomSampleGenerator();
        HervestPlan hervestPlanBack = getHervestPlanRandomSampleGenerator();

        farm.addHervestPlan(hervestPlanBack);
        assertThat(farm.getHervestPlans()).containsOnly(hervestPlanBack);
        assertThat(hervestPlanBack.getFarm()).isEqualTo(farm);

        farm.removeHervestPlan(hervestPlanBack);
        assertThat(farm.getHervestPlans()).doesNotContain(hervestPlanBack);
        assertThat(hervestPlanBack.getFarm()).isNull();

        farm.hervestPlans(new HashSet<>(Set.of(hervestPlanBack)));
        assertThat(farm.getHervestPlans()).containsOnly(hervestPlanBack);
        assertThat(hervestPlanBack.getFarm()).isEqualTo(farm);

        farm.setHervestPlans(new HashSet<>());
        assertThat(farm.getHervestPlans()).doesNotContain(hervestPlanBack);
        assertThat(hervestPlanBack.getFarm()).isNull();
    }

    @Test
    void supplyConfirmationTest() {
        Farm farm = getFarmRandomSampleGenerator();
        SupplyConfirmation supplyConfirmationBack = getSupplyConfirmationRandomSampleGenerator();

        farm.addSupplyConfirmation(supplyConfirmationBack);
        assertThat(farm.getSupplyConfirmations()).containsOnly(supplyConfirmationBack);
        assertThat(supplyConfirmationBack.getFarm()).isEqualTo(farm);

        farm.removeSupplyConfirmation(supplyConfirmationBack);
        assertThat(farm.getSupplyConfirmations()).doesNotContain(supplyConfirmationBack);
        assertThat(supplyConfirmationBack.getFarm()).isNull();

        farm.supplyConfirmations(new HashSet<>(Set.of(supplyConfirmationBack)));
        assertThat(farm.getSupplyConfirmations()).containsOnly(supplyConfirmationBack);
        assertThat(supplyConfirmationBack.getFarm()).isEqualTo(farm);

        farm.setSupplyConfirmations(new HashSet<>());
        assertThat(farm.getSupplyConfirmations()).doesNotContain(supplyConfirmationBack);
        assertThat(supplyConfirmationBack.getFarm()).isNull();
    }

    @Test
    void pickUpConfirmationTest() {
        Farm farm = getFarmRandomSampleGenerator();
        PickUpConfirmation pickUpConfirmationBack = getPickUpConfirmationRandomSampleGenerator();

        farm.addPickUpConfirmation(pickUpConfirmationBack);
        assertThat(farm.getPickUpConfirmations()).containsOnly(pickUpConfirmationBack);
        assertThat(pickUpConfirmationBack.getFarm()).isEqualTo(farm);

        farm.removePickUpConfirmation(pickUpConfirmationBack);
        assertThat(farm.getPickUpConfirmations()).doesNotContain(pickUpConfirmationBack);
        assertThat(pickUpConfirmationBack.getFarm()).isNull();

        farm.pickUpConfirmations(new HashSet<>(Set.of(pickUpConfirmationBack)));
        assertThat(farm.getPickUpConfirmations()).containsOnly(pickUpConfirmationBack);
        assertThat(pickUpConfirmationBack.getFarm()).isEqualTo(farm);

        farm.setPickUpConfirmations(new HashSet<>());
        assertThat(farm.getPickUpConfirmations()).doesNotContain(pickUpConfirmationBack);
        assertThat(pickUpConfirmationBack.getFarm()).isNull();
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
    void farmerTest() {
        Farm farm = getFarmRandomSampleGenerator();
        Farmer farmerBack = getFarmerRandomSampleGenerator();

        farm.setFarmer(farmerBack);
        assertThat(farm.getFarmer()).isEqualTo(farmerBack);

        farm.farmer(null);
        assertThat(farm.getFarmer()).isNull();
    }
}
