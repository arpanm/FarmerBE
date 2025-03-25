package com.farmer.be.service.dto;

import com.farmer.be.domain.enumeration.PaymentStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.farmer.be.domain.PickupPayment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PickupPaymentDTO implements Serializable {

    private Long id;

    private PaymentStatus paymentStatus;

    private String transactionId;

    private LocalDate paymentDate;

    private String details;

    private String paymentUpdatedBy;

    private Instant paymentUpdatedTime;

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

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPaymentUpdatedBy() {
        return paymentUpdatedBy;
    }

    public void setPaymentUpdatedBy(String paymentUpdatedBy) {
        this.paymentUpdatedBy = paymentUpdatedBy;
    }

    public Instant getPaymentUpdatedTime() {
        return paymentUpdatedTime;
    }

    public void setPaymentUpdatedTime(Instant paymentUpdatedTime) {
        this.paymentUpdatedTime = paymentUpdatedTime;
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
        if (!(o instanceof PickupPaymentDTO)) {
            return false;
        }

        PickupPaymentDTO pickupPaymentDTO = (PickupPaymentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pickupPaymentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PickupPaymentDTO{" +
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
