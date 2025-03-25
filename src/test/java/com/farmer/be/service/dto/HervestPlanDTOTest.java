package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HervestPlanDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HervestPlanDTO.class);
        HervestPlanDTO hervestPlanDTO1 = new HervestPlanDTO();
        hervestPlanDTO1.setId(1L);
        HervestPlanDTO hervestPlanDTO2 = new HervestPlanDTO();
        assertThat(hervestPlanDTO1).isNotEqualTo(hervestPlanDTO2);
        hervestPlanDTO2.setId(hervestPlanDTO1.getId());
        assertThat(hervestPlanDTO1).isEqualTo(hervestPlanDTO2);
        hervestPlanDTO2.setId(2L);
        assertThat(hervestPlanDTO1).isNotEqualTo(hervestPlanDTO2);
        hervestPlanDTO1.setId(null);
        assertThat(hervestPlanDTO1).isNotEqualTo(hervestPlanDTO2);
    }
}
