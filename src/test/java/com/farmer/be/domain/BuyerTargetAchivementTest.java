package com.farmer.be.domain;

import static com.farmer.be.domain.BuyerTargetAchivementTestSamples.*;
import static com.farmer.be.domain.BuyerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BuyerTargetAchivementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuyerTargetAchivement.class);
        BuyerTargetAchivement buyerTargetAchivement1 = getBuyerTargetAchivementSample1();
        BuyerTargetAchivement buyerTargetAchivement2 = new BuyerTargetAchivement();
        assertThat(buyerTargetAchivement1).isNotEqualTo(buyerTargetAchivement2);

        buyerTargetAchivement2.setId(buyerTargetAchivement1.getId());
        assertThat(buyerTargetAchivement1).isEqualTo(buyerTargetAchivement2);

        buyerTargetAchivement2 = getBuyerTargetAchivementSample2();
        assertThat(buyerTargetAchivement1).isNotEqualTo(buyerTargetAchivement2);
    }

    @Test
    void buyerTest() {
        BuyerTargetAchivement buyerTargetAchivement = getBuyerTargetAchivementRandomSampleGenerator();
        Buyer buyerBack = getBuyerRandomSampleGenerator();

        buyerTargetAchivement.setBuyer(buyerBack);
        assertThat(buyerTargetAchivement.getBuyer()).isEqualTo(buyerBack);

        buyerTargetAchivement.buyer(null);
        assertThat(buyerTargetAchivement.getBuyer()).isNull();
    }
}
