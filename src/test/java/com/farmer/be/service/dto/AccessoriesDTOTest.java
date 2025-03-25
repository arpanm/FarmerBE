package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AccessoriesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccessoriesDTO.class);
        AccessoriesDTO accessoriesDTO1 = new AccessoriesDTO();
        accessoriesDTO1.setId(1L);
        AccessoriesDTO accessoriesDTO2 = new AccessoriesDTO();
        assertThat(accessoriesDTO1).isNotEqualTo(accessoriesDTO2);
        accessoriesDTO2.setId(accessoriesDTO1.getId());
        assertThat(accessoriesDTO1).isEqualTo(accessoriesDTO2);
        accessoriesDTO2.setId(2L);
        assertThat(accessoriesDTO1).isNotEqualTo(accessoriesDTO2);
        accessoriesDTO1.setId(null);
        assertThat(accessoriesDTO1).isNotEqualTo(accessoriesDTO2);
    }
}
