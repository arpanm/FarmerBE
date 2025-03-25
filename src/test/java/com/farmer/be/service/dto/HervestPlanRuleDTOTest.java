package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HervestPlanRuleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HervestPlanRuleDTO.class);
        HervestPlanRuleDTO hervestPlanRuleDTO1 = new HervestPlanRuleDTO();
        hervestPlanRuleDTO1.setId(1L);
        HervestPlanRuleDTO hervestPlanRuleDTO2 = new HervestPlanRuleDTO();
        assertThat(hervestPlanRuleDTO1).isNotEqualTo(hervestPlanRuleDTO2);
        hervestPlanRuleDTO2.setId(hervestPlanRuleDTO1.getId());
        assertThat(hervestPlanRuleDTO1).isEqualTo(hervestPlanRuleDTO2);
        hervestPlanRuleDTO2.setId(2L);
        assertThat(hervestPlanRuleDTO1).isNotEqualTo(hervestPlanRuleDTO2);
        hervestPlanRuleDTO1.setId(null);
        assertThat(hervestPlanRuleDTO1).isNotEqualTo(hervestPlanRuleDTO2);
    }
}
