package com.farmer.be.domain;

import static com.farmer.be.domain.AddressTestSamples.*;
import static com.farmer.be.domain.BankDetailsTestSamples.*;
import static com.farmer.be.domain.DocumentTestSamples.*;
import static com.farmer.be.domain.FarmTestSamples.*;
import static com.farmer.be.domain.FarmerTestSamples.*;
import static com.farmer.be.domain.PanDetailsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocumentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Document.class);
        Document document1 = getDocumentSample1();
        Document document2 = new Document();
        assertThat(document1).isNotEqualTo(document2);

        document2.setId(document1.getId());
        assertThat(document1).isEqualTo(document2);

        document2 = getDocumentSample2();
        assertThat(document1).isNotEqualTo(document2);
    }

    @Test
    void farmerTest() {
        Document document = getDocumentRandomSampleGenerator();
        Farmer farmerBack = getFarmerRandomSampleGenerator();

        document.setFarmer(farmerBack);
        assertThat(document.getFarmer()).isEqualTo(farmerBack);

        document.farmer(null);
        assertThat(document.getFarmer()).isNull();
    }

    @Test
    void farmTest() {
        Document document = getDocumentRandomSampleGenerator();
        Farm farmBack = getFarmRandomSampleGenerator();

        document.setFarm(farmBack);
        assertThat(document.getFarm()).isEqualTo(farmBack);

        document.farm(null);
        assertThat(document.getFarm()).isNull();
    }

    @Test
    void addressTest() {
        Document document = getDocumentRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        document.setAddress(addressBack);
        assertThat(document.getAddress()).isEqualTo(addressBack);

        document.address(null);
        assertThat(document.getAddress()).isNull();
    }

    @Test
    void panDetailsTest() {
        Document document = getDocumentRandomSampleGenerator();
        PanDetails panDetailsBack = getPanDetailsRandomSampleGenerator();

        document.setPanDetails(panDetailsBack);
        assertThat(document.getPanDetails()).isEqualTo(panDetailsBack);

        document.panDetails(null);
        assertThat(document.getPanDetails()).isNull();
    }

    @Test
    void bankDetailsTest() {
        Document document = getDocumentRandomSampleGenerator();
        BankDetails bankDetailsBack = getBankDetailsRandomSampleGenerator();

        document.setBankDetails(bankDetailsBack);
        assertThat(document.getBankDetails()).isEqualTo(bankDetailsBack);

        document.bankDetails(null);
        assertThat(document.getBankDetails()).isNull();
    }
}
