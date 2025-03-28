package com.farmer.be.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendenceAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAttendenceAllPropertiesEquals(Attendence expected, Attendence actual) {
        assertAttendenceAutoGeneratedPropertiesEquals(expected, actual);
        assertAttendenceAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAttendenceAllUpdatablePropertiesEquals(Attendence expected, Attendence actual) {
        assertAttendenceUpdatableFieldsEquals(expected, actual);
        assertAttendenceUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAttendenceAutoGeneratedPropertiesEquals(Attendence expected, Attendence actual) {
        assertThat(actual)
            .as("Verify Attendence auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertAttendenceUpdatableFieldsEquals(Attendence expected, Attendence actual) {
        assertThat(actual)
            .as("Verify Attendence relevant properties")
            .satisfies(a -> assertThat(a.getAttendenceType()).as("check attendenceType").isEqualTo(expected.getAttendenceType()))
            .satisfies(a -> assertThat(a.getAttendenceDate()).as("check attendenceDate").isEqualTo(expected.getAttendenceDate()))
            .satisfies(a -> assertThat(a.getAttendenceTime()).as("check attendenceTime").isEqualTo(expected.getAttendenceTime()))
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
    public static void assertAttendenceUpdatableRelationshipsEquals(Attendence expected, Attendence actual) {
        assertThat(actual)
            .as("Verify Attendence relationships")
            .satisfies(a -> assertThat(a.getBuyer()).as("check buyer").isEqualTo(expected.getBuyer()));
    }
}
