package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PickupGradationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PickupGradationDTO.class);
        PickupGradationDTO pickupGradationDTO1 = new PickupGradationDTO();
        pickupGradationDTO1.setId(1L);
        PickupGradationDTO pickupGradationDTO2 = new PickupGradationDTO();
        assertThat(pickupGradationDTO1).isNotEqualTo(pickupGradationDTO2);
        pickupGradationDTO2.setId(pickupGradationDTO1.getId());
        assertThat(pickupGradationDTO1).isEqualTo(pickupGradationDTO2);
        pickupGradationDTO2.setId(2L);
        assertThat(pickupGradationDTO1).isNotEqualTo(pickupGradationDTO2);
        pickupGradationDTO1.setId(null);
        assertThat(pickupGradationDTO1).isNotEqualTo(pickupGradationDTO2);
    }
}
