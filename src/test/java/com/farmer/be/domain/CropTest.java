package com.farmer.be.domain;

import static com.farmer.be.domain.CategoryTestSamples.*;
import static com.farmer.be.domain.CropTestSamples.*;
import static com.farmer.be.domain.DemandTestSamples.*;
import static com.farmer.be.domain.FarmTestSamples.*;
import static com.farmer.be.domain.PriceTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CropTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Crop.class);
        Crop crop1 = getCropSample1();
        Crop crop2 = new Crop();
        assertThat(crop1).isNotEqualTo(crop2);

        crop2.setId(crop1.getId());
        assertThat(crop1).isEqualTo(crop2);

        crop2 = getCropSample2();
        assertThat(crop1).isNotEqualTo(crop2);
    }

    @Test
    void demandTest() {
        Crop crop = getCropRandomSampleGenerator();
        Demand demandBack = getDemandRandomSampleGenerator();

        crop.addDemand(demandBack);
        assertThat(crop.getDemands()).containsOnly(demandBack);
        assertThat(demandBack.getCrop()).isEqualTo(crop);

        crop.removeDemand(demandBack);
        assertThat(crop.getDemands()).doesNotContain(demandBack);
        assertThat(demandBack.getCrop()).isNull();

        crop.demands(new HashSet<>(Set.of(demandBack)));
        assertThat(crop.getDemands()).containsOnly(demandBack);
        assertThat(demandBack.getCrop()).isEqualTo(crop);

        crop.setDemands(new HashSet<>());
        assertThat(crop.getDemands()).doesNotContain(demandBack);
        assertThat(demandBack.getCrop()).isNull();
    }

    @Test
    void priceTest() {
        Crop crop = getCropRandomSampleGenerator();
        Price priceBack = getPriceRandomSampleGenerator();

        crop.addPrice(priceBack);
        assertThat(crop.getPrices()).containsOnly(priceBack);
        assertThat(priceBack.getCrop()).isEqualTo(crop);

        crop.removePrice(priceBack);
        assertThat(crop.getPrices()).doesNotContain(priceBack);
        assertThat(priceBack.getCrop()).isNull();

        crop.prices(new HashSet<>(Set.of(priceBack)));
        assertThat(crop.getPrices()).containsOnly(priceBack);
        assertThat(priceBack.getCrop()).isEqualTo(crop);

        crop.setPrices(new HashSet<>());
        assertThat(crop.getPrices()).doesNotContain(priceBack);
        assertThat(priceBack.getCrop()).isNull();
    }

    @Test
    void categoryTest() {
        Crop crop = getCropRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        crop.setCategory(categoryBack);
        assertThat(crop.getCategory()).isEqualTo(categoryBack);

        crop.category(null);
        assertThat(crop.getCategory()).isNull();
    }

    @Test
    void farmTest() {
        Crop crop = getCropRandomSampleGenerator();
        Farm farmBack = getFarmRandomSampleGenerator();

        crop.addFarm(farmBack);
        assertThat(crop.getFarms()).containsOnly(farmBack);
        assertThat(farmBack.getCrops()).containsOnly(crop);

        crop.removeFarm(farmBack);
        assertThat(crop.getFarms()).doesNotContain(farmBack);
        assertThat(farmBack.getCrops()).doesNotContain(crop);

        crop.farms(new HashSet<>(Set.of(farmBack)));
        assertThat(crop.getFarms()).containsOnly(farmBack);
        assertThat(farmBack.getCrops()).containsOnly(crop);

        crop.setFarms(new HashSet<>());
        assertThat(crop.getFarms()).doesNotContain(farmBack);
        assertThat(farmBack.getCrops()).doesNotContain(crop);
    }
}
