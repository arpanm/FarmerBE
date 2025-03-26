package com.farmer.be.domain;

import static com.farmer.be.domain.CollectionCenterTestSamples.*;
import static com.farmer.be.domain.CropTestSamples.*;
import static com.farmer.be.domain.DemandDataFileTestSamples.*;
import static com.farmer.be.domain.DemandDataTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandDataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandData.class);
        DemandData demandData1 = getDemandDataSample1();
        DemandData demandData2 = new DemandData();
        assertThat(demandData1).isNotEqualTo(demandData2);

        demandData2.setId(demandData1.getId());
        assertThat(demandData1).isEqualTo(demandData2);

        demandData2 = getDemandDataSample2();
        assertThat(demandData1).isNotEqualTo(demandData2);
    }

    @Test
    void cropTest() {
        DemandData demandData = getDemandDataRandomSampleGenerator();
        Crop cropBack = getCropRandomSampleGenerator();

        demandData.setCrop(cropBack);
        assertThat(demandData.getCrop()).isEqualTo(cropBack);

        demandData.crop(null);
        assertThat(demandData.getCrop()).isNull();
    }

    @Test
    void collectionCenterTest() {
        DemandData demandData = getDemandDataRandomSampleGenerator();
        CollectionCenter collectionCenterBack = getCollectionCenterRandomSampleGenerator();

        demandData.setCollectionCenter(collectionCenterBack);
        assertThat(demandData.getCollectionCenter()).isEqualTo(collectionCenterBack);

        demandData.collectionCenter(null);
        assertThat(demandData.getCollectionCenter()).isNull();
    }

    @Test
    void fileTest() {
        DemandData demandData = getDemandDataRandomSampleGenerator();
        DemandDataFile demandDataFileBack = getDemandDataFileRandomSampleGenerator();

        demandData.setFile(demandDataFileBack);
        assertThat(demandData.getFile()).isEqualTo(demandDataFileBack);

        demandData.file(null);
        assertThat(demandData.getFile()).isNull();
    }
}
