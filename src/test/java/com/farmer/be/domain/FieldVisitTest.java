package com.farmer.be.domain;

import static com.farmer.be.domain.BuyerTestSamples.*;
import static com.farmer.be.domain.DocumentTestSamples.*;
import static com.farmer.be.domain.FarmTestSamples.*;
import static com.farmer.be.domain.FieldVisitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FieldVisitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldVisit.class);
        FieldVisit fieldVisit1 = getFieldVisitSample1();
        FieldVisit fieldVisit2 = new FieldVisit();
        assertThat(fieldVisit1).isNotEqualTo(fieldVisit2);

        fieldVisit2.setId(fieldVisit1.getId());
        assertThat(fieldVisit1).isEqualTo(fieldVisit2);

        fieldVisit2 = getFieldVisitSample2();
        assertThat(fieldVisit1).isNotEqualTo(fieldVisit2);
    }

    @Test
    void documentTest() {
        FieldVisit fieldVisit = getFieldVisitRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        fieldVisit.addDocument(documentBack);
        assertThat(fieldVisit.getDocuments()).containsOnly(documentBack);
        assertThat(documentBack.getFieldVisit()).isEqualTo(fieldVisit);

        fieldVisit.removeDocument(documentBack);
        assertThat(fieldVisit.getDocuments()).doesNotContain(documentBack);
        assertThat(documentBack.getFieldVisit()).isNull();

        fieldVisit.documents(new HashSet<>(Set.of(documentBack)));
        assertThat(fieldVisit.getDocuments()).containsOnly(documentBack);
        assertThat(documentBack.getFieldVisit()).isEqualTo(fieldVisit);

        fieldVisit.setDocuments(new HashSet<>());
        assertThat(fieldVisit.getDocuments()).doesNotContain(documentBack);
        assertThat(documentBack.getFieldVisit()).isNull();
    }

    @Test
    void buyerTest() {
        FieldVisit fieldVisit = getFieldVisitRandomSampleGenerator();
        Buyer buyerBack = getBuyerRandomSampleGenerator();

        fieldVisit.setBuyer(buyerBack);
        assertThat(fieldVisit.getBuyer()).isEqualTo(buyerBack);

        fieldVisit.buyer(null);
        assertThat(fieldVisit.getBuyer()).isNull();
    }

    @Test
    void farmTest() {
        FieldVisit fieldVisit = getFieldVisitRandomSampleGenerator();
        Farm farmBack = getFarmRandomSampleGenerator();

        fieldVisit.setFarm(farmBack);
        assertThat(fieldVisit.getFarm()).isEqualTo(farmBack);

        fieldVisit.farm(null);
        assertThat(fieldVisit.getFarm()).isNull();
    }
}
