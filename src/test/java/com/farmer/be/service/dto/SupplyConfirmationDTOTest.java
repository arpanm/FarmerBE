package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SupplyConfirmationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplyConfirmationDTO.class);
        SupplyConfirmationDTO supplyConfirmationDTO1 = new SupplyConfirmationDTO();
        supplyConfirmationDTO1.setId(1L);
        SupplyConfirmationDTO supplyConfirmationDTO2 = new SupplyConfirmationDTO();
        assertThat(supplyConfirmationDTO1).isNotEqualTo(supplyConfirmationDTO2);
        supplyConfirmationDTO2.setId(supplyConfirmationDTO1.getId());
        assertThat(supplyConfirmationDTO1).isEqualTo(supplyConfirmationDTO2);
        supplyConfirmationDTO2.setId(2L);
        assertThat(supplyConfirmationDTO1).isNotEqualTo(supplyConfirmationDTO2);
        supplyConfirmationDTO1.setId(null);
        assertThat(supplyConfirmationDTO1).isNotEqualTo(supplyConfirmationDTO2);
    }
}
