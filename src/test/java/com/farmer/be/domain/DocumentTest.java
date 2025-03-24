package com.farmer.be.domain;

import static com.farmer.be.domain.DocumentTestSamples.*;
import static com.farmer.be.domain.FarmerTestSamples.*;
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
}
