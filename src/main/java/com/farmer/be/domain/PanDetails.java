package com.farmer.be.domain;

import com.farmer.be.domain.enumeration.Gender;
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
 * A PanDetails.
 */
@Entity
@Table(name = "pan_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PanDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "pan", nullable = false)
    private String pan;

    @Column(name = "name")
    private String name;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "country")
    private String country;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "verification_time")
    private Instant verificationTime;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "panDetails")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "farmer", "farm", "address", "panDetails", "bankDetails" }, allowSetters = true)
    private Set<Document> documents = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "farms", "addresses", "panDetails", "bankDetails", "termsAndConditions", "documents", "otps" },
        allowSetters = true
    )
    private Farmer farmer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PanDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPan() {
        return this.pan;
    }

    public PanDetails pan(String pan) {
        this.setPan(pan);
        return this;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getName() {
        return this.name;
    }

    public PanDetails name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public PanDetails dateOfBirth(LocalDate dateOfBirth) {
        this.setDateOfBirth(dateOfBirth);
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return this.gender;
    }

    public PanDetails gender(Gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCountry() {
        return this.country;
    }

    public PanDetails country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getIsVerified() {
        return this.isVerified;
    }

    public PanDetails isVerified(Boolean isVerified) {
        this.setIsVerified(isVerified);
        return this;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Instant getVerificationTime() {
        return this.verificationTime;
    }

    public PanDetails verificationTime(Instant verificationTime) {
        this.setVerificationTime(verificationTime);
        return this;
    }

    public void setVerificationTime(Instant verificationTime) {
        this.verificationTime = verificationTime;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public PanDetails isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public PanDetails createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public PanDetails createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public PanDetails updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public PanDetails updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<Document> getDocuments() {
        return this.documents;
    }

    public void setDocuments(Set<Document> documents) {
        if (this.documents != null) {
            this.documents.forEach(i -> i.setPanDetails(null));
        }
        if (documents != null) {
            documents.forEach(i -> i.setPanDetails(this));
        }
        this.documents = documents;
    }

    public PanDetails documents(Set<Document> documents) {
        this.setDocuments(documents);
        return this;
    }

    public PanDetails addDocument(Document document) {
        this.documents.add(document);
        document.setPanDetails(this);
        return this;
    }

    public PanDetails removeDocument(Document document) {
        this.documents.remove(document);
        document.setPanDetails(null);
        return this;
    }

    public Farmer getFarmer() {
        return this.farmer;
    }

    public void setFarmer(Farmer farmer) {
        this.farmer = farmer;
    }

    public PanDetails farmer(Farmer farmer) {
        this.setFarmer(farmer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PanDetails)) {
            return false;
        }
        return getId() != null && getId().equals(((PanDetails) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PanDetails{" +
            "id=" + getId() +
            ", pan='" + getPan() + "'" +
            ", name='" + getName() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", gender='" + getGender() + "'" +
            ", country='" + getCountry() + "'" +
            ", isVerified='" + getIsVerified() + "'" +
            ", verificationTime='" + getVerificationTime() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
