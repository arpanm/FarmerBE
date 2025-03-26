package com.farmer.be.domain;

import static com.farmer.be.domain.AddressTestSamples.*;
import static com.farmer.be.domain.BuyerTestSamples.*;
import static com.farmer.be.domain.CollectionCenterTestSamples.*;
import static com.farmer.be.domain.CropTestSamples.*;
import static com.farmer.be.domain.DemandDataTestSamples.*;
import static com.farmer.be.domain.FarmTestSamples.*;
import static com.farmer.be.domain.LocationMappingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CollectionCenterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollectionCenter.class);
        CollectionCenter collectionCenter1 = getCollectionCenterSample1();
        CollectionCenter collectionCenter2 = new CollectionCenter();
        assertThat(collectionCenter1).isNotEqualTo(collectionCenter2);

        collectionCenter2.setId(collectionCenter1.getId());
        assertThat(collectionCenter1).isEqualTo(collectionCenter2);

        collectionCenter2 = getCollectionCenterSample2();
        assertThat(collectionCenter1).isNotEqualTo(collectionCenter2);
    }

    @Test
    void addressTest() {
        CollectionCenter collectionCenter = getCollectionCenterRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        collectionCenter.addAddress(addressBack);
        assertThat(collectionCenter.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getCollectionCenter()).isEqualTo(collectionCenter);

        collectionCenter.removeAddress(addressBack);
        assertThat(collectionCenter.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getCollectionCenter()).isNull();

        collectionCenter.addresses(new HashSet<>(Set.of(addressBack)));
        assertThat(collectionCenter.getAddresses()).containsOnly(addressBack);
        assertThat(addressBack.getCollectionCenter()).isEqualTo(collectionCenter);

        collectionCenter.setAddresses(new HashSet<>());
        assertThat(collectionCenter.getAddresses()).doesNotContain(addressBack);
        assertThat(addressBack.getCollectionCenter()).isNull();
    }

    @Test
    void locationMappingTest() {
        CollectionCenter collectionCenter = getCollectionCenterRandomSampleGenerator();
        LocationMapping locationMappingBack = getLocationMappingRandomSampleGenerator();

        collectionCenter.addLocationMapping(locationMappingBack);
        assertThat(collectionCenter.getLocationMappings()).containsOnly(locationMappingBack);
        assertThat(locationMappingBack.getCollectionCenter()).isEqualTo(collectionCenter);

        collectionCenter.removeLocationMapping(locationMappingBack);
        assertThat(collectionCenter.getLocationMappings()).doesNotContain(locationMappingBack);
        assertThat(locationMappingBack.getCollectionCenter()).isNull();

        collectionCenter.locationMappings(new HashSet<>(Set.of(locationMappingBack)));
        assertThat(collectionCenter.getLocationMappings()).containsOnly(locationMappingBack);
        assertThat(locationMappingBack.getCollectionCenter()).isEqualTo(collectionCenter);

        collectionCenter.setLocationMappings(new HashSet<>());
        assertThat(collectionCenter.getLocationMappings()).doesNotContain(locationMappingBack);
        assertThat(locationMappingBack.getCollectionCenter()).isNull();
    }

    @Test
    void demandDataTest() {
        CollectionCenter collectionCenter = getCollectionCenterRandomSampleGenerator();
        DemandData demandDataBack = getDemandDataRandomSampleGenerator();

        collectionCenter.addDemandData(demandDataBack);
        assertThat(collectionCenter.getDemandData()).containsOnly(demandDataBack);
        assertThat(demandDataBack.getCollectionCenter()).isEqualTo(collectionCenter);

        collectionCenter.removeDemandData(demandDataBack);
        assertThat(collectionCenter.getDemandData()).doesNotContain(demandDataBack);
        assertThat(demandDataBack.getCollectionCenter()).isNull();

        collectionCenter.demandData(new HashSet<>(Set.of(demandDataBack)));
        assertThat(collectionCenter.getDemandData()).containsOnly(demandDataBack);
        assertThat(demandDataBack.getCollectionCenter()).isEqualTo(collectionCenter);

        collectionCenter.setDemandData(new HashSet<>());
        assertThat(collectionCenter.getDemandData()).doesNotContain(demandDataBack);
        assertThat(demandDataBack.getCollectionCenter()).isNull();
    }

    @Test
    void farmTest() {
        CollectionCenter collectionCenter = getCollectionCenterRandomSampleGenerator();
        Farm farmBack = getFarmRandomSampleGenerator();

        collectionCenter.addFarm(farmBack);
        assertThat(collectionCenter.getFarms()).containsOnly(farmBack);
        assertThat(farmBack.getCollectionCenter()).isEqualTo(collectionCenter);

        collectionCenter.removeFarm(farmBack);
        assertThat(collectionCenter.getFarms()).doesNotContain(farmBack);
        assertThat(farmBack.getCollectionCenter()).isNull();

        collectionCenter.farms(new HashSet<>(Set.of(farmBack)));
        assertThat(collectionCenter.getFarms()).containsOnly(farmBack);
        assertThat(farmBack.getCollectionCenter()).isEqualTo(collectionCenter);

        collectionCenter.setFarms(new HashSet<>());
        assertThat(collectionCenter.getFarms()).doesNotContain(farmBack);
        assertThat(farmBack.getCollectionCenter()).isNull();
    }

    @Test
    void buyerTest() {
        CollectionCenter collectionCenter = getCollectionCenterRandomSampleGenerator();
        Buyer buyerBack = getBuyerRandomSampleGenerator();

        collectionCenter.addBuyer(buyerBack);
        assertThat(collectionCenter.getBuyers()).containsOnly(buyerBack);
        assertThat(buyerBack.getCollectionCenter()).isEqualTo(collectionCenter);

        collectionCenter.removeBuyer(buyerBack);
        assertThat(collectionCenter.getBuyers()).doesNotContain(buyerBack);
        assertThat(buyerBack.getCollectionCenter()).isNull();

        collectionCenter.buyers(new HashSet<>(Set.of(buyerBack)));
        assertThat(collectionCenter.getBuyers()).containsOnly(buyerBack);
        assertThat(buyerBack.getCollectionCenter()).isEqualTo(collectionCenter);

        collectionCenter.setBuyers(new HashSet<>());
        assertThat(collectionCenter.getBuyers()).doesNotContain(buyerBack);
        assertThat(buyerBack.getCollectionCenter()).isNull();
    }

    @Test
    void cropTest() {
        CollectionCenter collectionCenter = getCollectionCenterRandomSampleGenerator();
        Crop cropBack = getCropRandomSampleGenerator();

        collectionCenter.addCrop(cropBack);
        assertThat(collectionCenter.getCrops()).containsOnly(cropBack);

        collectionCenter.removeCrop(cropBack);
        assertThat(collectionCenter.getCrops()).doesNotContain(cropBack);

        collectionCenter.crops(new HashSet<>(Set.of(cropBack)));
        assertThat(collectionCenter.getCrops()).containsOnly(cropBack);

        collectionCenter.setCrops(new HashSet<>());
        assertThat(collectionCenter.getCrops()).doesNotContain(cropBack);
    }
}
