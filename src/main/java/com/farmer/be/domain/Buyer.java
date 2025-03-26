package com.farmer.be.domain;

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
 * A Buyer.
 */
@Entity
@Table(name = "buyer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Buyer implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "buyer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "addresses",
            "documents",
            "hervestPlanRules",
            "hervestPlans",
            "supplyConfirmations",
            "pickUpConfirmations",
            "fieldVisits",
            "crops",
            "accessories",
            "farmer",
            "collectionCenter",
            "buyer",
        },
        allowSetters = true
    )
    private Set<Farm> farms = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "buyer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "buyer" }, allowSetters = true)
    private Set<Attendence> attendences = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "buyer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "documents", "buyer", "farm" }, allowSetters = true)
    private Set<FieldVisit> fieldVisits = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "buyer")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "buyer" }, allowSetters = true)
    private Set<BuyerTargetAchivement> buyerTargetAchivements = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "addresses", "locationMappings", "demandData", "farms", "buyers", "crops" }, allowSetters = true)
    private CollectionCenter collectionCenter;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Buyer id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Buyer name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public Buyer email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPhone() {
        return this.phone;
    }

    public Buyer phone(Long phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Boolean getIsWhatsAppEnabled() {
        return this.isWhatsAppEnabled;
    }

    public Buyer isWhatsAppEnabled(Boolean isWhatsAppEnabled) {
        this.setIsWhatsAppEnabled(isWhatsAppEnabled);
        return this;
    }

    public void setIsWhatsAppEnabled(Boolean isWhatsAppEnabled) {
        this.isWhatsAppEnabled = isWhatsAppEnabled;
    }

    public Language getPreferedLanguage() {
        return this.preferedLanguage;
    }

    public Buyer preferedLanguage(Language preferedLanguage) {
        this.setPreferedLanguage(preferedLanguage);
        return this;
    }

    public void setPreferedLanguage(Language preferedLanguage) {
        this.preferedLanguage = preferedLanguage;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Buyer isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public Buyer createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public Buyer createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Buyer updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public Buyer updatedTime(Instant updatedTime) {
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
            this.farms.forEach(i -> i.setBuyer(null));
        }
        if (farms != null) {
            farms.forEach(i -> i.setBuyer(this));
        }
        this.farms = farms;
    }

    public Buyer farms(Set<Farm> farms) {
        this.setFarms(farms);
        return this;
    }

    public Buyer addFarm(Farm farm) {
        this.farms.add(farm);
        farm.setBuyer(this);
        return this;
    }

    public Buyer removeFarm(Farm farm) {
        this.farms.remove(farm);
        farm.setBuyer(null);
        return this;
    }

    public Set<Attendence> getAttendences() {
        return this.attendences;
    }

    public void setAttendences(Set<Attendence> attendences) {
        if (this.attendences != null) {
            this.attendences.forEach(i -> i.setBuyer(null));
        }
        if (attendences != null) {
            attendences.forEach(i -> i.setBuyer(this));
        }
        this.attendences = attendences;
    }

    public Buyer attendences(Set<Attendence> attendences) {
        this.setAttendences(attendences);
        return this;
    }

    public Buyer addAttendence(Attendence attendence) {
        this.attendences.add(attendence);
        attendence.setBuyer(this);
        return this;
    }

    public Buyer removeAttendence(Attendence attendence) {
        this.attendences.remove(attendence);
        attendence.setBuyer(null);
        return this;
    }

    public Set<FieldVisit> getFieldVisits() {
        return this.fieldVisits;
    }

    public void setFieldVisits(Set<FieldVisit> fieldVisits) {
        if (this.fieldVisits != null) {
            this.fieldVisits.forEach(i -> i.setBuyer(null));
        }
        if (fieldVisits != null) {
            fieldVisits.forEach(i -> i.setBuyer(this));
        }
        this.fieldVisits = fieldVisits;
    }

    public Buyer fieldVisits(Set<FieldVisit> fieldVisits) {
        this.setFieldVisits(fieldVisits);
        return this;
    }

    public Buyer addFieldVisit(FieldVisit fieldVisit) {
        this.fieldVisits.add(fieldVisit);
        fieldVisit.setBuyer(this);
        return this;
    }

    public Buyer removeFieldVisit(FieldVisit fieldVisit) {
        this.fieldVisits.remove(fieldVisit);
        fieldVisit.setBuyer(null);
        return this;
    }

    public Set<BuyerTargetAchivement> getBuyerTargetAchivements() {
        return this.buyerTargetAchivements;
    }

    public void setBuyerTargetAchivements(Set<BuyerTargetAchivement> buyerTargetAchivements) {
        if (this.buyerTargetAchivements != null) {
            this.buyerTargetAchivements.forEach(i -> i.setBuyer(null));
        }
        if (buyerTargetAchivements != null) {
            buyerTargetAchivements.forEach(i -> i.setBuyer(this));
        }
        this.buyerTargetAchivements = buyerTargetAchivements;
    }

    public Buyer buyerTargetAchivements(Set<BuyerTargetAchivement> buyerTargetAchivements) {
        this.setBuyerTargetAchivements(buyerTargetAchivements);
        return this;
    }

    public Buyer addBuyerTargetAchivement(BuyerTargetAchivement buyerTargetAchivement) {
        this.buyerTargetAchivements.add(buyerTargetAchivement);
        buyerTargetAchivement.setBuyer(this);
        return this;
    }

    public Buyer removeBuyerTargetAchivement(BuyerTargetAchivement buyerTargetAchivement) {
        this.buyerTargetAchivements.remove(buyerTargetAchivement);
        buyerTargetAchivement.setBuyer(null);
        return this;
    }

    public CollectionCenter getCollectionCenter() {
        return this.collectionCenter;
    }

    public void setCollectionCenter(CollectionCenter collectionCenter) {
        this.collectionCenter = collectionCenter;
    }

    public Buyer collectionCenter(CollectionCenter collectionCenter) {
        this.setCollectionCenter(collectionCenter);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Buyer)) {
            return false;
        }
        return getId() != null && getId().equals(((Buyer) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Buyer{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phone=" + getPhone() +
            ", isWhatsAppEnabled='" + getIsWhatsAppEnabled() + "'" +
            ", preferedLanguage='" + getPreferedLanguage() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
