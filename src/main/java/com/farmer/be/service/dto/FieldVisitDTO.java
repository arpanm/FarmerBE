package com.farmer.be.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.farmer.be.domain.FieldVisit} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FieldVisitDTO implements Serializable {

    private Long id;

    private LocalDate fieldVisitDate;

    private Instant fieldVisitTime;

    private Float lat;

    private Float lon;

    private Boolean isActive;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private BuyerDTO buyer;

    private FarmDTO farm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFieldVisitDate() {
        return fieldVisitDate;
    }

    public void setFieldVisitDate(LocalDate fieldVisitDate) {
        this.fieldVisitDate = fieldVisitDate;
    }

    public Instant getFieldVisitTime() {
        return fieldVisitTime;
    }

    public void setFieldVisitTime(Instant fieldVisitTime) {
        this.fieldVisitTime = fieldVisitTime;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
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

    public BuyerDTO getBuyer() {
        return buyer;
    }

    public void setBuyer(BuyerDTO buyer) {
        this.buyer = buyer;
    }

    public FarmDTO getFarm() {
        return farm;
    }

    public void setFarm(FarmDTO farm) {
        this.farm = farm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldVisitDTO)) {
            return false;
        }

        FieldVisitDTO fieldVisitDTO = (FieldVisitDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fieldVisitDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldVisitDTO{" +
            "id=" + getId() +
            ", fieldVisitDate='" + getFieldVisitDate() + "'" +
            ", fieldVisitTime='" + getFieldVisitTime() + "'" +
            ", lat=" + getLat() +
            ", lon=" + getLon() +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", buyer=" + getBuyer() +
            ", farm=" + getFarm() +
            "}";
    }
}
