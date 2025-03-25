package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TermsAndConditionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TermsAndConditionDTO.class);
        TermsAndConditionDTO termsAndConditionDTO1 = new TermsAndConditionDTO();
        termsAndConditionDTO1.setId(1L);
        TermsAndConditionDTO termsAndConditionDTO2 = new TermsAndConditionDTO();
        assertThat(termsAndConditionDTO1).isNotEqualTo(termsAndConditionDTO2);
        termsAndConditionDTO2.setId(termsAndConditionDTO1.getId());
        assertThat(termsAndConditionDTO1).isEqualTo(termsAndConditionDTO2);
        termsAndConditionDTO2.setId(2L);
        assertThat(termsAndConditionDTO1).isNotEqualTo(termsAndConditionDTO2);
        termsAndConditionDTO1.setId(null);
        assertThat(termsAndConditionDTO1).isNotEqualTo(termsAndConditionDTO2);
    }
}
