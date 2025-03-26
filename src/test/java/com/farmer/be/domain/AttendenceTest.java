package com.farmer.be.domain;

import static com.farmer.be.domain.AttendenceTestSamples.*;
import static com.farmer.be.domain.BuyerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttendenceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attendence.class);
        Attendence attendence1 = getAttendenceSample1();
        Attendence attendence2 = new Attendence();
        assertThat(attendence1).isNotEqualTo(attendence2);

        attendence2.setId(attendence1.getId());
        assertThat(attendence1).isEqualTo(attendence2);

        attendence2 = getAttendenceSample2();
        assertThat(attendence1).isNotEqualTo(attendence2);
    }

    @Test
    void buyerTest() {
        Attendence attendence = getAttendenceRandomSampleGenerator();
        Buyer buyerBack = getBuyerRandomSampleGenerator();

        attendence.setBuyer(buyerBack);
        assertThat(attendence.getBuyer()).isEqualTo(buyerBack);

        attendence.buyer(null);
        assertThat(attendence.getBuyer()).isNull();
    }
}
