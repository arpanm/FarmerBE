package com.farmer.be.domain;

import static com.farmer.be.domain.PickUpConfirmationTestSamples.*;
import static com.farmer.be.domain.PickupGradationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PickupGradationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PickupGradation.class);
        PickupGradation pickupGradation1 = getPickupGradationSample1();
        PickupGradation pickupGradation2 = new PickupGradation();
        assertThat(pickupGradation1).isNotEqualTo(pickupGradation2);

        pickupGradation2.setId(pickupGradation1.getId());
        assertThat(pickupGradation1).isEqualTo(pickupGradation2);

        pickupGradation2 = getPickupGradationSample2();
        assertThat(pickupGradation1).isNotEqualTo(pickupGradation2);
    }

    @Test
    void pickupItemTest() {
        PickupGradation pickupGradation = getPickupGradationRandomSampleGenerator();
        PickUpConfirmation pickUpConfirmationBack = getPickUpConfirmationRandomSampleGenerator();

        pickupGradation.setPickupItem(pickUpConfirmationBack);
        assertThat(pickupGradation.getPickupItem()).isEqualTo(pickUpConfirmationBack);
        assertThat(pickUpConfirmationBack.getGrade()).isEqualTo(pickupGradation);

        pickupGradation.pickupItem(null);
        assertThat(pickupGradation.getPickupItem()).isNull();
        assertThat(pickUpConfirmationBack.getGrade()).isNull();
    }
}
