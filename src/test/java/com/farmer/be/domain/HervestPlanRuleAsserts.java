package com.farmer.be.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class HervestPlanRuleAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHervestPlanRuleAllPropertiesEquals(HervestPlanRule expected, HervestPlanRule actual) {
        assertHervestPlanRuleAutoGeneratedPropertiesEquals(expected, actual);
        assertHervestPlanRuleAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHervestPlanRuleAllUpdatablePropertiesEquals(HervestPlanRule expected, HervestPlanRule actual) {
        assertHervestPlanRuleUpdatableFieldsEquals(expected, actual);
        assertHervestPlanRuleUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHervestPlanRuleAutoGeneratedPropertiesEquals(HervestPlanRule expected, HervestPlanRule actual) {
        assertThat(actual)
            .as("Verify HervestPlanRule auto generated properties")
            .satisfies(a -> assertThat(a.getId()).as("check id").isEqualTo(expected.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertHervestPlanRuleUpdatableFieldsEquals(HervestPlanRule expected, HervestPlanRule actual) {
        assertThat(actual)
            .as("Verify HervestPlanRule relevant properties")
            .satisfies(a -> assertThat(a.getFrequencyType()).as("check frequencyType").isEqualTo(expected.getFrequencyType()))
            .satisfies(a -> assertThat(a.getHervestPlanValue()).as("check hervestPlanValue").isEqualTo(expected.getHervestPlanValue()))
            .satisfies(a ->
                assertThat(a.getHervestPlanValueMin()).as("check hervestPlanValueMin").isEqualTo(expected.getHervestPlanValueMin())
            )
            .satisfies(a ->
                assertThat(a.getHervestPlanValueMax()).as("check hervestPlanValueMax").isEqualTo(expected.getHervestPlanValueMax())
            )
            .satisfies(a -> assertThat(a.getDaysOfWeek()).as("check daysOfWeek").isEqualTo(expected.getDaysOfWeek()))
            .satisfies(a -> assertThat(a.getDaysOfMonth()).as("check daysOfMonth").isEqualTo(expected.getDaysOfMonth()))
            .satisfies(a -> assertThat(a.getDaysOfYear()).as("check daysOfYear").isEqualTo(expected.getDaysOfYear()))
            .satisfies(a -> assertThat(a.getAlternateXdays()).as("check alternateXdays").isEqualTo(expected.getAlternateXdays()))
            .satisfies(a -> assertThat(a.getStartDate()).as("check startDate").isEqualTo(expected.getStartDate()))
            .satisfies(a -> assertThat(a.getHasEndDate()).as("check hasEndDate").isEqualTo(expected.getHasEndDate()))
            .satisfies(a -> assertThat(a.getEndDate()).as("check endDate").isEqualTo(expected.getEndDate()))
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
    public static void assertHervestPlanRuleUpdatableRelationshipsEquals(HervestPlanRule expected, HervestPlanRule actual) {
        assertThat(actual)
            .as("Verify HervestPlanRule relationships")
            .satisfies(a -> assertThat(a.getFarm()).as("check farm").isEqualTo(expected.getFarm()))
            .satisfies(a -> assertThat(a.getCrop()).as("check crop").isEqualTo(expected.getCrop()));
    }
}
