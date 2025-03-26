package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CollectionCenterDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollectionCenterDTO.class);
        CollectionCenterDTO collectionCenterDTO1 = new CollectionCenterDTO();
        collectionCenterDTO1.setId(1L);
        CollectionCenterDTO collectionCenterDTO2 = new CollectionCenterDTO();
        assertThat(collectionCenterDTO1).isNotEqualTo(collectionCenterDTO2);
        collectionCenterDTO2.setId(collectionCenterDTO1.getId());
        assertThat(collectionCenterDTO1).isEqualTo(collectionCenterDTO2);
        collectionCenterDTO2.setId(2L);
        assertThat(collectionCenterDTO1).isNotEqualTo(collectionCenterDTO2);
        collectionCenterDTO1.setId(null);
        assertThat(collectionCenterDTO1).isNotEqualTo(collectionCenterDTO2);
    }
}
