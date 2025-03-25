package com.farmer.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BankDetails.
 */
@Entity
@Table(name = "bank_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BankDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "ifs_c")
    private String ifsC;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "branch_name")
    private String branchName;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bankDetails")
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

    public BankDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public BankDetails name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public BankDetails accountNumber(String accountNumber) {
        this.setAccountNumber(accountNumber);
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfsC() {
        return this.ifsC;
    }

    public BankDetails ifsC(String ifsC) {
        this.setIfsC(ifsC);
        return this;
    }

    public void setIfsC(String ifsC) {
        this.ifsC = ifsC;
    }

    public String getBankName() {
        return this.bankName;
    }

    public BankDetails bankName(String bankName) {
        this.setBankName(bankName);
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return this.branchName;
    }

    public BankDetails branchName(String branchName) {
        this.setBranchName(branchName);
        return this;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Boolean getIsVerified() {
        return this.isVerified;
    }

    public BankDetails isVerified(Boolean isVerified) {
        this.setIsVerified(isVerified);
        return this;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Instant getVerificationTime() {
        return this.verificationTime;
    }

    public BankDetails verificationTime(Instant verificationTime) {
        this.setVerificationTime(verificationTime);
        return this;
    }

    public void setVerificationTime(Instant verificationTime) {
        this.verificationTime = verificationTime;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public BankDetails isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public BankDetails createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public BankDetails createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public BankDetails updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public BankDetails updatedTime(Instant updatedTime) {
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
            this.documents.forEach(i -> i.setBankDetails(null));
        }
        if (documents != null) {
            documents.forEach(i -> i.setBankDetails(this));
        }
        this.documents = documents;
    }

    public BankDetails documents(Set<Document> documents) {
        this.setDocuments(documents);
        return this;
    }

    public BankDetails addDocument(Document document) {
        this.documents.add(document);
        document.setBankDetails(this);
        return this;
    }

    public BankDetails removeDocument(Document document) {
        this.documents.remove(document);
        document.setBankDetails(null);
        return this;
    }

    public Farmer getFarmer() {
        return this.farmer;
    }

    public void setFarmer(Farmer farmer) {
        this.farmer = farmer;
    }

    public BankDetails farmer(Farmer farmer) {
        this.setFarmer(farmer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BankDetails)) {
            return false;
        }
        return getId() != null && getId().equals(((BankDetails) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankDetails{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", ifsC='" + getIfsC() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", branchName='" + getBranchName() + "'" +
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
