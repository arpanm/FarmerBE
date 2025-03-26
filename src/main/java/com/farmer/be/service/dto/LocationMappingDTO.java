package com.farmer.be.service.dto;

import com.farmer.be.domain.enumeration.AreaType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.farmer.be.domain.LocationMapping} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LocationMappingDTO implements Serializable {

    private Long id;

    private String areaName;

    private AreaType areaType;

    private Long pincode;

    private Boolean isActive;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private CollectionCenterDTO collectionCenter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public AreaType getAreaType() {
        return areaType;
    }

    public void setAreaType(AreaType areaType) {
        this.areaType = areaType;
    }

    public Long getPincode() {
        return pincode;
    }

    public void setPincode(Long pincode) {
        this.pincode = pincode;
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

    public CollectionCenterDTO getCollectionCenter() {
        return collectionCenter;
    }

    public void setCollectionCenter(CollectionCenterDTO collectionCenter) {
        this.collectionCenter = collectionCenter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocationMappingDTO)) {
            return false;
        }

        LocationMappingDTO locationMappingDTO = (LocationMappingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, locationMappingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationMappingDTO{" +
            "id=" + getId() +
            ", areaName='" + getAreaName() + "'" +
            ", areaType='" + getAreaType() + "'" +
            ", pincode=" + getPincode() +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", collectionCenter=" + getCollectionCenter() +
            "}";
    }
}
