package com.farmer.be.domain;

import static com.farmer.be.domain.CropTestSamples.*;
import static com.farmer.be.domain.FarmTestSamples.*;
import static com.farmer.be.domain.SupplyConfirmationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SupplyConfirmationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyConfirmation.class);
        SupplyConfirmation supplyConfirmation1 = getSupplyConfirmationSample1();
        SupplyConfirmation supplyConfirmation2 = new SupplyConfirmation();
        assertThat(supplyConfirmation1).isNotEqualTo(supplyConfirmation2);

        supplyConfirmation2.setId(supplyConfirmation1.getId());
        assertThat(supplyConfirmation1).isEqualTo(supplyConfirmation2);

        supplyConfirmation2 = getSupplyConfirmationSample2();
        assertThat(supplyConfirmation1).isNotEqualTo(supplyConfirmation2);
    }

    @Test
    void farmTest() {
        SupplyConfirmation supplyConfirmation = getSupplyConfirmationRandomSampleGenerator();
        Farm farmBack = getFarmRandomSampleGenerator();

        supplyConfirmation.setFarm(farmBack);
        assertThat(supplyConfirmation.getFarm()).isEqualTo(farmBack);

        supplyConfirmation.farm(null);
        assertThat(supplyConfirmation.getFarm()).isNull();
    }

    @Test
    void cropTest() {
        SupplyConfirmation supplyConfirmation = getSupplyConfirmationRandomSampleGenerator();
        Crop cropBack = getCropRandomSampleGenerator();

        supplyConfirmation.setCrop(cropBack);
        assertThat(supplyConfirmation.getCrop()).isEqualTo(cropBack);

        supplyConfirmation.crop(null);
        assertThat(supplyConfirmation.getCrop()).isNull();
    }
}
