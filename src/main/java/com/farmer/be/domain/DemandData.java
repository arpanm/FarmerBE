package com.farmer.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DemandData.
 */
@Entity
@Table(name = "demand_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DemandData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "from_cpc")
    private String fromCPC;

    @Column(name = "to_cc")
    private String toCC;

    @Column(name = "p_code")
    private String pCode;

    @Column(name = "article")
    private String article;

    @Column(name = "description")
    private String description;

    @Column(name = "uom")
    private String uom;

    @Column(name = "net_weight_grams")
    private Float netWeightGrams;

    @Column(name = "crate_size")
    private Float crateSize;

    @Column(name = "indent_uom")
    private Float indentUom;

    @Column(name = "indent_kg")
    private Float indentKg;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Crop crop;

    @JsonIgnoreProperties(value = { "addresses", "locationMappings", "crops", "demandData" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private CollectionCenter collectionCenter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "demandData" }, allowSetters = true)
    private DemandDataFile file;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DemandData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromCPC() {
        return this.fromCPC;
    }

    public DemandData fromCPC(String fromCPC) {
        this.setFromCPC(fromCPC);
        return this;
    }

    public void setFromCPC(String fromCPC) {
        this.fromCPC = fromCPC;
    }

    public String getToCC() {
        return this.toCC;
    }

    public DemandData toCC(String toCC) {
        this.setToCC(toCC);
        return this;
    }

    public void setToCC(String toCC) {
        this.toCC = toCC;
    }

    public String getpCode() {
        return this.pCode;
    }

    public DemandData pCode(String pCode) {
        this.setpCode(pCode);
        return this;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getArticle() {
        return this.article;
    }

    public DemandData article(String article) {
        this.setArticle(article);
        return this;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getDescription() {
        return this.description;
    }

    public DemandData description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUom() {
        return this.uom;
    }

    public DemandData uom(String uom) {
        this.setUom(uom);
        return this;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public Float getNetWeightGrams() {
        return this.netWeightGrams;
    }

    public DemandData netWeightGrams(Float netWeightGrams) {
        this.setNetWeightGrams(netWeightGrams);
        return this;
    }

    public void setNetWeightGrams(Float netWeightGrams) {
        this.netWeightGrams = netWeightGrams;
    }

    public Float getCrateSize() {
        return this.crateSize;
    }

    public DemandData crateSize(Float crateSize) {
        this.setCrateSize(crateSize);
        return this;
    }

    public void setCrateSize(Float crateSize) {
        this.crateSize = crateSize;
    }

    public Float getIndentUom() {
        return this.indentUom;
    }

    public DemandData indentUom(Float indentUom) {
        this.setIndentUom(indentUom);
        return this;
    }

    public void setIndentUom(Float indentUom) {
        this.indentUom = indentUom;
    }

    public Float getIndentKg() {
        return this.indentKg;
    }

    public DemandData indentKg(Float indentKg) {
        this.setIndentKg(indentKg);
        return this;
    }

    public void setIndentKg(Float indentKg) {
        this.indentKg = indentKg;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public DemandData isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public DemandData createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public DemandData createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public DemandData updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public DemandData updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Crop getCrop() {
        return this.crop;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }

    public DemandData crop(Crop crop) {
        this.setCrop(crop);
        return this;
    }

    public CollectionCenter getCollectionCenter() {
        return this.collectionCenter;
    }

    public void setCollectionCenter(CollectionCenter collectionCenter) {
        this.collectionCenter = collectionCenter;
    }

    public DemandData collectionCenter(CollectionCenter collectionCenter) {
        this.setCollectionCenter(collectionCenter);
        return this;
    }

    public DemandDataFile getFile() {
        return this.file;
    }

    public void setFile(DemandDataFile demandDataFile) {
        this.file = demandDataFile;
    }

    public DemandData file(DemandDataFile demandDataFile) {
        this.setFile(demandDataFile);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandData)) {
            return false;
        }
        return getId() != null && getId().equals(((DemandData) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandData{" +
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
            "}";
    }
}
