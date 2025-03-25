package com.farmer.be.domain;

import static com.farmer.be.domain.DocumentTestSamples.*;
import static com.farmer.be.domain.FarmerTestSamples.*;
import static com.farmer.be.domain.PanDetailsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PanDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PanDetails.class);
        PanDetails panDetails1 = getPanDetailsSample1();
        PanDetails panDetails2 = new PanDetails();
        assertThat(panDetails1).isNotEqualTo(panDetails2);

        panDetails2.setId(panDetails1.getId());
        assertThat(panDetails1).isEqualTo(panDetails2);

        panDetails2 = getPanDetailsSample2();
        assertThat(panDetails1).isNotEqualTo(panDetails2);
    }

    @Test
    void documentTest() {
        PanDetails panDetails = getPanDetailsRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        panDetails.addDocument(documentBack);
        assertThat(panDetails.getDocuments()).containsOnly(documentBack);
        assertThat(documentBack.getPanDetails()).isEqualTo(panDetails);

        panDetails.removeDocument(documentBack);
        assertThat(panDetails.getDocuments()).doesNotContain(documentBack);
        assertThat(documentBack.getPanDetails()).isNull();

        panDetails.documents(new HashSet<>(Set.of(documentBack)));
        assertThat(panDetails.getDocuments()).containsOnly(documentBack);
        assertThat(documentBack.getPanDetails()).isEqualTo(panDetails);

        panDetails.setDocuments(new HashSet<>());
        assertThat(panDetails.getDocuments()).doesNotContain(documentBack);
        assertThat(documentBack.getPanDetails()).isNull();
    }

    @Test
    void farmerTest() {
        PanDetails panDetails = getPanDetailsRandomSampleGenerator();
        Farmer farmerBack = getFarmerRandomSampleGenerator();

        panDetails.setFarmer(farmerBack);
        assertThat(panDetails.getFarmer()).isEqualTo(farmerBack);

        panDetails.farmer(null);
        assertThat(panDetails.getFarmer()).isNull();
    }
}
