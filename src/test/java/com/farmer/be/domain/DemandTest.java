package com.farmer.be.domain;

import static com.farmer.be.domain.CropTestSamples.*;
import static com.farmer.be.domain.DemandTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Demand.class);
        Demand demand1 = getDemandSample1();
        Demand demand2 = new Demand();
        assertThat(demand1).isNotEqualTo(demand2);

        demand2.setId(demand1.getId());
        assertThat(demand1).isEqualTo(demand2);

        demand2 = getDemandSample2();
        assertThat(demand1).isNotEqualTo(demand2);
    }

    @Test
    void cropTest() {
        Demand demand = getDemandRandomSampleGenerator();
        Crop cropBack = getCropRandomSampleGenerator();

        demand.setCrop(cropBack);
        assertThat(demand.getCrop()).isEqualTo(cropBack);

        demand.crop(null);
        assertThat(demand.getCrop()).isNull();
    }
}
