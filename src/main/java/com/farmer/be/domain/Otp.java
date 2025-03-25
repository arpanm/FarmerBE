package com.farmer.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Otp.
 */
@Entity
@Table(name = "otp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Otp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email")
    private String email;

    @Column(name = "email_otp")
    private Integer emailOtp;

    @Column(name = "phone")
    private Long phone;

    @Column(name = "phone_otp")
    private Integer phoneOtp;

    @Column(name = "is_validated")
    private Boolean isValidated;

    @Column(name = "expiry_time")
    private Instant expiryTime;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "farms", "addresses", "panDetails", "termsAndConditions", "documents", "otps" }, allowSetters = true)
    private Farmer farmer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Otp id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public Otp email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEmailOtp() {
        return this.emailOtp;
    }

    public Otp emailOtp(Integer emailOtp) {
        this.setEmailOtp(emailOtp);
        return this;
    }

    public void setEmailOtp(Integer emailOtp) {
        this.emailOtp = emailOtp;
    }

    public Long getPhone() {
        return this.phone;
    }

    public Otp phone(Long phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Integer getPhoneOtp() {
        return this.phoneOtp;
    }

    public Otp phoneOtp(Integer phoneOtp) {
        this.setPhoneOtp(phoneOtp);
        return this;
    }

    public void setPhoneOtp(Integer phoneOtp) {
        this.phoneOtp = phoneOtp;
    }

    public Boolean getIsValidated() {
        return this.isValidated;
    }

    public Otp isValidated(Boolean isValidated) {
        this.setIsValidated(isValidated);
        return this;
    }

    public void setIsValidated(Boolean isValidated) {
        this.isValidated = isValidated;
    }

    public Instant getExpiryTime() {
        return this.expiryTime;
    }

    public Otp expiryTime(Instant expiryTime) {
        this.setExpiryTime(expiryTime);
        return this;
    }

    public void setExpiryTime(Instant expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Otp isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public Otp createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public Otp createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Otp updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public Otp updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Farmer getFarmer() {
        return this.farmer;
    }

    public void setFarmer(Farmer farmer) {
        this.farmer = farmer;
    }

    public Otp farmer(Farmer farmer) {
        this.setFarmer(farmer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Otp)) {
            return false;
        }
        return getId() != null && getId().equals(((Otp) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Otp{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", emailOtp=" + getEmailOtp() +
            ", phone=" + getPhone() +
            ", phoneOtp=" + getPhoneOtp() +
            ", isValidated='" + getIsValidated() + "'" +
            ", expiryTime='" + getExpiryTime() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
