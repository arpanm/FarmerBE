package com.farmer.be.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class TermsAndConditionAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTermsAndConditionAllPropertiesEquals(TermsAndCondition expected, TermsAndCondition actual) {
        assertTermsAndConditionAutoGeneratedPropertiesEquals(expected, actual);
        assertTermsAndConditionAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTermsAndConditionAllUpdatablePropertiesEquals(TermsAndCondition expected, TermsAndCondition actual) {
        assertTermsAndConditionUpdatableFieldsEquals(expected, actual);
        assertTermsAndConditionUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTermsAndConditionAutoGeneratedPropertiesEquals(TermsAndCondition expected, TermsAndCondition actual) {
        assertThat(actual)
            .as("Verify TermsAndCondition auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTermsAndConditionUpdatableFieldsEquals(TermsAndCondition expected, TermsAndCondition actual) {
        assertThat(actual)
            .as("Verify TermsAndCondition relevant properties")
            .satisfies(a -> assertThat(a.getTermsLink()).as("check termsLink").isEqualTo(expected.getTermsLink()))
            .satisfies(a -> assertThat(a.getAggreedOn()).as("check aggreedOn").isEqualTo(expected.getAggreedOn()))
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
    public static void assertTermsAndConditionUpdatableRelationshipsEquals(TermsAndCondition expected, TermsAndCondition actual) {
        assertThat(actual)
            .as("Verify TermsAndCondition relationships")
            .satisfies(a -> assertThat(a.getFarmer()).as("check farmer").isEqualTo(expected.getFarmer()));
    }
}
