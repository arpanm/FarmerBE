package com.farmer.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PickUpConfirmation.
 */
@Entity
@Table(name = "pick_up_confirmation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PickUpConfirmation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "confirm_date")
    private LocalDate confirmDate;

    @Column(name = "confirm_value")
    private Float confirmValue;

    @Column(name = "pickup_by")
    private String pickupBy;

    @Column(name = "pickup_time")
    private String pickupTime;

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

    @JsonIgnoreProperties(value = { "pickupItem" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private PickupGradation grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "addresses", "documents", "hervestPlans", "supplyConfirmations", "pickUpConfirmations", "crops", "accessories", "farmer",
        },
        allowSetters = true
    )
    private Farm farm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "demands", "prices", "hervestPlans", "supplyConfirmations", "pickUpConfirmations", "category", "farms" },
        allowSetters = true
    )
    private Crop crop;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PickUpConfirmation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getConfirmDate() {
        return this.confirmDate;
    }

    public PickUpConfirmation confirmDate(LocalDate confirmDate) {
        this.setConfirmDate(confirmDate);
        return this;
    }

    public void setConfirmDate(LocalDate confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Float getConfirmValue() {
        return this.confirmValue;
    }

    public PickUpConfirmation confirmValue(Float confirmValue) {
        this.setConfirmValue(confirmValue);
        return this;
    }

    public void setConfirmValue(Float confirmValue) {
        this.confirmValue = confirmValue;
    }

    public String getPickupBy() {
        return this.pickupBy;
    }

    public PickUpConfirmation pickupBy(String pickupBy) {
        this.setPickupBy(pickupBy);
        return this;
    }

    public void setPickupBy(String pickupBy) {
        this.pickupBy = pickupBy;
    }

    public String getPickupTime() {
        return this.pickupTime;
    }

    public PickUpConfirmation pickupTime(String pickupTime) {
        this.setPickupTime(pickupTime);
        return this;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public PickUpConfirmation isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public PickUpConfirmation createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public PickUpConfirmation createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public PickUpConfirmation updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public PickUpConfirmation updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public PickupGradation getGrade() {
        return this.grade;
    }

    public void setGrade(PickupGradation pickupGradation) {
        this.grade = pickupGradation;
    }

    public PickUpConfirmation grade(PickupGradation pickupGradation) {
        this.setGrade(pickupGradation);
        return this;
    }

    public Farm getFarm() {
        return this.farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public PickUpConfirmation farm(Farm farm) {
        this.setFarm(farm);
        return this;
    }

    public Crop getCrop() {
        return this.crop;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }

    public PickUpConfirmation crop(Crop crop) {
        this.setCrop(crop);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PickUpConfirmation)) {
            return false;
        }
        return getId() != null && getId().equals(((PickUpConfirmation) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PickUpConfirmation{" +
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
            "}";
    }
}
