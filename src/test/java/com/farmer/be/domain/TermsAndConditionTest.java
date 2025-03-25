package com.farmer.be.domain;

import static com.farmer.be.domain.FarmerTestSamples.*;
import static com.farmer.be.domain.TermsAndConditionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TermsAndConditionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TermsAndCondition.class);
        TermsAndCondition termsAndCondition1 = getTermsAndConditionSample1();
        TermsAndCondition termsAndCondition2 = new TermsAndCondition();
        assertThat(termsAndCondition1).isNotEqualTo(termsAndCondition2);

        termsAndCondition2.setId(termsAndCondition1.getId());
        assertThat(termsAndCondition1).isEqualTo(termsAndCondition2);

        termsAndCondition2 = getTermsAndConditionSample2();
        assertThat(termsAndCondition1).isNotEqualTo(termsAndCondition2);
    }

    @Test
    void farmerTest() {
        TermsAndCondition termsAndCondition = getTermsAndConditionRandomSampleGenerator();
        Farmer farmerBack = getFarmerRandomSampleGenerator();

        termsAndCondition.setFarmer(farmerBack);
        assertThat(termsAndCondition.getFarmer()).isEqualTo(farmerBack);

        termsAndCondition.farmer(null);
        assertThat(termsAndCondition.getFarmer()).isNull();
    }
}
