package com.farmer.be.service.dto;

import com.farmer.be.domain.enumeration.AttendenceType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.farmer.be.domain.Attendence} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AttendenceDTO implements Serializable {

    private Long id;

    private AttendenceType attendenceType;

    private LocalDate attendenceDate;

    private Instant attendenceTime;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AttendenceType getAttendenceType() {
        return attendenceType;
    }

    public void setAttendenceType(AttendenceType attendenceType) {
        this.attendenceType = attendenceType;
    }

    public LocalDate getAttendenceDate() {
        return attendenceDate;
    }

    public void setAttendenceDate(LocalDate attendenceDate) {
        this.attendenceDate = attendenceDate;
    }

    public Instant getAttendenceTime() {
        return attendenceTime;
    }

    public void setAttendenceTime(Instant attendenceTime) {
        this.attendenceTime = attendenceTime;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttendenceDTO)) {
            return false;
        }

        AttendenceDTO attendenceDTO = (AttendenceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, attendenceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttendenceDTO{" +
            "id=" + getId() +
            ", attendenceType='" + getAttendenceType() + "'" +
            ", attendenceDate='" + getAttendenceDate() + "'" +
            ", attendenceTime='" + getAttendenceTime() + "'" +
            ", lat=" + getLat() +
            ", lon=" + getLon() +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", buyer=" + getBuyer() +
            "}";
    }
}
