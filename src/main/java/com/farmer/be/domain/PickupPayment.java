package com.farmer.be.domain;

import com.farmer.be.domain.enumeration.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PickupPayment.
 */
@Entity
@Table(name = "pickup_payment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PickupPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "details")
    private String details;

    @Column(name = "payment_updated_by")
    private String paymentUpdatedBy;

    @Column(name = "payment_updated_time")
    private Instant paymentUpdatedTime;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "itemPayment")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "grade", "itemPayment", "farm", "crop" }, allowSetters = true)
    private Set<PickUpConfirmation> pickupItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PickupPayment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PaymentStatus getPaymentStatus() {
        return this.paymentStatus;
    }

    public PickupPayment paymentStatus(PaymentStatus paymentStatus) {
        this.setPaymentStatus(paymentStatus);
        return this;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public PickupPayment transactionId(String transactionId) {
        this.setTransactionId(transactionId);
        return this;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDate getPaymentDate() {
        return this.paymentDate;
    }

    public PickupPayment paymentDate(LocalDate paymentDate) {
        this.setPaymentDate(paymentDate);
        return this;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getDetails() {
        return this.details;
    }

    public PickupPayment details(String details) {
        this.setDetails(details);
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPaymentUpdatedBy() {
        return this.paymentUpdatedBy;
    }

    public PickupPayment paymentUpdatedBy(String paymentUpdatedBy) {
        this.setPaymentUpdatedBy(paymentUpdatedBy);
        return this;
    }

    public void setPaymentUpdatedBy(String paymentUpdatedBy) {
        this.paymentUpdatedBy = paymentUpdatedBy;
    }

    public Instant getPaymentUpdatedTime() {
        return this.paymentUpdatedTime;
    }

    public PickupPayment paymentUpdatedTime(Instant paymentUpdatedTime) {
        this.setPaymentUpdatedTime(paymentUpdatedTime);
        return this;
    }

    public void setPaymentUpdatedTime(Instant paymentUpdatedTime) {
        this.paymentUpdatedTime = paymentUpdatedTime;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public PickupPayment isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public PickupPayment createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public PickupPayment createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public PickupPayment updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public PickupPayment updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<PickUpConfirmation> getPickupItems() {
        return this.pickupItems;
    }

    public void setPickupItems(Set<PickUpConfirmation> pickUpConfirmations) {
        if (this.pickupItems != null) {
            this.pickupItems.forEach(i -> i.setItemPayment(null));
        }
        if (pickUpConfirmations != null) {
            pickUpConfirmations.forEach(i -> i.setItemPayment(this));
        }
        this.pickupItems = pickUpConfirmations;
    }

    public PickupPayment pickupItems(Set<PickUpConfirmation> pickUpConfirmations) {
        this.setPickupItems(pickUpConfirmations);
        return this;
    }

    public PickupPayment addPickupItems(PickUpConfirmation pickUpConfirmation) {
        this.pickupItems.add(pickUpConfirmation);
        pickUpConfirmation.setItemPayment(this);
        return this;
    }

    public PickupPayment removePickupItems(PickUpConfirmation pickUpConfirmation) {
        this.pickupItems.remove(pickUpConfirmation);
        pickUpConfirmation.setItemPayment(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PickupPayment)) {
            return false;
        }
        return getId() != null && getId().equals(((PickupPayment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PickupPayment{" +
            "id=" + getId() +
            ", paymentStatus='" + getPaymentStatus() + "'" +
            ", transactionId='" + getTransactionId() + "'" +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", details='" + getDetails() + "'" +
            ", paymentUpdatedBy='" + getPaymentUpdatedBy() + "'" +
            ", paymentUpdatedTime='" + getPaymentUpdatedTime() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
