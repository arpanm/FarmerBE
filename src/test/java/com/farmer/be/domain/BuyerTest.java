package com.farmer.be.domain;

import static com.farmer.be.domain.AttendenceTestSamples.*;
import static com.farmer.be.domain.BuyerTargetAchivementTestSamples.*;
import static com.farmer.be.domain.BuyerTestSamples.*;
import static com.farmer.be.domain.CollectionCenterTestSamples.*;
import static com.farmer.be.domain.FarmTestSamples.*;
import static com.farmer.be.domain.FieldVisitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BuyerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Buyer.class);
        Buyer buyer1 = getBuyerSample1();
        Buyer buyer2 = new Buyer();
        assertThat(buyer1).isNotEqualTo(buyer2);

        buyer2.setId(buyer1.getId());
        assertThat(buyer1).isEqualTo(buyer2);

        buyer2 = getBuyerSample2();
        assertThat(buyer1).isNotEqualTo(buyer2);
    }

    @Test
    void farmTest() {
        Buyer buyer = getBuyerRandomSampleGenerator();
        Farm farmBack = getFarmRandomSampleGenerator();

        buyer.addFarm(farmBack);
        assertThat(buyer.getFarms()).containsOnly(farmBack);
        assertThat(farmBack.getBuyer()).isEqualTo(buyer);

        buyer.removeFarm(farmBack);
        assertThat(buyer.getFarms()).doesNotContain(farmBack);
        assertThat(farmBack.getBuyer()).isNull();

        buyer.farms(new HashSet<>(Set.of(farmBack)));
        assertThat(buyer.getFarms()).containsOnly(farmBack);
        assertThat(farmBack.getBuyer()).isEqualTo(buyer);

        buyer.setFarms(new HashSet<>());
        assertThat(buyer.getFarms()).doesNotContain(farmBack);
        assertThat(farmBack.getBuyer()).isNull();
    }

    @Test
    void attendenceTest() {
        Buyer buyer = getBuyerRandomSampleGenerator();
        Attendence attendenceBack = getAttendenceRandomSampleGenerator();

        buyer.addAttendence(attendenceBack);
        assertThat(buyer.getAttendences()).containsOnly(attendenceBack);
        assertThat(attendenceBack.getBuyer()).isEqualTo(buyer);

        buyer.removeAttendence(attendenceBack);
        assertThat(buyer.getAttendences()).doesNotContain(attendenceBack);
        assertThat(attendenceBack.getBuyer()).isNull();

        buyer.attendences(new HashSet<>(Set.of(attendenceBack)));
        assertThat(buyer.getAttendences()).containsOnly(attendenceBack);
        assertThat(attendenceBack.getBuyer()).isEqualTo(buyer);

        buyer.setAttendences(new HashSet<>());
        assertThat(buyer.getAttendences()).doesNotContain(attendenceBack);
        assertThat(attendenceBack.getBuyer()).isNull();
    }

    @Test
    void fieldVisitTest() {
        Buyer buyer = getBuyerRandomSampleGenerator();
        FieldVisit fieldVisitBack = getFieldVisitRandomSampleGenerator();

        buyer.addFieldVisit(fieldVisitBack);
        assertThat(buyer.getFieldVisits()).containsOnly(fieldVisitBack);
        assertThat(fieldVisitBack.getBuyer()).isEqualTo(buyer);

        buyer.removeFieldVisit(fieldVisitBack);
        assertThat(buyer.getFieldVisits()).doesNotContain(fieldVisitBack);
        assertThat(fieldVisitBack.getBuyer()).isNull();

        buyer.fieldVisits(new HashSet<>(Set.of(fieldVisitBack)));
        assertThat(buyer.getFieldVisits()).containsOnly(fieldVisitBack);
        assertThat(fieldVisitBack.getBuyer()).isEqualTo(buyer);

        buyer.setFieldVisits(new HashSet<>());
        assertThat(buyer.getFieldVisits()).doesNotContain(fieldVisitBack);
        assertThat(fieldVisitBack.getBuyer()).isNull();
    }

    @Test
    void buyerTargetAchivementTest() {
        Buyer buyer = getBuyerRandomSampleGenerator();
        BuyerTargetAchivement buyerTargetAchivementBack = getBuyerTargetAchivementRandomSampleGenerator();

        buyer.addBuyerTargetAchivement(buyerTargetAchivementBack);
        assertThat(buyer.getBuyerTargetAchivements()).containsOnly(buyerTargetAchivementBack);
        assertThat(buyerTargetAchivementBack.getBuyer()).isEqualTo(buyer);

        buyer.removeBuyerTargetAchivement(buyerTargetAchivementBack);
        assertThat(buyer.getBuyerTargetAchivements()).doesNotContain(buyerTargetAchivementBack);
        assertThat(buyerTargetAchivementBack.getBuyer()).isNull();

        buyer.buyerTargetAchivements(new HashSet<>(Set.of(buyerTargetAchivementBack)));
        assertThat(buyer.getBuyerTargetAchivements()).containsOnly(buyerTargetAchivementBack);
        assertThat(buyerTargetAchivementBack.getBuyer()).isEqualTo(buyer);

        buyer.setBuyerTargetAchivements(new HashSet<>());
        assertThat(buyer.getBuyerTargetAchivements()).doesNotContain(buyerTargetAchivementBack);
        assertThat(buyerTargetAchivementBack.getBuyer()).isNull();
    }

    @Test
    void collectionCenterTest() {
        Buyer buyer = getBuyerRandomSampleGenerator();
        CollectionCenter collectionCenterBack = getCollectionCenterRandomSampleGenerator();

        buyer.setCollectionCenter(collectionCenterBack);
        assertThat(buyer.getCollectionCenter()).isEqualTo(collectionCenterBack);

        buyer.collectionCenter(null);
        assertThat(buyer.getCollectionCenter()).isNull();
    }
}
