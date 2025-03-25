package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PanDetailsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PanDetailsDTO.class);
        PanDetailsDTO panDetailsDTO1 = new PanDetailsDTO();
        panDetailsDTO1.setId(1L);
        PanDetailsDTO panDetailsDTO2 = new PanDetailsDTO();
        assertThat(panDetailsDTO1).isNotEqualTo(panDetailsDTO2);
        panDetailsDTO2.setId(panDetailsDTO1.getId());
        assertThat(panDetailsDTO1).isEqualTo(panDetailsDTO2);
        panDetailsDTO2.setId(2L);
        assertThat(panDetailsDTO1).isNotEqualTo(panDetailsDTO2);
        panDetailsDTO1.setId(null);
        assertThat(panDetailsDTO1).isNotEqualTo(panDetailsDTO2);
    }
}
