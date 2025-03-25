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
 * A Crop.
 */
@Entity
@Table(name = "crop")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Crop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "description")
    private String description;

    @Column(name = "order_no")
    private Long orderNo;

    @Column(name = "sku_id")
    private String skuId;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "crop")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "crop" }, allowSetters = true)
    private Set<Demand> demands = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "crop")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "crop" }, allowSetters = true)
    private Set<Price> prices = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "categories", "crops", "accessories", "parent" }, allowSetters = true)
    private Category category;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "crops")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "addresses", "documents", "crops", "accessories", "farmer" }, allowSetters = true)
    private Set<Farm> farms = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Crop id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Crop name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public Crop imagePath(String imagePath) {
        this.setImagePath(imagePath);
        return this;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return this.description;
    }

    public Crop description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOrderNo() {
        return this.orderNo;
    }

    public Crop orderNo(Long orderNo) {
        this.setOrderNo(orderNo);
        return this;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public String getSkuId() {
        return this.skuId;
    }

    public Crop skuId(String skuId) {
        this.setSkuId(skuId);
        return this;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Crop isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public Crop createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public Crop createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Crop updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public Crop updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<Demand> getDemands() {
        return this.demands;
    }

    public void setDemands(Set<Demand> demands) {
        if (this.demands != null) {
            this.demands.forEach(i -> i.setCrop(null));
        }
        if (demands != null) {
            demands.forEach(i -> i.setCrop(this));
        }
        this.demands = demands;
    }

    public Crop demands(Set<Demand> demands) {
        this.setDemands(demands);
        return this;
    }

    public Crop addDemand(Demand demand) {
        this.demands.add(demand);
        demand.setCrop(this);
        return this;
    }

    public Crop removeDemand(Demand demand) {
        this.demands.remove(demand);
        demand.setCrop(null);
        return this;
    }

    public Set<Price> getPrices() {
        return this.prices;
    }

    public void setPrices(Set<Price> prices) {
        if (this.prices != null) {
            this.prices.forEach(i -> i.setCrop(null));
        }
        if (prices != null) {
            prices.forEach(i -> i.setCrop(this));
        }
        this.prices = prices;
    }

    public Crop prices(Set<Price> prices) {
        this.setPrices(prices);
        return this;
    }

    public Crop addPrice(Price price) {
        this.prices.add(price);
        price.setCrop(this);
        return this;
    }

    public Crop removePrice(Price price) {
        this.prices.remove(price);
        price.setCrop(null);
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Crop category(Category category) {
        this.setCategory(category);
        return this;
    }

    public Set<Farm> getFarms() {
        return this.farms;
    }

    public void setFarms(Set<Farm> farms) {
        if (this.farms != null) {
            this.farms.forEach(i -> i.removeCrop(this));
        }
        if (farms != null) {
            farms.forEach(i -> i.addCrop(this));
        }
        this.farms = farms;
    }

    public Crop farms(Set<Farm> farms) {
        this.setFarms(farms);
        return this;
    }

    public Crop addFarm(Farm farm) {
        this.farms.add(farm);
        farm.getCrops().add(this);
        return this;
    }

    public Crop removeFarm(Farm farm) {
        this.farms.remove(farm);
        farm.getCrops().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Crop)) {
            return false;
        }
        return getId() != null && getId().equals(((Crop) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Crop{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", imagePath='" + getImagePath() + "'" +
            ", description='" + getDescription() + "'" +
            ", orderNo=" + getOrderNo() +
            ", skuId='" + getSkuId() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
