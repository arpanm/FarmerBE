package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FieldVisitDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldVisitDTO.class);
        FieldVisitDTO fieldVisitDTO1 = new FieldVisitDTO();
        fieldVisitDTO1.setId(1L);
        FieldVisitDTO fieldVisitDTO2 = new FieldVisitDTO();
        assertThat(fieldVisitDTO1).isNotEqualTo(fieldVisitDTO2);
        fieldVisitDTO2.setId(fieldVisitDTO1.getId());
        assertThat(fieldVisitDTO1).isEqualTo(fieldVisitDTO2);
        fieldVisitDTO2.setId(2L);
        assertThat(fieldVisitDTO1).isNotEqualTo(fieldVisitDTO2);
        fieldVisitDTO1.setId(null);
        assertThat(fieldVisitDTO1).isNotEqualTo(fieldVisitDTO2);
    }
}
