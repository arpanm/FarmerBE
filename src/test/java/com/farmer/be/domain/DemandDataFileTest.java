package com.farmer.be.domain;

import static com.farmer.be.domain.DemandDataFileTestSamples.*;
import static com.farmer.be.domain.DemandDataTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DemandDataFileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandDataFile.class);
        DemandDataFile demandDataFile1 = getDemandDataFileSample1();
        DemandDataFile demandDataFile2 = new DemandDataFile();
        assertThat(demandDataFile1).isNotEqualTo(demandDataFile2);

        demandDataFile2.setId(demandDataFile1.getId());
        assertThat(demandDataFile1).isEqualTo(demandDataFile2);

        demandDataFile2 = getDemandDataFileSample2();
        assertThat(demandDataFile1).isNotEqualTo(demandDataFile2);
    }

    @Test
    void demandDataTest() {
        DemandDataFile demandDataFile = getDemandDataFileRandomSampleGenerator();
        DemandData demandDataBack = getDemandDataRandomSampleGenerator();

        demandDataFile.addDemandData(demandDataBack);
        assertThat(demandDataFile.getDemandData()).containsOnly(demandDataBack);
        assertThat(demandDataBack.getFile()).isEqualTo(demandDataFile);

        demandDataFile.removeDemandData(demandDataBack);
        assertThat(demandDataFile.getDemandData()).doesNotContain(demandDataBack);
        assertThat(demandDataBack.getFile()).isNull();

        demandDataFile.demandData(new HashSet<>(Set.of(demandDataBack)));
        assertThat(demandDataFile.getDemandData()).containsOnly(demandDataBack);
        assertThat(demandDataBack.getFile()).isEqualTo(demandDataFile);

        demandDataFile.setDemandData(new HashSet<>());
        assertThat(demandDataFile.getDemandData()).doesNotContain(demandDataBack);
        assertThat(demandDataBack.getFile()).isNull();
    }
}
