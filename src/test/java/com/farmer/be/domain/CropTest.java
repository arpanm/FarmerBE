package com.farmer.be.domain;

import static com.farmer.be.domain.CategoryTestSamples.*;
import static com.farmer.be.domain.CropTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CropTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Crop.class);
        Crop crop1 = getCropSample1();
        Crop crop2 = new Crop();
        assertThat(crop1).isNotEqualTo(crop2);

        crop2.setId(crop1.getId());
        assertThat(crop1).isEqualTo(crop2);

        crop2 = getCropSample2();
        assertThat(crop1).isNotEqualTo(crop2);
    }

    @Test
    void categoryTest() {
        Crop crop = getCropRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        crop.setCategory(categoryBack);
        assertThat(crop.getCategory()).isEqualTo(categoryBack);

        crop.category(null);
        assertThat(crop.getCategory()).isNull();
    }
}
