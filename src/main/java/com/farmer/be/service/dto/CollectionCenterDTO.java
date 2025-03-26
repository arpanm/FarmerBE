package com.farmer.be.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.farmer.be.domain.CollectionCenter} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CollectionCenterDTO implements Serializable {

    private Long id;

    private String name;

    private String ccId;

    private String ffdcCode;

    private String ffdcName;

    private Boolean isActive;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private Set<CropDTO> crops = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCcId() {
        return ccId;
    }

    public void setCcId(String ccId) {
        this.ccId = ccId;
    }

    public String getFfdcCode() {
        return ffdcCode;
    }

    public void setFfdcCode(String ffdcCode) {
        this.ffdcCode = ffdcCode;
    }

    public String getFfdcName() {
        return ffdcName;
    }

    public void setFfdcName(String ffdcName) {
        this.ffdcName = ffdcName;
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

    public Set<CropDTO> getCrops() {
        return crops;
    }

    public void setCrops(Set<CropDTO> crops) {
        this.crops = crops;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CollectionCenterDTO)) {
            return false;
        }

        CollectionCenterDTO collectionCenterDTO = (CollectionCenterDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, collectionCenterDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CollectionCenterDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", ccId='" + getCcId() + "'" +
            ", ffdcCode='" + getFfdcCode() + "'" +
            ", ffdcName='" + getFfdcName() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", crops=" + getCrops() +
            "}";
    }
}
