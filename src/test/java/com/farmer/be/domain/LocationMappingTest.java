package com.farmer.be.domain;

import static com.farmer.be.domain.CollectionCenterTestSamples.*;
import static com.farmer.be.domain.LocationMappingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.farmer.be.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LocationMappingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationMapping.class);
        LocationMapping locationMapping1 = getLocationMappingSample1();
        LocationMapping locationMapping2 = new LocationMapping();
        assertThat(locationMapping1).isNotEqualTo(locationMapping2);

        locationMapping2.setId(locationMapping1.getId());
        assertThat(locationMapping1).isEqualTo(locationMapping2);

        locationMapping2 = getLocationMappingSample2();
        assertThat(locationMapping1).isNotEqualTo(locationMapping2);
    }

    @Test
    void collectionCenterTest() {
        LocationMapping locationMapping = getLocationMappingRandomSampleGenerator();
        CollectionCenter collectionCenterBack = getCollectionCenterRandomSampleGenerator();

        locationMapping.setCollectionCenter(collectionCenterBack);
        assertThat(locationMapping.getCollectionCenter()).isEqualTo(collectionCenterBack);

        locationMapping.collectionCenter(null);
        assertThat(locationMapping.getCollectionCenter()).isNull();
    }
}
