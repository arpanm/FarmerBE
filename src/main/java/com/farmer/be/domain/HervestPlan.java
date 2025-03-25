package com.farmer.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A HervestPlan.
 */
@Entity
@Table(name = "hervest_plan")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class HervestPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "hervest_plan_date")
    private LocalDate hervestPlanDate;

    @Column(name = "hervest_plan_value")
    private Float hervestPlanValue;

    @Column(name = "hervest_plan_value_min")
    private Float hervestPlanValueMin;

    @Column(name = "hervest_plan_value_max")
    private Float hervestPlanValueMax;

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
    @JsonIgnoreProperties(
        value = {
            "addresses",
            "documents",
            "hervestPlanRules",
            "hervestPlans",
            "supplyConfirmations",
            "pickUpConfirmations",
            "crops",
            "accessories",
            "farmer",
        },
        allowSetters = true
    )
    private Farm farm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "demands", "prices", "hervestPlanRules", "hervestPlans", "supplyConfirmations", "pickUpConfirmations", "category", "farms",
        },
        allowSetters = true
    )
    private Crop crop;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public HervestPlan id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getHervestPlanDate() {
        return this.hervestPlanDate;
    }

    public HervestPlan hervestPlanDate(LocalDate hervestPlanDate) {
        this.setHervestPlanDate(hervestPlanDate);
        return this;
    }

    public void setHervestPlanDate(LocalDate hervestPlanDate) {
        this.hervestPlanDate = hervestPlanDate;
    }

    public Float getHervestPlanValue() {
        return this.hervestPlanValue;
    }

    public HervestPlan hervestPlanValue(Float hervestPlanValue) {
        this.setHervestPlanValue(hervestPlanValue);
        return this;
    }

    public void setHervestPlanValue(Float hervestPlanValue) {
        this.hervestPlanValue = hervestPlanValue;
    }

    public Float getHervestPlanValueMin() {
        return this.hervestPlanValueMin;
    }

    public HervestPlan hervestPlanValueMin(Float hervestPlanValueMin) {
        this.setHervestPlanValueMin(hervestPlanValueMin);
        return this;
    }

    public void setHervestPlanValueMin(Float hervestPlanValueMin) {
        this.hervestPlanValueMin = hervestPlanValueMin;
    }

    public Float getHervestPlanValueMax() {
        return this.hervestPlanValueMax;
    }

    public HervestPlan hervestPlanValueMax(Float hervestPlanValueMax) {
        this.setHervestPlanValueMax(hervestPlanValueMax);
        return this;
    }

    public void setHervestPlanValueMax(Float hervestPlanValueMax) {
        this.hervestPlanValueMax = hervestPlanValueMax;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public HervestPlan isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public HervestPlan createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public HervestPlan createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public HervestPlan updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public HervestPlan updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Farm getFarm() {
        return this.farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public HervestPlan farm(Farm farm) {
        this.setFarm(farm);
        return this;
    }

    public Crop getCrop() {
        return this.crop;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }

    public HervestPlan crop(Crop crop) {
        this.setCrop(crop);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HervestPlan)) {
            return false;
        }
        return getId() != null && getId().equals(((HervestPlan) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HervestPlan{" +
            "id=" + getId() +
            ", hervestPlanDate='" + getHervestPlanDate() + "'" +
            ", hervestPlanValue=" + getHervestPlanValue() +
            ", hervestPlanValueMin=" + getHervestPlanValueMin() +
            ", hervestPlanValueMax=" + getHervestPlanValueMax() +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
