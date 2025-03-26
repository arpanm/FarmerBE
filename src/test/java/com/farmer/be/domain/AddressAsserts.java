package com.farmer.be.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AddressAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAddressAllPropertiesEquals(Address expected, Address actual) {
        assertAddressAutoGeneratedPropertiesEquals(expected, actual);
        assertAddressAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAddressAllUpdatablePropertiesEquals(Address expected, Address actual) {
        assertAddressUpdatableFieldsEquals(expected, actual);
        assertAddressUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAddressAutoGeneratedPropertiesEquals(Address expected, Address actual) {
        assertThat(actual)
            .as("Verify Address auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAddressUpdatableFieldsEquals(Address expected, Address actual) {
        assertThat(actual)
            .as("Verify Address relevant properties")
            .satisfies(a -> assertThat(a.getLine1()).as("check line1").isEqualTo(expected.getLine1()))
            .satisfies(a -> assertThat(a.getLine2()).as("check line2").isEqualTo(expected.getLine2()))
            .satisfies(a -> assertThat(a.getLandmark()).as("check landmark").isEqualTo(expected.getLandmark()))
            .satisfies(a -> assertThat(a.getCity()).as("check city").isEqualTo(expected.getCity()))
            .satisfies(a -> assertThat(a.getState()).as("check state").isEqualTo(expected.getState()))
            .satisfies(a -> assertThat(a.getCountry()).as("check country").isEqualTo(expected.getCountry()))
            .satisfies(a -> assertThat(a.getPincode()).as("check pincode").isEqualTo(expected.getPincode()))
            .satisfies(a -> assertThat(a.getLat()).as("check lat").isEqualTo(expected.getLat()))
            .satisfies(a -> assertThat(a.getLon()).as("check lon").isEqualTo(expected.getLon()))
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
    public static void assertAddressUpdatableRelationshipsEquals(Address expected, Address actual) {
        assertThat(actual)
            .as("Verify Address relationships")
            .satisfies(a -> assertThat(a.getFarmer()).as("check farmer").isEqualTo(expected.getFarmer()))
            .satisfies(a -> assertThat(a.getFarm()).as("check farm").isEqualTo(expected.getFarm()))
            .satisfies(a -> assertThat(a.getCollectionCenter()).as("check collectionCenter").isEqualTo(expected.getCollectionCenter()));
    }
}
