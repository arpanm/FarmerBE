package com.farmer.be.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CarouselContentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarouselContentDTO.class);
        CarouselContentDTO carouselContentDTO1 = new CarouselContentDTO();
        carouselContentDTO1.setId(1L);
        CarouselContentDTO carouselContentDTO2 = new CarouselContentDTO();
        assertThat(carouselContentDTO1).isNotEqualTo(carouselContentDTO2);
        carouselContentDTO2.setId(carouselContentDTO1.getId());
        assertThat(carouselContentDTO1).isEqualTo(carouselContentDTO2);
        carouselContentDTO2.setId(2L);
        assertThat(carouselContentDTO1).isNotEqualTo(carouselContentDTO2);
        carouselContentDTO1.setId(null);
        assertThat(carouselContentDTO1).isNotEqualTo(carouselContentDTO2);
    }
}
