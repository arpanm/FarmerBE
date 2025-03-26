package com.farmer.be.service.dto;

import com.farmer.be.domain.enumeration.FarmType;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.farmer.be.domain.Farm} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FarmDTO implements Serializable {

    private Long id;

    private FarmType farmType;

    private String ownerName;

    private String relationshipWithOwner;

    private Float areaInAcres;

    private String farmDocumentNo;

    private Boolean isActive;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private Set<CropDTO> crops = new HashSet<>();

    private Set<AccessoriesDTO> accessories = new HashSet<>();

    private FarmerDTO farmer;

    private CollectionCenterDTO collectionCenter;

    private BuyerDTO buyer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FarmType getFarmType() {
        return farmType;
    }

    public void setFarmType(FarmType farmType) {
        this.farmType = farmType;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getRelationshipWithOwner() {
        return relationshipWithOwner;
    }

    public void setRelationshipWithOwner(String relationshipWithOwner) {
        this.relationshipWithOwner = relationshipWithOwner;
    }

    public Float getAreaInAcres() {
        return areaInAcres;
    }

    public void setAreaInAcres(Float areaInAcres) {
        this.areaInAcres = areaInAcres;
    }

    public String getFarmDocumentNo() {
        return farmDocumentNo;
    }

    public void setFarmDocumentNo(String farmDocumentNo) {
        this.farmDocumentNo = farmDocumentNo;
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

    public Set<CropDTO> getCrops() {
        return crops;
    }

    public void setCrops(Set<CropDTO> crops) {
        this.crops = crops;
    }

    public Set<AccessoriesDTO> getAccessories() {
        return accessories;
    }

    public void setAccessories(Set<AccessoriesDTO> accessories) {
        this.accessories = accessories;
    }

    public FarmerDTO getFarmer() {
        return farmer;
    }

    public void setFarmer(FarmerDTO farmer) {
        this.farmer = farmer;
    }

    public CollectionCenterDTO getCollectionCenter() {
        return collectionCenter;
    }

    public void setCollectionCenter(CollectionCenterDTO collectionCenter) {
        this.collectionCenter = collectionCenter;
    }

    public BuyerDTO getBuyer() {
        return buyer;
    }

    public void setBuyer(BuyerDTO buyer) {
        this.buyer = buyer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FarmDTO)) {
            return false;
        }

        FarmDTO farmDTO = (FarmDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, farmDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FarmDTO{" +
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
            ", crops=" + getCrops() +
            ", accessories=" + getAccessories() +
            ", farmer=" + getFarmer() +
            ", collectionCenter=" + getCollectionCenter() +
            ", buyer=" + getBuyer() +
            "}";
    }
}
