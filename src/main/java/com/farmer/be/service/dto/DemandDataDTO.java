package com.farmer.be.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.farmer.be.domain.DemandData} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DemandDataDTO implements Serializable {

    private Long id;

    private String fromCPC;

    private String toCC;

    private String pCode;

    private String article;

    private String description;

    private String uom;

    private Float netWeightGrams;

    private Float crateSize;

    private Float indentUom;

    private Float indentKg;

    private Boolean isActive;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private CropDTO crop;

    private CollectionCenterDTO collectionCenter;

    private DemandDataFileDTO file;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromCPC() {
        return fromCPC;
    }

    public void setFromCPC(String fromCPC) {
        this.fromCPC = fromCPC;
    }

    public String getToCC() {
        return toCC;
    }

    public void setToCC(String toCC) {
        this.toCC = toCC;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public Float getNetWeightGrams() {
        return netWeightGrams;
    }

    public void setNetWeightGrams(Float netWeightGrams) {
        this.netWeightGrams = netWeightGrams;
    }

    public Float getCrateSize() {
        return crateSize;
    }

    public void setCrateSize(Float crateSize) {
        this.crateSize = crateSize;
    }

    public Float getIndentUom() {
        return indentUom;
    }

    public void setIndentUom(Float indentUom) {
        this.indentUom = indentUom;
    }

    public Float getIndentKg() {
        return indentKg;
    }

    public void setIndentKg(Float indentKg) {
        this.indentKg = indentKg;
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

    public CropDTO getCrop() {
        return crop;
    }

    public void setCrop(CropDTO crop) {
        this.crop = crop;
    }

    public CollectionCenterDTO getCollectionCenter() {
        return collectionCenter;
    }

    public void setCollectionCenter(CollectionCenterDTO collectionCenter) {
        this.collectionCenter = collectionCenter;
    }

    public DemandDataFileDTO getFile() {
        return file;
    }

    public void setFile(DemandDataFileDTO file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandDataDTO)) {
            return false;
        }

        DemandDataDTO demandDataDTO = (DemandDataDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, demandDataDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandDataDTO{" +
            "id=" + getId() +
            ", fromCPC='" + getFromCPC() + "'" +
            ", toCC='" + getToCC() + "'" +
            ", pCode='" + getpCode() + "'" +
            ", article='" + getArticle() + "'" +
            ", description='" + getDescription() + "'" +
            ", uom='" + getUom() + "'" +
            ", netWeightGrams=" + getNetWeightGrams() +
            ", crateSize=" + getCrateSize() +
            ", indentUom=" + getIndentUom() +
            ", indentKg=" + getIndentKg() +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", crop=" + getCrop() +
            ", collectionCenter=" + getCollectionCenter() +
            ", file=" + getFile() +
            "}";
    }
}
