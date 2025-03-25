package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PickupPaymentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PickupPaymentDTO.class);
        PickupPaymentDTO pickupPaymentDTO1 = new PickupPaymentDTO();
        pickupPaymentDTO1.setId(1L);
        PickupPaymentDTO pickupPaymentDTO2 = new PickupPaymentDTO();
        assertThat(pickupPaymentDTO1).isNotEqualTo(pickupPaymentDTO2);
        pickupPaymentDTO2.setId(pickupPaymentDTO1.getId());
        assertThat(pickupPaymentDTO1).isEqualTo(pickupPaymentDTO2);
        pickupPaymentDTO2.setId(2L);
        assertThat(pickupPaymentDTO1).isNotEqualTo(pickupPaymentDTO2);
        pickupPaymentDTO1.setId(null);
        assertThat(pickupPaymentDTO1).isNotEqualTo(pickupPaymentDTO2);
    }
}
