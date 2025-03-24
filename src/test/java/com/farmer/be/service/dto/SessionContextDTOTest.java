package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SessionContextDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SessionContextDTO.class);
        SessionContextDTO sessionContextDTO1 = new SessionContextDTO();
        sessionContextDTO1.setId(1L);
        SessionContextDTO sessionContextDTO2 = new SessionContextDTO();
        assertThat(sessionContextDTO1).isNotEqualTo(sessionContextDTO2);
        sessionContextDTO2.setId(sessionContextDTO1.getId());
        assertThat(sessionContextDTO1).isEqualTo(sessionContextDTO2);
        sessionContextDTO2.setId(2L);
        assertThat(sessionContextDTO1).isNotEqualTo(sessionContextDTO2);
        sessionContextDTO1.setId(null);
        assertThat(sessionContextDTO1).isNotEqualTo(sessionContextDTO2);
    }
}
