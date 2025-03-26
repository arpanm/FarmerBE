package com.farmer.be.domain;

import com.farmer.be.domain.enumeration.AreaType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LocationMapping.
 */
@Entity
@Table(name = "location_mapping")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LocationMapping implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "area_name")
    private String areaName;

    @Enumerated(EnumType.STRING)
    @Column(name = "area_type")
    private AreaType areaType;

    @Column(name = "pincode")
    private Long pincode;

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
    @JsonIgnoreProperties(value = { "addresses", "locationMappings", "crops", "demandData" }, allowSetters = true)
    private CollectionCenter collectionCenter;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LocationMapping id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public LocationMapping areaName(String areaName) {
        this.setAreaName(areaName);
        return this;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public AreaType getAreaType() {
        return this.areaType;
    }

    public LocationMapping areaType(AreaType areaType) {
        this.setAreaType(areaType);
        return this;
    }

    public void setAreaType(AreaType areaType) {
        this.areaType = areaType;
    }

    public Long getPincode() {
        return this.pincode;
    }

    public LocationMapping pincode(Long pincode) {
        this.setPincode(pincode);
        return this;
    }

    public void setPincode(Long pincode) {
        this.pincode = pincode;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public LocationMapping isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public LocationMapping createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public LocationMapping createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public LocationMapping updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public LocationMapping updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public CollectionCenter getCollectionCenter() {
        return this.collectionCenter;
    }

    public void setCollectionCenter(CollectionCenter collectionCenter) {
        this.collectionCenter = collectionCenter;
    }

    public LocationMapping collectionCenter(CollectionCenter collectionCenter) {
        this.setCollectionCenter(collectionCenter);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocationMapping)) {
            return false;
        }
        return getId() != null && getId().equals(((LocationMapping) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationMapping{" +
            "id=" + getId() +
            ", areaName='" + getAreaName() + "'" +
            ", areaType='" + getAreaType() + "'" +
            ", pincode=" + getPincode() +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
