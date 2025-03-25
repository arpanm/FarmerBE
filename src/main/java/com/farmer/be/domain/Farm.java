package com.farmer.be.domain;

import com.farmer.be.domain.enumeration.FarmType;
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
 * A Farm.
 */
@Entity
@Table(name = "farm")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Farm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "farm_type")
    private FarmType farmType;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "relationship_with_owner")
    private String relationshipWithOwner;

    @Column(name = "area_in_acres")
    private Float areaInAcres;

    @Column(name = "farm_document_no")
    private String farmDocumentNo;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "farm")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "documents", "farmer", "farm" }, allowSetters = true)
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "farm")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "farmer", "farm", "address", "panDetails", "bankDetails" }, allowSetters = true)
    private Set<Document> documents = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "farm")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "farm", "crop" }, allowSetters = true)
    private Set<HervestPlanRule> hervestPlanRules = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "farm")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "farm", "crop" }, allowSetters = true)
    private Set<HervestPlan> hervestPlans = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "farm")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "farm", "crop" }, allowSetters = true)
    private Set<SupplyConfirmation> supplyConfirmations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "farm")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "grade", "itemPayment", "farm", "crop" }, allowSetters = true)
    private Set<PickUpConfirmation> pickUpConfirmations = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rel_farm__crop", joinColumns = @JoinColumn(name = "farm_id"), inverseJoinColumns = @JoinColumn(name = "crop_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "demands", "prices", "hervestPlanRules", "hervestPlans", "supplyConfirmations", "pickUpConfirmations", "category", "farms",
        },
        allowSetters = true
    )
    private Set<Crop> crops = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_farm__accessories",
        joinColumns = @JoinColumn(name = "farm_id"),
        inverseJoinColumns = @JoinColumn(name = "accessories_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "category", "farms" }, allowSetters = true)
    private Set<Accessories> accessories = new HashSet<>();

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

    public Farm id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FarmType getFarmType() {
        return this.farmType;
    }

    public Farm farmType(FarmType farmType) {
        this.setFarmType(farmType);
        return this;
    }

    public void setFarmType(FarmType farmType) {
        this.farmType = farmType;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public Farm ownerName(String ownerName) {
        this.setOwnerName(ownerName);
        return this;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getRelationshipWithOwner() {
        return this.relationshipWithOwner;
    }

    public Farm relationshipWithOwner(String relationshipWithOwner) {
        this.setRelationshipWithOwner(relationshipWithOwner);
        return this;
    }

    public void setRelationshipWithOwner(String relationshipWithOwner) {
        this.relationshipWithOwner = relationshipWithOwner;
    }

    public Float getAreaInAcres() {
        return this.areaInAcres;
    }

    public Farm areaInAcres(Float areaInAcres) {
        this.setAreaInAcres(areaInAcres);
        return this;
    }

    public void setAreaInAcres(Float areaInAcres) {
        this.areaInAcres = areaInAcres;
    }

    public String getFarmDocumentNo() {
        return this.farmDocumentNo;
    }

    public Farm farmDocumentNo(String farmDocumentNo) {
        this.setFarmDocumentNo(farmDocumentNo);
        return this;
    }

    public void setFarmDocumentNo(String farmDocumentNo) {
        this.farmDocumentNo = farmDocumentNo;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Farm isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public Farm createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public Farm createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Farm updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public Farm updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        if (this.addresses != null) {
            this.addresses.forEach(i -> i.setFarm(null));
        }
        if (addresses != null) {
            addresses.forEach(i -> i.setFarm(this));
        }
        this.addresses = addresses;
    }

    public Farm addresses(Set<Address> addresses) {
        this.setAddresses(addresses);
        return this;
    }

    public Farm addAddress(Address address) {
        this.addresses.add(address);
        address.setFarm(this);
        return this;
    }

    public Farm removeAddress(Address address) {
        this.addresses.remove(address);
        address.setFarm(null);
        return this;
    }

    public Set<Document> getDocuments() {
        return this.documents;
    }

    public void setDocuments(Set<Document> documents) {
        if (this.documents != null) {
            this.documents.forEach(i -> i.setFarm(null));
        }
        if (documents != null) {
            documents.forEach(i -> i.setFarm(this));
        }
        this.documents = documents;
    }

    public Farm documents(Set<Document> documents) {
        this.setDocuments(documents);
        return this;
    }

    public Farm addDocument(Document document) {
        this.documents.add(document);
        document.setFarm(this);
        return this;
    }

    public Farm removeDocument(Document document) {
        this.documents.remove(document);
        document.setFarm(null);
        return this;
    }

    public Set<HervestPlanRule> getHervestPlanRules() {
        return this.hervestPlanRules;
    }

    public void setHervestPlanRules(Set<HervestPlanRule> hervestPlanRules) {
        if (this.hervestPlanRules != null) {
            this.hervestPlanRules.forEach(i -> i.setFarm(null));
        }
        if (hervestPlanRules != null) {
            hervestPlanRules.forEach(i -> i.setFarm(this));
        }
        this.hervestPlanRules = hervestPlanRules;
    }

    public Farm hervestPlanRules(Set<HervestPlanRule> hervestPlanRules) {
        this.setHervestPlanRules(hervestPlanRules);
        return this;
    }

    public Farm addHervestPlanRule(HervestPlanRule hervestPlanRule) {
        this.hervestPlanRules.add(hervestPlanRule);
        hervestPlanRule.setFarm(this);
        return this;
    }

    public Farm removeHervestPlanRule(HervestPlanRule hervestPlanRule) {
        this.hervestPlanRules.remove(hervestPlanRule);
        hervestPlanRule.setFarm(null);
        return this;
    }

    public Set<HervestPlan> getHervestPlans() {
        return this.hervestPlans;
    }

    public void setHervestPlans(Set<HervestPlan> hervestPlans) {
        if (this.hervestPlans != null) {
            this.hervestPlans.forEach(i -> i.setFarm(null));
        }
        if (hervestPlans != null) {
            hervestPlans.forEach(i -> i.setFarm(this));
        }
        this.hervestPlans = hervestPlans;
    }

    public Farm hervestPlans(Set<HervestPlan> hervestPlans) {
        this.setHervestPlans(hervestPlans);
        return this;
    }

    public Farm addHervestPlan(HervestPlan hervestPlan) {
        this.hervestPlans.add(hervestPlan);
        hervestPlan.setFarm(this);
        return this;
    }

    public Farm removeHervestPlan(HervestPlan hervestPlan) {
        this.hervestPlans.remove(hervestPlan);
        hervestPlan.setFarm(null);
        return this;
    }

    public Set<SupplyConfirmation> getSupplyConfirmations() {
        return this.supplyConfirmations;
    }

    public void setSupplyConfirmations(Set<SupplyConfirmation> supplyConfirmations) {
        if (this.supplyConfirmations != null) {
            this.supplyConfirmations.forEach(i -> i.setFarm(null));
        }
        if (supplyConfirmations != null) {
            supplyConfirmations.forEach(i -> i.setFarm(this));
        }
        this.supplyConfirmations = supplyConfirmations;
    }

    public Farm supplyConfirmations(Set<SupplyConfirmation> supplyConfirmations) {
        this.setSupplyConfirmations(supplyConfirmations);
        return this;
    }

    public Farm addSupplyConfirmation(SupplyConfirmation supplyConfirmation) {
        this.supplyConfirmations.add(supplyConfirmation);
        supplyConfirmation.setFarm(this);
        return this;
    }

    public Farm removeSupplyConfirmation(SupplyConfirmation supplyConfirmation) {
        this.supplyConfirmations.remove(supplyConfirmation);
        supplyConfirmation.setFarm(null);
        return this;
    }

    public Set<PickUpConfirmation> getPickUpConfirmations() {
        return this.pickUpConfirmations;
    }

    public void setPickUpConfirmations(Set<PickUpConfirmation> pickUpConfirmations) {
        if (this.pickUpConfirmations != null) {
            this.pickUpConfirmations.forEach(i -> i.setFarm(null));
        }
        if (pickUpConfirmations != null) {
            pickUpConfirmations.forEach(i -> i.setFarm(this));
        }
        this.pickUpConfirmations = pickUpConfirmations;
    }

    public Farm pickUpConfirmations(Set<PickUpConfirmation> pickUpConfirmations) {
        this.setPickUpConfirmations(pickUpConfirmations);
        return this;
    }

    public Farm addPickUpConfirmation(PickUpConfirmation pickUpConfirmation) {
        this.pickUpConfirmations.add(pickUpConfirmation);
        pickUpConfirmation.setFarm(this);
        return this;
    }

    public Farm removePickUpConfirmation(PickUpConfirmation pickUpConfirmation) {
        this.pickUpConfirmations.remove(pickUpConfirmation);
        pickUpConfirmation.setFarm(null);
        return this;
    }

    public Set<Crop> getCrops() {
        return this.crops;
    }

    public void setCrops(Set<Crop> crops) {
        this.crops = crops;
    }

    public Farm crops(Set<Crop> crops) {
        this.setCrops(crops);
        return this;
    }

    public Farm addCrop(Crop crop) {
        this.crops.add(crop);
        return this;
    }

    public Farm removeCrop(Crop crop) {
        this.crops.remove(crop);
        return this;
    }

    public Set<Accessories> getAccessories() {
        return this.accessories;
    }

    public void setAccessories(Set<Accessories> accessories) {
        this.accessories = accessories;
    }

    public Farm accessories(Set<Accessories> accessories) {
        this.setAccessories(accessories);
        return this;
    }

    public Farm addAccessories(Accessories accessories) {
        this.accessories.add(accessories);
        return this;
    }

    public Farm removeAccessories(Accessories accessories) {
        this.accessories.remove(accessories);
        return this;
    }

    public Farmer getFarmer() {
        return this.farmer;
    }

    public void setFarmer(Farmer farmer) {
        this.farmer = farmer;
    }

    public Farm farmer(Farmer farmer) {
        this.setFarmer(farmer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Farm)) {
            return false;
        }
        return getId() != null && getId().equals(((Farm) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Farm{" +
            "id=" + getId() +
            ", farmType='" + getFarmType() + "'" +
            ", ownerName='" + getOwnerName() + "'" +
            ", relationshipWithOwner='" + getRelationshipWithOwner() + "'" +
            ", areaInAcres=" + getAreaInAcres() +
            ", farmDocumentNo='" + getFarmDocumentNo() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
