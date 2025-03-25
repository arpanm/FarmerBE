package com.farmer.be.domain;

import com.farmer.be.domain.enumeration.ItemGrade;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PickupGradation.
 */
@Entity
@Table(name = "pickup_gradation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PickupGradation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_grade")
    private ItemGrade itemGrade;

    @Column(name = "graded_by")
    private String gradedBy;

    @Column(name = "graded_time")
    private Instant gradedTime;

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

    @JsonIgnoreProperties(value = { "grade", "itemPayment", "farm", "crop" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "grade")
    private PickUpConfirmation pickupItem;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PickupGradation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ItemGrade getItemGrade() {
        return this.itemGrade;
    }

    public PickupGradation itemGrade(ItemGrade itemGrade) {
        this.setItemGrade(itemGrade);
        return this;
    }

    public void setItemGrade(ItemGrade itemGrade) {
        this.itemGrade = itemGrade;
    }

    public String getGradedBy() {
        return this.gradedBy;
    }

    public PickupGradation gradedBy(String gradedBy) {
        this.setGradedBy(gradedBy);
        return this;
    }

    public void setGradedBy(String gradedBy) {
        this.gradedBy = gradedBy;
    }

    public Instant getGradedTime() {
        return this.gradedTime;
    }

    public PickupGradation gradedTime(Instant gradedTime) {
        this.setGradedTime(gradedTime);
        return this;
    }

    public void setGradedTime(Instant gradedTime) {
        this.gradedTime = gradedTime;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public PickupGradation isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public PickupGradation createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public PickupGradation createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public PickupGradation updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public PickupGradation updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public PickUpConfirmation getPickupItem() {
        return this.pickupItem;
    }

    public void setPickupItem(PickUpConfirmation pickUpConfirmation) {
        if (this.pickupItem != null) {
            this.pickupItem.setGrade(null);
        }
        if (pickUpConfirmation != null) {
            pickUpConfirmation.setGrade(this);
        }
        this.pickupItem = pickUpConfirmation;
    }

    public PickupGradation pickupItem(PickUpConfirmation pickUpConfirmation) {
        this.setPickupItem(pickUpConfirmation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PickupGradation)) {
            return false;
        }
        return getId() != null && getId().equals(((PickupGradation) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PickupGradation{" +
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
