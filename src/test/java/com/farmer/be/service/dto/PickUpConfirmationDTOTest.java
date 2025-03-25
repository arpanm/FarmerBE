package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PickUpConfirmationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PickUpConfirmationDTO.class);
        PickUpConfirmationDTO pickUpConfirmationDTO1 = new PickUpConfirmationDTO();
        pickUpConfirmationDTO1.setId(1L);
        PickUpConfirmationDTO pickUpConfirmationDTO2 = new PickUpConfirmationDTO();
        assertThat(pickUpConfirmationDTO1).isNotEqualTo(pickUpConfirmationDTO2);
        pickUpConfirmationDTO2.setId(pickUpConfirmationDTO1.getId());
        assertThat(pickUpConfirmationDTO1).isEqualTo(pickUpConfirmationDTO2);
        pickUpConfirmationDTO2.setId(2L);
        assertThat(pickUpConfirmationDTO1).isNotEqualTo(pickUpConfirmationDTO2);
        pickUpConfirmationDTO1.setId(null);
        assertThat(pickUpConfirmationDTO1).isNotEqualTo(pickUpConfirmationDTO2);
    }
}
