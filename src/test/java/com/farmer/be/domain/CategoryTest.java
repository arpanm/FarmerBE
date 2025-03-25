package com.farmer.be.domain;

import static com.farmer.be.domain.CategoryTestSamples.*;
import static com.farmer.be.domain.CategoryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Category.class);
        Category category1 = getCategorySample1();
        Category category2 = new Category();
        assertThat(category1).isNotEqualTo(category2);

        category2.setId(category1.getId());
        assertThat(category1).isEqualTo(category2);

        category2 = getCategorySample2();
        assertThat(category1).isNotEqualTo(category2);
    }

    @Test
    void categoryTest() {
        Category category = getCategoryRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        category.addCategory(categoryBack);
        assertThat(category.getCategories()).containsOnly(categoryBack);
        assertThat(categoryBack.getParent()).isEqualTo(category);

        category.removeCategory(categoryBack);
        assertThat(category.getCategories()).doesNotContain(categoryBack);
        assertThat(categoryBack.getParent()).isNull();

        category.categories(new HashSet<>(Set.of(categoryBack)));
        assertThat(category.getCategories()).containsOnly(categoryBack);
        assertThat(categoryBack.getParent()).isEqualTo(category);

        category.setCategories(new HashSet<>());
        assertThat(category.getCategories()).doesNotContain(categoryBack);
        assertThat(categoryBack.getParent()).isNull();
    }

    @Test
    void parentTest() {
        Category category = getCategoryRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        category.setParent(categoryBack);
        assertThat(category.getParent()).isEqualTo(categoryBack);

        category.parent(null);
        assertThat(category.getParent()).isNull();
    }
}
