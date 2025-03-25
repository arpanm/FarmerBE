package com.farmer.be.domain;

import com.farmer.be.domain.enumeration.FrequencyType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A HervestPlanRule.
 */
@Entity
@Table(name = "hervest_plan_rule")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HervestPlanRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "frequency_type")
    private FrequencyType frequencyType;

    @Column(name = "hervest_plan_value")
    private Float hervestPlanValue;

    @Column(name = "hervest_plan_value_min")
    private Float hervestPlanValueMin;

    @Column(name = "hervest_plan_value_max")
    private Float hervestPlanValueMax;

    @Column(name = "days_of_week")
    private String daysOfWeek;

    @Column(name = "days_of_month")
    private String daysOfMonth;

    @Column(name = "days_of_year")
    private String daysOfYear;

    @Column(name = "alternate_xdays")
    private Long alternateXdays;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "has_end_date")
    private Boolean hasEndDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "is_active")
    private Boolean isActive;

    @NotNull
    @Column(name = "createdd_by", nullable = false)
    private String createddBy;

    @NotNull
    @Column(name = "created_time", nullable = false)
    private Instant createdTime;

    @NotNull
    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @NotNull
    @Column(name = "updated_time", nullable = false)
    private Instant updatedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "addresses",
            "documents",
            "hervestPlanRules",
            "hervestPlans",
            "supplyConfirmations",
            "pickUpConfirmations",
            "crops",
            "accessories",
            "farmer",
        },
        allowSetters = true
    )
    private Farm farm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "demands", "prices", "hervestPlanRules", "hervestPlans", "supplyConfirmations", "pickUpConfirmations", "category", "farms",
        },
        allowSetters = true
    )
    private Crop crop;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public HervestPlanRule id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FrequencyType getFrequencyType() {
        return this.frequencyType;
    }

    public HervestPlanRule frequencyType(FrequencyType frequencyType) {
        this.setFrequencyType(frequencyType);
        return this;
    }

    public void setFrequencyType(FrequencyType frequencyType) {
        this.frequencyType = frequencyType;
    }

    public Float getHervestPlanValue() {
        return this.hervestPlanValue;
    }

    public HervestPlanRule hervestPlanValue(Float hervestPlanValue) {
        this.setHervestPlanValue(hervestPlanValue);
        return this;
    }

    public void setHervestPlanValue(Float hervestPlanValue) {
        this.hervestPlanValue = hervestPlanValue;
    }

    public Float getHervestPlanValueMin() {
        return this.hervestPlanValueMin;
    }

    public HervestPlanRule hervestPlanValueMin(Float hervestPlanValueMin) {
        this.setHervestPlanValueMin(hervestPlanValueMin);
        return this;
    }

    public void setHervestPlanValueMin(Float hervestPlanValueMin) {
        this.hervestPlanValueMin = hervestPlanValueMin;
    }

    public Float getHervestPlanValueMax() {
        return this.hervestPlanValueMax;
    }

    public HervestPlanRule hervestPlanValueMax(Float hervestPlanValueMax) {
        this.setHervestPlanValueMax(hervestPlanValueMax);
        return this;
    }

    public void setHervestPlanValueMax(Float hervestPlanValueMax) {
        this.hervestPlanValueMax = hervestPlanValueMax;
    }

    public String getDaysOfWeek() {
        return this.daysOfWeek;
    }

    public HervestPlanRule daysOfWeek(String daysOfWeek) {
        this.setDaysOfWeek(daysOfWeek);
        return this;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public String getDaysOfMonth() {
        return this.daysOfMonth;
    }

    public HervestPlanRule daysOfMonth(String daysOfMonth) {
        this.setDaysOfMonth(daysOfMonth);
        return this;
    }

    public void setDaysOfMonth(String daysOfMonth) {
        this.daysOfMonth = daysOfMonth;
    }

    public String getDaysOfYear() {
        return this.daysOfYear;
    }

    public HervestPlanRule daysOfYear(String daysOfYear) {
        this.setDaysOfYear(daysOfYear);
        return this;
    }

    public void setDaysOfYear(String daysOfYear) {
        this.daysOfYear = daysOfYear;
    }

    public Long getAlternateXdays() {
        return this.alternateXdays;
    }

    public HervestPlanRule alternateXdays(Long alternateXdays) {
        this.setAlternateXdays(alternateXdays);
        return this;
    }

    public void setAlternateXdays(Long alternateXdays) {
        this.alternateXdays = alternateXdays;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public HervestPlanRule startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Boolean getHasEndDate() {
        return this.hasEndDate;
    }

    public HervestPlanRule hasEndDate(Boolean hasEndDate) {
        this.setHasEndDate(hasEndDate);
        return this;
    }

    public void setHasEndDate(Boolean hasEndDate) {
        this.hasEndDate = hasEndDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public HervestPlanRule endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public HervestPlanRule isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public HervestPlanRule createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public HervestPlanRule createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public HervestPlanRule updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public HervestPlanRule updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Farm getFarm() {
        return this.farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public HervestPlanRule farm(Farm farm) {
        this.setFarm(farm);
        return this;
    }

    public Crop getCrop() {
        return this.crop;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }

    public HervestPlanRule crop(Crop crop) {
        this.setCrop(crop);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HervestPlanRule)) {
            return false;
        }
        return getId() != null && getId().equals(((HervestPlanRule) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HervestPlanRule{" +
            "id=" + getId() +
            ", frequencyType='" + getFrequencyType() + "'" +
            ", hervestPlanValue=" + getHervestPlanValue() +
            ", hervestPlanValueMin=" + getHervestPlanValueMin() +
            ", hervestPlanValueMax=" + getHervestPlanValueMax() +
            ", daysOfWeek='" + getDaysOfWeek() + "'" +
            ", daysOfMonth='" + getDaysOfMonth() + "'" +
            ", daysOfYear='" + getDaysOfYear() + "'" +
            ", alternateXdays=" + getAlternateXdays() +
            ", startDate='" + getStartDate() + "'" +
            ", hasEndDate='" + getHasEndDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
