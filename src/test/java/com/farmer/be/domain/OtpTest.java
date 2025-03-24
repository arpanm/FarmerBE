package com.farmer.be.domain;

import static com.farmer.be.domain.FarmerTestSamples.*;
import static com.farmer.be.domain.OtpTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OtpTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Otp.class);
        Otp otp1 = getOtpSample1();
        Otp otp2 = new Otp();
        assertThat(otp1).isNotEqualTo(otp2);

        otp2.setId(otp1.getId());
        assertThat(otp1).isEqualTo(otp2);

        otp2 = getOtpSample2();
        assertThat(otp1).isNotEqualTo(otp2);
    }

    @Test
    void farmerTest() {
        Otp otp = getOtpRandomSampleGenerator();
        Farmer farmerBack = getFarmerRandomSampleGenerator();

        otp.setFarmer(farmerBack);
        assertThat(otp.getFarmer()).isEqualTo(farmerBack);

        otp.farmer(null);
        assertThat(otp.getFarmer()).isNull();
    }
}
