package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandDataFileDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandDataFileDTO.class);
        DemandDataFileDTO demandDataFileDTO1 = new DemandDataFileDTO();
        demandDataFileDTO1.setId(1L);
        DemandDataFileDTO demandDataFileDTO2 = new DemandDataFileDTO();
        assertThat(demandDataFileDTO1).isNotEqualTo(demandDataFileDTO2);
        demandDataFileDTO2.setId(demandDataFileDTO1.getId());
        assertThat(demandDataFileDTO1).isEqualTo(demandDataFileDTO2);
        demandDataFileDTO2.setId(2L);
        assertThat(demandDataFileDTO1).isNotEqualTo(demandDataFileDTO2);
        demandDataFileDTO1.setId(null);
        assertThat(demandDataFileDTO1).isNotEqualTo(demandDataFileDTO2);
    }
}
