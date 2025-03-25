package com.farmer.be.domain;

import com.farmer.be.domain.enumeration.FarmerType;
import com.farmer.be.domain.enumeration.Language;
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
 * A Farmer.
 */
@Entity
@Table(name = "farmer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Farmer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email")
    private String email;

    @NotNull
    @Min(value = 1000000000L)
    @Max(value = 9999999999L)
    @Column(name = "phone", nullable = false)
    private Long phone;

    @Column(name = "is_whats_app_enabled")
    private Boolean isWhatsAppEnabled;

    @Enumerated(EnumType.STRING)
    @Column(name = "farmer_type")
    private FarmerType farmerType;

    @Enumerated(EnumType.STRING)
    @Column(name = "prefered_language")
    private Language preferedLanguage;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "farmer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "addresses", "documents", "hervestPlans", "supplyConfirmations", "pickUpConfirmations", "crops", "accessories", "farmer",
        },
        allowSetters = true
    )
    private Set<Farm> farms = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "farmer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "documents", "farmer", "farm" }, allowSetters = true)
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "farmer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "documents", "farmer" }, allowSetters = true)
    private Set<PanDetails> panDetails = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "farmer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "documents", "farmer" }, allowSetters = true)
    private Set<BankDetails> bankDetails = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "farmer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "farmer" }, allowSetters = true)
    private Set<TermsAndCondition> termsAndConditions = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "farmer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "farmer", "farm", "address", "panDetails", "bankDetails" }, allowSetters = true)
    private Set<Document> documents = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "farmer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "farmer" }, allowSetters = true)
    private Set<Otp> otps = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Farmer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Farmer name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public Farmer email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return this.phone;
    }

    public Farmer phone(Long phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Boolean getIsWhatsAppEnabled() {
        return this.isWhatsAppEnabled;
    }

    public Farmer isWhatsAppEnabled(Boolean isWhatsAppEnabled) {
        this.setIsWhatsAppEnabled(isWhatsAppEnabled);
        return this;
    }

    public void setIsWhatsAppEnabled(Boolean isWhatsAppEnabled) {
        this.isWhatsAppEnabled = isWhatsAppEnabled;
    }

    public FarmerType getFarmerType() {
        return this.farmerType;
    }

    public Farmer farmerType(FarmerType farmerType) {
        this.setFarmerType(farmerType);
        return this;
    }

    public void setFarmerType(FarmerType farmerType) {
        this.farmerType = farmerType;
    }

    public Language getPreferedLanguage() {
        return this.preferedLanguage;
    }

    public Farmer preferedLanguage(Language preferedLanguage) {
        this.setPreferedLanguage(preferedLanguage);
        return this;
    }

    public void setPreferedLanguage(Language preferedLanguage) {
        this.preferedLanguage = preferedLanguage;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Farmer isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public Farmer createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public Farmer createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Farmer updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public Farmer updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<Farm> getFarms() {
        return this.farms;
    }

    public void setFarms(Set<Farm> farms) {
        if (this.farms != null) {
            this.farms.forEach(i -> i.setFarmer(null));
        }
        if (farms != null) {
            farms.forEach(i -> i.setFarmer(this));
        }
        this.farms = farms;
    }

    public Farmer farms(Set<Farm> farms) {
        this.setFarms(farms);
        return this;
    }

    public Farmer addFarm(Farm farm) {
        this.farms.add(farm);
        farm.setFarmer(this);
        return this;
    }

    public Farmer removeFarm(Farm farm) {
        this.farms.remove(farm);
        farm.setFarmer(null);
        return this;
    }

    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        if (this.addresses != null) {
            this.addresses.forEach(i -> i.setFarmer(null));
        }
        if (addresses != null) {
            addresses.forEach(i -> i.setFarmer(this));
        }
        this.addresses = addresses;
    }

    public Farmer addresses(Set<Address> addresses) {
        this.setAddresses(addresses);
        return this;
    }

    public Farmer addAddress(Address address) {
        this.addresses.add(address);
        address.setFarmer(this);
        return this;
    }

    public Farmer removeAddress(Address address) {
        this.addresses.remove(address);
        address.setFarmer(null);
        return this;
    }

    public Set<PanDetails> getPanDetails() {
        return this.panDetails;
    }

    public void setPanDetails(Set<PanDetails> panDetails) {
        if (this.panDetails != null) {
            this.panDetails.forEach(i -> i.setFarmer(null));
        }
        if (panDetails != null) {
            panDetails.forEach(i -> i.setFarmer(this));
        }
        this.panDetails = panDetails;
    }

    public Farmer panDetails(Set<PanDetails> panDetails) {
        this.setPanDetails(panDetails);
        return this;
    }

    public Farmer addPanDetails(PanDetails panDetails) {
        this.panDetails.add(panDetails);
        panDetails.setFarmer(this);
        return this;
    }

    public Farmer removePanDetails(PanDetails panDetails) {
        this.panDetails.remove(panDetails);
        panDetails.setFarmer(null);
        return this;
    }

    public Set<BankDetails> getBankDetails() {
        return this.bankDetails;
    }

    public void setBankDetails(Set<BankDetails> bankDetails) {
        if (this.bankDetails != null) {
            this.bankDetails.forEach(i -> i.setFarmer(null));
        }
        if (bankDetails != null) {
            bankDetails.forEach(i -> i.setFarmer(this));
        }
        this.bankDetails = bankDetails;
    }

    public Farmer bankDetails(Set<BankDetails> bankDetails) {
        this.setBankDetails(bankDetails);
        return this;
    }

    public Farmer addBankDetails(BankDetails bankDetails) {
        this.bankDetails.add(bankDetails);
        bankDetails.setFarmer(this);
        return this;
    }

    public Farmer removeBankDetails(BankDetails bankDetails) {
        this.bankDetails.remove(bankDetails);
        bankDetails.setFarmer(null);
        return this;
    }

    public Set<TermsAndCondition> getTermsAndConditions() {
        return this.termsAndConditions;
    }

    public void setTermsAndConditions(Set<TermsAndCondition> termsAndConditions) {
        if (this.termsAndConditions != null) {
            this.termsAndConditions.forEach(i -> i.setFarmer(null));
        }
        if (termsAndConditions != null) {
            termsAndConditions.forEach(i -> i.setFarmer(this));
        }
        this.termsAndConditions = termsAndConditions;
    }

    public Farmer termsAndConditions(Set<TermsAndCondition> termsAndConditions) {
        this.setTermsAndConditions(termsAndConditions);
        return this;
    }

    public Farmer addTermsAndCondition(TermsAndCondition termsAndCondition) {
        this.termsAndConditions.add(termsAndCondition);
        termsAndCondition.setFarmer(this);
        return this;
    }

    public Farmer removeTermsAndCondition(TermsAndCondition termsAndCondition) {
        this.termsAndConditions.remove(termsAndCondition);
        termsAndCondition.setFarmer(null);
        return this;
    }

    public Set<Document> getDocuments() {
        return this.documents;
    }

    public void setDocuments(Set<Document> documents) {
        if (this.documents != null) {
            this.documents.forEach(i -> i.setFarmer(null));
        }
        if (documents != null) {
            documents.forEach(i -> i.setFarmer(this));
        }
        this.documents = documents;
    }

    public Farmer documents(Set<Document> documents) {
        this.setDocuments(documents);
        return this;
    }

    public Farmer addDocument(Document document) {
        this.documents.add(document);
        document.setFarmer(this);
        return this;
    }

    public Farmer removeDocument(Document document) {
        this.documents.remove(document);
        document.setFarmer(null);
        return this;
    }

    public Set<Otp> getOtps() {
        return this.otps;
    }

    public void setOtps(Set<Otp> otps) {
        if (this.otps != null) {
            this.otps.forEach(i -> i.setFarmer(null));
        }
        if (otps != null) {
            otps.forEach(i -> i.setFarmer(this));
        }
        this.otps = otps;
    }

    public Farmer otps(Set<Otp> otps) {
        this.setOtps(otps);
        return this;
    }

    public Farmer addOtp(Otp otp) {
        this.otps.add(otp);
        otp.setFarmer(this);
        return this;
    }

    public Farmer removeOtp(Otp otp) {
        this.otps.remove(otp);
        otp.setFarmer(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Farmer)) {
            return false;
        }
        return getId() != null && getId().equals(((Farmer) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Farmer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone=" + getPhone() +
            ", isWhatsAppEnabled='" + getIsWhatsAppEnabled() + "'" +
            ", farmerType='" + getFarmerType() + "'" +
            ", preferedLanguage='" + getPreferedLanguage() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
