package com.farmer.be.service.dto;

import com.farmer.be.domain.enumeration.FrequencyType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.farmer.be.domain.HervestPlanRule} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HervestPlanRuleDTO implements Serializable {

    private Long id;

    private FrequencyType frequencyType;

    private Float hervestPlanValue;

    private Float hervestPlanValueMin;

    private Float hervestPlanValueMax;

    private String daysOfWeek;

    private String daysOfMonth;

    private String daysOfYear;

    private Long alternateXdays;

    private LocalDate startDate;

    private Boolean hasEndDate;

    private LocalDate endDate;

    private Boolean isActive;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private FarmDTO farm;

    private CropDTO crop;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FrequencyType getFrequencyType() {
        return frequencyType;
    }

    public void setFrequencyType(FrequencyType frequencyType) {
        this.frequencyType = frequencyType;
    }

    public Float getHervestPlanValue() {
        return hervestPlanValue;
    }

    public void setHervestPlanValue(Float hervestPlanValue) {
        this.hervestPlanValue = hervestPlanValue;
    }

    public Float getHervestPlanValueMin() {
        return hervestPlanValueMin;
    }

    public void setHervestPlanValueMin(Float hervestPlanValueMin) {
        this.hervestPlanValueMin = hervestPlanValueMin;
    }

    public Float getHervestPlanValueMax() {
        return hervestPlanValueMax;
    }

    public void setHervestPlanValueMax(Float hervestPlanValueMax) {
        this.hervestPlanValueMax = hervestPlanValueMax;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public String getDaysOfMonth() {
        return daysOfMonth;
    }

    public void setDaysOfMonth(String daysOfMonth) {
        this.daysOfMonth = daysOfMonth;
    }

    public String getDaysOfYear() {
        return daysOfYear;
    }

    public void setDaysOfYear(String daysOfYear) {
        this.daysOfYear = daysOfYear;
    }

    public Long getAlternateXdays() {
        return alternateXdays;
    }

    public void setAlternateXdays(Long alternateXdays) {
        this.alternateXdays = alternateXdays;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Boolean getHasEndDate() {
        return hasEndDate;
    }

    public void setHasEndDate(Boolean hasEndDate) {
        this.hasEndDate = hasEndDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return createddBy;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public FarmDTO getFarm() {
        return farm;
    }

    public void setFarm(FarmDTO farm) {
        this.farm = farm;
    }

    public CropDTO getCrop() {
        return crop;
    }

    public void setCrop(CropDTO crop) {
        this.crop = crop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HervestPlanRuleDTO)) {
            return false;
        }

        HervestPlanRuleDTO hervestPlanRuleDTO = (HervestPlanRuleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, hervestPlanRuleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HervestPlanRuleDTO{" +
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
            ", farm=" + getFarm() +
            ", crop=" + getCrop() +
            "}";
    }
}
