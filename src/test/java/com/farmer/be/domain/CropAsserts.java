package com.farmer.be.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class CropAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCropAllPropertiesEquals(Crop expected, Crop actual) {
        assertCropAutoGeneratedPropertiesEquals(expected, actual);
        assertCropAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCropAllUpdatablePropertiesEquals(Crop expected, Crop actual) {
        assertCropUpdatableFieldsEquals(expected, actual);
        assertCropUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCropAutoGeneratedPropertiesEquals(Crop expected, Crop actual) {
        assertThat(actual)
            .as("Verify Crop auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCropUpdatableFieldsEquals(Crop expected, Crop actual) {
        assertThat(actual)
            .as("Verify Crop relevant properties")
            .satisfies(a -> assertThat(a.getName()).as("check name").isEqualTo(expected.getName()))
            .satisfies(a -> assertThat(a.getImagePath()).as("check imagePath").isEqualTo(expected.getImagePath()))
            .satisfies(a -> assertThat(a.getDescription()).as("check description").isEqualTo(expected.getDescription()))
            .satisfies(a -> assertThat(a.getOrderNo()).as("check orderNo").isEqualTo(expected.getOrderNo()))
            .satisfies(a -> assertThat(a.getSkuId()).as("check skuId").isEqualTo(expected.getSkuId()))
            .satisfies(a -> assertThat(a.getIsActive()).as("check isActive").isEqualTo(expected.getIsActive()))
            .satisfies(a -> assertThat(a.getCreateddBy()).as("check createddBy").isEqualTo(expected.getCreateddBy()))
            .satisfies(a -> assertThat(a.getCreatedTime()).as("check createdTime").isEqualTo(expected.getCreatedTime()))
            .satisfies(a -> assertThat(a.getUpdatedBy()).as("check updatedBy").isEqualTo(expected.getUpdatedBy()))
            .satisfies(a -> assertThat(a.getUpdatedTime()).as("check updatedTime").isEqualTo(expected.getUpdatedTime()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCropUpdatableRelationshipsEquals(Crop expected, Crop actual) {
        assertThat(actual)
            .as("Verify Crop relationships")
            .satisfies(a -> assertThat(a.getCategory()).as("check category").isEqualTo(expected.getCategory()))
            .satisfies(a -> assertThat(a.getFarms()).as("check farms").isEqualTo(expected.getFarms()))
            .satisfies(a -> assertThat(a.getCollectionCenters()).as("check collectionCenters").isEqualTo(expected.getCollectionCenters()));
    }
}
