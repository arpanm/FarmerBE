package com.farmer.be.domain;

import static com.farmer.be.domain.CropTestSamples.*;
import static com.farmer.be.domain.PriceTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PriceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Price.class);
        Price price1 = getPriceSample1();
        Price price2 = new Price();
        assertThat(price1).isNotEqualTo(price2);

        price2.setId(price1.getId());
        assertThat(price1).isEqualTo(price2);

        price2 = getPriceSample2();
        assertThat(price1).isNotEqualTo(price2);
    }

    @Test
    void cropTest() {
        Price price = getPriceRandomSampleGenerator();
        Crop cropBack = getCropRandomSampleGenerator();

        price.setCrop(cropBack);
        assertThat(price.getCrop()).isEqualTo(cropBack);

        price.crop(null);
        assertThat(price.getCrop()).isNull();
    }
}
