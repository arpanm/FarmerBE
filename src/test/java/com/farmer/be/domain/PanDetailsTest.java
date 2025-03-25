package com.farmer.be.domain;

import static com.farmer.be.domain.FarmerTestSamples.*;
import static com.farmer.be.domain.PanDetailsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PanDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PanDetails.class);
        PanDetails panDetails1 = getPanDetailsSample1();
        PanDetails panDetails2 = new PanDetails();
        assertThat(panDetails1).isNotEqualTo(panDetails2);

        panDetails2.setId(panDetails1.getId());
        assertThat(panDetails1).isEqualTo(panDetails2);

        panDetails2 = getPanDetailsSample2();
        assertThat(panDetails1).isNotEqualTo(panDetails2);
    }

    @Test
    void farmerTest() {
        PanDetails panDetails = getPanDetailsRandomSampleGenerator();
        Farmer farmerBack = getFarmerRandomSampleGenerator();

        panDetails.setFarmer(farmerBack);
        assertThat(panDetails.getFarmer()).isEqualTo(farmerBack);

        panDetails.farmer(null);
        assertThat(panDetails.getFarmer()).isNull();
    }
}
