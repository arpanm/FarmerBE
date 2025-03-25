package com.farmer.be.domain;

import static com.farmer.be.domain.CropTestSamples.*;
import static com.farmer.be.domain.FarmTestSamples.*;
import static com.farmer.be.domain.HervestPlanRuleTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HervestPlanRuleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HervestPlanRule.class);
        HervestPlanRule hervestPlanRule1 = getHervestPlanRuleSample1();
        HervestPlanRule hervestPlanRule2 = new HervestPlanRule();
        assertThat(hervestPlanRule1).isNotEqualTo(hervestPlanRule2);

        hervestPlanRule2.setId(hervestPlanRule1.getId());
        assertThat(hervestPlanRule1).isEqualTo(hervestPlanRule2);

        hervestPlanRule2 = getHervestPlanRuleSample2();
        assertThat(hervestPlanRule1).isNotEqualTo(hervestPlanRule2);
    }

    @Test
    void farmTest() {
        HervestPlanRule hervestPlanRule = getHervestPlanRuleRandomSampleGenerator();
        Farm farmBack = getFarmRandomSampleGenerator();

        hervestPlanRule.setFarm(farmBack);
        assertThat(hervestPlanRule.getFarm()).isEqualTo(farmBack);

        hervestPlanRule.farm(null);
        assertThat(hervestPlanRule.getFarm()).isNull();
    }

    @Test
    void cropTest() {
        HervestPlanRule hervestPlanRule = getHervestPlanRuleRandomSampleGenerator();
        Crop cropBack = getCropRandomSampleGenerator();

        hervestPlanRule.setCrop(cropBack);
        assertThat(hervestPlanRule.getCrop()).isEqualTo(cropBack);

        hervestPlanRule.crop(null);
        assertThat(hervestPlanRule.getCrop()).isNull();
    }
}
