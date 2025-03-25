package com.farmer.be.service.dto;

import com.farmer.be.domain.enumeration.ItemGrade;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.farmer.be.domain.PickupGradation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PickupGradationDTO implements Serializable {

    private Long id;

    private ItemGrade itemGrade;

    private String gradedBy;

    private Instant gradedTime;

    private Boolean isActive;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemGrade getItemGrade() {
        return itemGrade;
    }

    public void setItemGrade(ItemGrade itemGrade) {
        this.itemGrade = itemGrade;
    }

    public String getGradedBy() {
        return gradedBy;
    }

    public void setGradedBy(String gradedBy) {
        this.gradedBy = gradedBy;
    }

    public Instant getGradedTime() {
        return gradedTime;
    }

    public void setGradedTime(Instant gradedTime) {
        this.gradedTime = gradedTime;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PickupGradationDTO)) {
            return false;
        }

        PickupGradationDTO pickupGradationDTO = (PickupGradationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pickupGradationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PickupGradationDTO{" +
            "id=" + getId() +
            ", itemGrade='" + getItemGrade() + "'" +
            ", gradedBy='" + getGradedBy() + "'" +
            ", gradedTime='" + getGradedTime() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
