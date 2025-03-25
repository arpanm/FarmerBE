package com.farmer.be.domain;

import static com.farmer.be.domain.PickUpConfirmationTestSamples.*;
import static com.farmer.be.domain.PickupPaymentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PickupPaymentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PickupPayment.class);
        PickupPayment pickupPayment1 = getPickupPaymentSample1();
        PickupPayment pickupPayment2 = new PickupPayment();
        assertThat(pickupPayment1).isNotEqualTo(pickupPayment2);

        pickupPayment2.setId(pickupPayment1.getId());
        assertThat(pickupPayment1).isEqualTo(pickupPayment2);

        pickupPayment2 = getPickupPaymentSample2();
        assertThat(pickupPayment1).isNotEqualTo(pickupPayment2);
    }

    @Test
    void pickupItemsTest() {
        PickupPayment pickupPayment = getPickupPaymentRandomSampleGenerator();
        PickUpConfirmation pickUpConfirmationBack = getPickUpConfirmationRandomSampleGenerator();

        pickupPayment.addPickupItems(pickUpConfirmationBack);
        assertThat(pickupPayment.getPickupItems()).containsOnly(pickUpConfirmationBack);
        assertThat(pickUpConfirmationBack.getItemPayment()).isEqualTo(pickupPayment);

        pickupPayment.removePickupItems(pickUpConfirmationBack);
        assertThat(pickupPayment.getPickupItems()).doesNotContain(pickUpConfirmationBack);
        assertThat(pickUpConfirmationBack.getItemPayment()).isNull();

        pickupPayment.pickupItems(new HashSet<>(Set.of(pickUpConfirmationBack)));
        assertThat(pickupPayment.getPickupItems()).containsOnly(pickUpConfirmationBack);
        assertThat(pickUpConfirmationBack.getItemPayment()).isEqualTo(pickupPayment);

        pickupPayment.setPickupItems(new HashSet<>());
        assertThat(pickupPayment.getPickupItems()).doesNotContain(pickUpConfirmationBack);
        assertThat(pickUpConfirmationBack.getItemPayment()).isNull();
    }
}
