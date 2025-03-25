package com.farmer.be.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.farmer.be.domain.Demand} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DemandDTO implements Serializable {

    private Long id;

    private LocalDate demandDate;

    private Float demandValue;

    private Boolean isActive;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private CropDTO crop;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDemandDate() {
        return demandDate;
    }

    public void setDemandDate(LocalDate demandDate) {
        this.demandDate = demandDate;
    }

    public Float getDemandValue() {
        return demandValue;
    }

    public void setDemandValue(Float demandValue) {
        this.demandValue = demandValue;
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
        if (!(o instanceof DemandDTO)) {
            return false;
        }

        DemandDTO demandDTO = (DemandDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, demandDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandDTO{" +
            "id=" + getId() +
            ", demandDate='" + getDemandDate() + "'" +
            ", demandValue=" + getDemandValue() +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", crop=" + getCrop() +
            "}";
    }
}
