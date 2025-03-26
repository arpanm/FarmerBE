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
 * A BuyerTargetAchivement.
 */
@Entity
@Table(name = "buyer_target_achivement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BuyerTargetAchivement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "labour")
    private Long labour;

    @Column(name = "farm_visit")
    private Long farmVisit;

    @Column(name = "total_collection")
    private Float totalCollection;

    @Column(name = "target_date")
    private LocalDate targetDate;

    @Column(name = "attendence_hours")
    private Float attendenceHours;

    @Column(name = "achivement_labour")
    private Long achivementLabour;

    @Column(name = "achivement_farm_visit")
    private Long achivementFarmVisit;

    @Column(name = "achivement_total_collection")
    private Float achivementTotalCollection;

    @Column(name = "achivement_attendence_hours")
    private Float achivementAttendenceHours;

    @Column(name = "achivement_score")
    private Float achivementScore;

    @Column(name = "incentive")
    private Float incentive;

    @Column(name = "km_driven")
    private Float kmDriven;

    @Column(name = "conveyance")
    private Float conveyance;

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
        value = { "farms", "attendences", "fieldVisits", "buyerTargetAchivements", "collectionCenter" },
        allowSetters = true
    )
    private Buyer buyer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BuyerTargetAchivement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLabour() {
        return this.labour;
    }

    public BuyerTargetAchivement labour(Long labour) {
        this.setLabour(labour);
        return this;
    }

    public void setLabour(Long labour) {
        this.labour = labour;
    }

    public Long getFarmVisit() {
        return this.farmVisit;
    }

    public BuyerTargetAchivement farmVisit(Long farmVisit) {
        this.setFarmVisit(farmVisit);
        return this;
    }

    public void setFarmVisit(Long farmVisit) {
        this.farmVisit = farmVisit;
    }

    public Float getTotalCollection() {
        return this.totalCollection;
    }

    public BuyerTargetAchivement totalCollection(Float totalCollection) {
        this.setTotalCollection(totalCollection);
        return this;
    }

    public void setTotalCollection(Float totalCollection) {
        this.totalCollection = totalCollection;
    }

    public LocalDate getTargetDate() {
        return this.targetDate;
    }

    public BuyerTargetAchivement targetDate(LocalDate targetDate) {
        this.setTargetDate(targetDate);
        return this;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public Float getAttendenceHours() {
        return this.attendenceHours;
    }

    public BuyerTargetAchivement attendenceHours(Float attendenceHours) {
        this.setAttendenceHours(attendenceHours);
        return this;
    }

    public void setAttendenceHours(Float attendenceHours) {
        this.attendenceHours = attendenceHours;
    }

    public Long getAchivementLabour() {
        return this.achivementLabour;
    }

    public BuyerTargetAchivement achivementLabour(Long achivementLabour) {
        this.setAchivementLabour(achivementLabour);
        return this;
    }

    public void setAchivementLabour(Long achivementLabour) {
        this.achivementLabour = achivementLabour;
    }

    public Long getAchivementFarmVisit() {
        return this.achivementFarmVisit;
    }

    public BuyerTargetAchivement achivementFarmVisit(Long achivementFarmVisit) {
        this.setAchivementFarmVisit(achivementFarmVisit);
        return this;
    }

    public void setAchivementFarmVisit(Long achivementFarmVisit) {
        this.achivementFarmVisit = achivementFarmVisit;
    }

    public Float getAchivementTotalCollection() {
        return this.achivementTotalCollection;
    }

    public BuyerTargetAchivement achivementTotalCollection(Float achivementTotalCollection) {
        this.setAchivementTotalCollection(achivementTotalCollection);
        return this;
    }

    public void setAchivementTotalCollection(Float achivementTotalCollection) {
        this.achivementTotalCollection = achivementTotalCollection;
    }

    public Float getAchivementAttendenceHours() {
        return this.achivementAttendenceHours;
    }

    public BuyerTargetAchivement achivementAttendenceHours(Float achivementAttendenceHours) {
        this.setAchivementAttendenceHours(achivementAttendenceHours);
        return this;
    }

    public void setAchivementAttendenceHours(Float achivementAttendenceHours) {
        this.achivementAttendenceHours = achivementAttendenceHours;
    }

    public Float getAchivementScore() {
        return this.achivementScore;
    }

    public BuyerTargetAchivement achivementScore(Float achivementScore) {
        this.setAchivementScore(achivementScore);
        return this;
    }

    public void setAchivementScore(Float achivementScore) {
        this.achivementScore = achivementScore;
    }

    public Float getIncentive() {
        return this.incentive;
    }

    public BuyerTargetAchivement incentive(Float incentive) {
        this.setIncentive(incentive);
        return this;
    }

    public void setIncentive(Float incentive) {
        this.incentive = incentive;
    }

    public Float getKmDriven() {
        return this.kmDriven;
    }

    public BuyerTargetAchivement kmDriven(Float kmDriven) {
        this.setKmDriven(kmDriven);
        return this;
    }

    public void setKmDriven(Float kmDriven) {
        this.kmDriven = kmDriven;
    }

    public Float getConveyance() {
        return this.conveyance;
    }

    public BuyerTargetAchivement conveyance(Float conveyance) {
        this.setConveyance(conveyance);
        return this;
    }

    public void setConveyance(Float conveyance) {
        this.conveyance = conveyance;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public BuyerTargetAchivement isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public BuyerTargetAchivement createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public BuyerTargetAchivement createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public BuyerTargetAchivement updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public BuyerTargetAchivement updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Buyer getBuyer() {
        return this.buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public BuyerTargetAchivement buyer(Buyer buyer) {
        this.setBuyer(buyer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BuyerTargetAchivement)) {
            return false;
        }
        return getId() != null && getId().equals(((BuyerTargetAchivement) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BuyerTargetAchivement{" +
            "id=" + getId() +
            ", labour=" + getLabour() +
            ", farmVisit=" + getFarmVisit() +
            ", totalCollection=" + getTotalCollection() +
            ", targetDate='" + getTargetDate() + "'" +
            ", attendenceHours=" + getAttendenceHours() +
            ", achivementLabour=" + getAchivementLabour() +
            ", achivementFarmVisit=" + getAchivementFarmVisit() +
            ", achivementTotalCollection=" + getAchivementTotalCollection() +
            ", achivementAttendenceHours=" + getAchivementAttendenceHours() +
            ", achivementScore=" + getAchivementScore() +
            ", incentive=" + getIncentive() +
            ", kmDriven=" + getKmDriven() +
            ", conveyance=" + getConveyance() +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
