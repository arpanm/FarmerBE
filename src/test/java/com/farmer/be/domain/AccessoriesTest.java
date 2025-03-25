package com.farmer.be.domain;

import static com.farmer.be.domain.AccessoriesTestSamples.*;
import static com.farmer.be.domain.CategoryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccessoriesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Accessories.class);
        Accessories accessories1 = getAccessoriesSample1();
        Accessories accessories2 = new Accessories();
        assertThat(accessories1).isNotEqualTo(accessories2);

        accessories2.setId(accessories1.getId());
        assertThat(accessories1).isEqualTo(accessories2);

        accessories2 = getAccessoriesSample2();
        assertThat(accessories1).isNotEqualTo(accessories2);
    }

    @Test
    void categoryTest() {
        Accessories accessories = getAccessoriesRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        accessories.setCategory(categoryBack);
        assertThat(accessories.getCategory()).isEqualTo(categoryBack);

        accessories.category(null);
        assertThat(accessories.getCategory()).isNull();
    }
}
