package com.farmer.be.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.farmer.be.domain.PickUpConfirmation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PickUpConfirmationDTO implements Serializable {

    private Long id;

    private LocalDate confirmDate;

    private Float confirmValue;

    private String pickupBy;

    private String pickupTime;

    private Boolean isActive;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private PickupGradationDTO grade;

    private PickupPaymentDTO itemPayment;

    private FarmDTO farm;

    private CropDTO crop;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(LocalDate confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Float getConfirmValue() {
        return confirmValue;
    }

    public void setConfirmValue(Float confirmValue) {
        this.confirmValue = confirmValue;
    }

    public String getPickupBy() {
        return pickupBy;
    }

    public void setPickupBy(String pickupBy) {
        this.pickupBy = pickupBy;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
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

    public PickupGradationDTO getGrade() {
        return grade;
    }

    public void setGrade(PickupGradationDTO grade) {
        this.grade = grade;
    }

    public PickupPaymentDTO getItemPayment() {
        return itemPayment;
    }

    public void setItemPayment(PickupPaymentDTO itemPayment) {
        this.itemPayment = itemPayment;
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
        if (!(o instanceof PickUpConfirmationDTO)) {
            return false;
        }

        PickUpConfirmationDTO pickUpConfirmationDTO = (PickUpConfirmationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pickUpConfirmationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PickUpConfirmationDTO{" +
            "id=" + getId() +
            ", confirmDate='" + getConfirmDate() + "'" +
            ", confirmValue=" + getConfirmValue() +
            ", pickupBy='" + getPickupBy() + "'" +
            ", pickupTime='" + getPickupTime() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", grade=" + getGrade() +
            ", itemPayment=" + getItemPayment() +
            ", farm=" + getFarm() +
            ", crop=" + getCrop() +
            "}";
    }
}
