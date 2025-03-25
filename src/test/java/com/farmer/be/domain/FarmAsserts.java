package com.farmer.be.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class FarmAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFarmAllPropertiesEquals(Farm expected, Farm actual) {
        assertFarmAutoGeneratedPropertiesEquals(expected, actual);
        assertFarmAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFarmAllUpdatablePropertiesEquals(Farm expected, Farm actual) {
        assertFarmUpdatableFieldsEquals(expected, actual);
        assertFarmUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFarmAutoGeneratedPropertiesEquals(Farm expected, Farm actual) {
        assertThat(actual)
            .as("Verify Farm auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertFarmUpdatableFieldsEquals(Farm expected, Farm actual) {
        assertThat(actual)
            .as("Verify Farm relevant properties")
            .satisfies(a -> assertThat(a.getFarmType()).as("check farmType").isEqualTo(expected.getFarmType()))
            .satisfies(a -> assertThat(a.getOwnerName()).as("check ownerName").isEqualTo(expected.getOwnerName()))
            .satisfies(a ->
                assertThat(a.getRelationshipWithOwner()).as("check relationshipWithOwner").isEqualTo(expected.getRelationshipWithOwner())
            )
            .satisfies(a -> assertThat(a.getAreaInAcres()).as("check areaInAcres").isEqualTo(expected.getAreaInAcres()))
            .satisfies(a -> assertThat(a.getFarmDocumentNo()).as("check farmDocumentNo").isEqualTo(expected.getFarmDocumentNo()))
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
    public static void assertFarmUpdatableRelationshipsEquals(Farm expected, Farm actual) {
        assertThat(actual)
            .as("Verify Farm relationships")
            .satisfies(a -> assertThat(a.getCrops()).as("check crops").isEqualTo(expected.getCrops()))
            .satisfies(a -> assertThat(a.getAccessories()).as("check accessories").isEqualTo(expected.getAccessories()))
            .satisfies(a -> assertThat(a.getFarmer()).as("check farmer").isEqualTo(expected.getFarmer()));
    }
}
