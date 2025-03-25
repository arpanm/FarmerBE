package com.farmer.be.domain;

import static com.farmer.be.domain.BankDetailsTestSamples.*;
import static com.farmer.be.domain.DocumentTestSamples.*;
import static com.farmer.be.domain.FarmerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BankDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BankDetails.class);
        BankDetails bankDetails1 = getBankDetailsSample1();
        BankDetails bankDetails2 = new BankDetails();
        assertThat(bankDetails1).isNotEqualTo(bankDetails2);

        bankDetails2.setId(bankDetails1.getId());
        assertThat(bankDetails1).isEqualTo(bankDetails2);

        bankDetails2 = getBankDetailsSample2();
        assertThat(bankDetails1).isNotEqualTo(bankDetails2);
    }

    @Test
    void documentTest() {
        BankDetails bankDetails = getBankDetailsRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        bankDetails.addDocument(documentBack);
        assertThat(bankDetails.getDocuments()).containsOnly(documentBack);
        assertThat(documentBack.getBankDetails()).isEqualTo(bankDetails);

        bankDetails.removeDocument(documentBack);
        assertThat(bankDetails.getDocuments()).doesNotContain(documentBack);
        assertThat(documentBack.getBankDetails()).isNull();

        bankDetails.documents(new HashSet<>(Set.of(documentBack)));
        assertThat(bankDetails.getDocuments()).containsOnly(documentBack);
        assertThat(documentBack.getBankDetails()).isEqualTo(bankDetails);

        bankDetails.setDocuments(new HashSet<>());
        assertThat(bankDetails.getDocuments()).doesNotContain(documentBack);
        assertThat(documentBack.getBankDetails()).isNull();
    }

    @Test
    void farmerTest() {
        BankDetails bankDetails = getBankDetailsRandomSampleGenerator();
        Farmer farmerBack = getFarmerRandomSampleGenerator();

        bankDetails.setFarmer(farmerBack);
        assertThat(bankDetails.getFarmer()).isEqualTo(farmerBack);

        bankDetails.farmer(null);
        assertThat(bankDetails.getFarmer()).isNull();
    }
}
