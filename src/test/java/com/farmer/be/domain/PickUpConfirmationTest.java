package com.farmer.be.domain;

import static com.farmer.be.domain.CropTestSamples.*;
import static com.farmer.be.domain.FarmTestSamples.*;
import static com.farmer.be.domain.PickUpConfirmationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PickUpConfirmationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PickUpConfirmation.class);
        PickUpConfirmation pickUpConfirmation1 = getPickUpConfirmationSample1();
        PickUpConfirmation pickUpConfirmation2 = new PickUpConfirmation();
        assertThat(pickUpConfirmation1).isNotEqualTo(pickUpConfirmation2);

        pickUpConfirmation2.setId(pickUpConfirmation1.getId());
        assertThat(pickUpConfirmation1).isEqualTo(pickUpConfirmation2);

        pickUpConfirmation2 = getPickUpConfirmationSample2();
        assertThat(pickUpConfirmation1).isNotEqualTo(pickUpConfirmation2);
    }

    @Test
    void farmTest() {
        PickUpConfirmation pickUpConfirmation = getPickUpConfirmationRandomSampleGenerator();
        Farm farmBack = getFarmRandomSampleGenerator();

        pickUpConfirmation.setFarm(farmBack);
        assertThat(pickUpConfirmation.getFarm()).isEqualTo(farmBack);

        pickUpConfirmation.farm(null);
        assertThat(pickUpConfirmation.getFarm()).isNull();
    }

    @Test
    void cropTest() {
        PickUpConfirmation pickUpConfirmation = getPickUpConfirmationRandomSampleGenerator();
        Crop cropBack = getCropRandomSampleGenerator();

        pickUpConfirmation.setCrop(cropBack);
        assertThat(pickUpConfirmation.getCrop()).isEqualTo(cropBack);

        pickUpConfirmation.crop(null);
        assertThat(pickUpConfirmation.getCrop()).isNull();
    }
}
