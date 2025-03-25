package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandDTO.class);
        DemandDTO demandDTO1 = new DemandDTO();
        demandDTO1.setId(1L);
        DemandDTO demandDTO2 = new DemandDTO();
        assertThat(demandDTO1).isNotEqualTo(demandDTO2);
        demandDTO2.setId(demandDTO1.getId());
        assertThat(demandDTO1).isEqualTo(demandDTO2);
        demandDTO2.setId(2L);
        assertThat(demandDTO1).isNotEqualTo(demandDTO2);
        demandDTO1.setId(null);
        assertThat(demandDTO1).isNotEqualTo(demandDTO2);
    }
}
