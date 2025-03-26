package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BuyerTargetAchivementDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuyerTargetAchivementDTO.class);
        BuyerTargetAchivementDTO buyerTargetAchivementDTO1 = new BuyerTargetAchivementDTO();
        buyerTargetAchivementDTO1.setId(1L);
        BuyerTargetAchivementDTO buyerTargetAchivementDTO2 = new BuyerTargetAchivementDTO();
        assertThat(buyerTargetAchivementDTO1).isNotEqualTo(buyerTargetAchivementDTO2);
        buyerTargetAchivementDTO2.setId(buyerTargetAchivementDTO1.getId());
        assertThat(buyerTargetAchivementDTO1).isEqualTo(buyerTargetAchivementDTO2);
        buyerTargetAchivementDTO2.setId(2L);
        assertThat(buyerTargetAchivementDTO1).isNotEqualTo(buyerTargetAchivementDTO2);
        buyerTargetAchivementDTO1.setId(null);
        assertThat(buyerTargetAchivementDTO1).isNotEqualTo(buyerTargetAchivementDTO2);
    }
}
