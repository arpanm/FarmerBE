package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandDataDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandDataDTO.class);
        DemandDataDTO demandDataDTO1 = new DemandDataDTO();
        demandDataDTO1.setId(1L);
        DemandDataDTO demandDataDTO2 = new DemandDataDTO();
        assertThat(demandDataDTO1).isNotEqualTo(demandDataDTO2);
        demandDataDTO2.setId(demandDataDTO1.getId());
        assertThat(demandDataDTO1).isEqualTo(demandDataDTO2);
        demandDataDTO2.setId(2L);
        assertThat(demandDataDTO1).isNotEqualTo(demandDataDTO2);
        demandDataDTO1.setId(null);
        assertThat(demandDataDTO1).isNotEqualTo(demandDataDTO2);
    }
}
