package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BannerContentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BannerContentDTO.class);
        BannerContentDTO bannerContentDTO1 = new BannerContentDTO();
        bannerContentDTO1.setId(1L);
        BannerContentDTO bannerContentDTO2 = new BannerContentDTO();
        assertThat(bannerContentDTO1).isNotEqualTo(bannerContentDTO2);
        bannerContentDTO2.setId(bannerContentDTO1.getId());
        assertThat(bannerContentDTO1).isEqualTo(bannerContentDTO2);
        bannerContentDTO2.setId(2L);
        assertThat(bannerContentDTO1).isNotEqualTo(bannerContentDTO2);
        bannerContentDTO1.setId(null);
        assertThat(bannerContentDTO1).isNotEqualTo(bannerContentDTO2);
    }
}
