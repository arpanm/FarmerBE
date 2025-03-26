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
 * A CollectionCenter.
 */
@Entity
@Table(name = "collection_center")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CollectionCenter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "cc_id")
    private String ccId;

    @Column(name = "ffdc_code")
    private String ffdcCode;

    @Column(name = "ffdc_name")
    private String ffdcName;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "collectionCenter")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "documents", "farmer", "farm", "collectionCenter" }, allowSetters = true)
    private Set<Address> addresses = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "collectionCenter")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "collectionCenter" }, allowSetters = true)
    private Set<LocationMapping> locationMappings = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_collection_center__crop",
        joinColumns = @JoinColumn(name = "collection_center_id"),
        inverseJoinColumns = @JoinColumn(name = "crop_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "demands",
            "prices",
            "hervestPlanRules",
            "hervestPlans",
            "supplyConfirmations",
            "pickUpConfirmations",
            "demandData",
            "category",
            "farms",
            "collectionCenters",
        },
        allowSetters = true
    )
    private Set<Crop> crops = new HashSet<>();

    @JsonIgnoreProperties(value = { "crop", "collectionCenter", "file" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "collectionCenter")
    private DemandData demandData;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CollectionCenter id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CollectionCenter name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCcId() {
        return this.ccId;
    }

    public CollectionCenter ccId(String ccId) {
        this.setCcId(ccId);
        return this;
    }

    public void setCcId(String ccId) {
        this.ccId = ccId;
    }

    public String getFfdcCode() {
        return this.ffdcCode;
    }

    public CollectionCenter ffdcCode(String ffdcCode) {
        this.setFfdcCode(ffdcCode);
        return this;
    }

    public void setFfdcCode(String ffdcCode) {
        this.ffdcCode = ffdcCode;
    }

    public String getFfdcName() {
        return this.ffdcName;
    }

    public CollectionCenter ffdcName(String ffdcName) {
        this.setFfdcName(ffdcName);
        return this;
    }

    public void setFfdcName(String ffdcName) {
        this.ffdcName = ffdcName;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public CollectionCenter isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public CollectionCenter createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public CollectionCenter createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public CollectionCenter updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public CollectionCenter updatedTime(Instant updatedTime) {
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
            this.addresses.forEach(i -> i.setCollectionCenter(null));
        }
        if (addresses != null) {
            addresses.forEach(i -> i.setCollectionCenter(this));
        }
        this.addresses = addresses;
    }

    public CollectionCenter addresses(Set<Address> addresses) {
        this.setAddresses(addresses);
        return this;
    }

    public CollectionCenter addAddress(Address address) {
        this.addresses.add(address);
        address.setCollectionCenter(this);
        return this;
    }

    public CollectionCenter removeAddress(Address address) {
        this.addresses.remove(address);
        address.setCollectionCenter(null);
        return this;
    }

    public Set<LocationMapping> getLocationMappings() {
        return this.locationMappings;
    }

    public void setLocationMappings(Set<LocationMapping> locationMappings) {
        if (this.locationMappings != null) {
            this.locationMappings.forEach(i -> i.setCollectionCenter(null));
        }
        if (locationMappings != null) {
            locationMappings.forEach(i -> i.setCollectionCenter(this));
        }
        this.locationMappings = locationMappings;
    }

    public CollectionCenter locationMappings(Set<LocationMapping> locationMappings) {
        this.setLocationMappings(locationMappings);
        return this;
    }

    public CollectionCenter addLocationMapping(LocationMapping locationMapping) {
        this.locationMappings.add(locationMapping);
        locationMapping.setCollectionCenter(this);
        return this;
    }

    public CollectionCenter removeLocationMapping(LocationMapping locationMapping) {
        this.locationMappings.remove(locationMapping);
        locationMapping.setCollectionCenter(null);
        return this;
    }

    public Set<Crop> getCrops() {
        return this.crops;
    }

    public void setCrops(Set<Crop> crops) {
        this.crops = crops;
    }

    public CollectionCenter crops(Set<Crop> crops) {
        this.setCrops(crops);
        return this;
    }

    public CollectionCenter addCrop(Crop crop) {
        this.crops.add(crop);
        return this;
    }

    public CollectionCenter removeCrop(Crop crop) {
        this.crops.remove(crop);
        return this;
    }

    public DemandData getDemandData() {
        return this.demandData;
    }

    public void setDemandData(DemandData demandData) {
        if (this.demandData != null) {
            this.demandData.setCollectionCenter(null);
        }
        if (demandData != null) {
            demandData.setCollectionCenter(this);
        }
        this.demandData = demandData;
    }

    public CollectionCenter demandData(DemandData demandData) {
        this.setDemandData(demandData);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CollectionCenter)) {
            return false;
        }
        return getId() != null && getId().equals(((CollectionCenter) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CollectionCenter{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", ccId='" + getCcId() + "'" +
            ", ffdcCode='" + getFfdcCode() + "'" +
            ", ffdcName='" + getFfdcName() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
