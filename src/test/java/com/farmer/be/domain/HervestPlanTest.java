package com.farmer.be.domain;

import static com.farmer.be.domain.CropTestSamples.*;
import static com.farmer.be.domain.FarmTestSamples.*;
import static com.farmer.be.domain.HervestPlanTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HervestPlanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HervestPlan.class);
        HervestPlan hervestPlan1 = getHervestPlanSample1();
        HervestPlan hervestPlan2 = new HervestPlan();
        assertThat(hervestPlan1).isNotEqualTo(hervestPlan2);

        hervestPlan2.setId(hervestPlan1.getId());
        assertThat(hervestPlan1).isEqualTo(hervestPlan2);

        hervestPlan2 = getHervestPlanSample2();
        assertThat(hervestPlan1).isNotEqualTo(hervestPlan2);
    }

    @Test
    void farmTest() {
        HervestPlan hervestPlan = getHervestPlanRandomSampleGenerator();
        Farm farmBack = getFarmRandomSampleGenerator();

        hervestPlan.setFarm(farmBack);
        assertThat(hervestPlan.getFarm()).isEqualTo(farmBack);

        hervestPlan.farm(null);
        assertThat(hervestPlan.getFarm()).isNull();
    }

    @Test
    void cropTest() {
        HervestPlan hervestPlan = getHervestPlanRandomSampleGenerator();
        Crop cropBack = getCropRandomSampleGenerator();

        hervestPlan.setCrop(cropBack);
        assertThat(hervestPlan.getCrop()).isEqualTo(cropBack);

        hervestPlan.crop(null);
        assertThat(hervestPlan.getCrop()).isNull();
    }
}
