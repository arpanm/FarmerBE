package com.farmer.be.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.farmer.be.domain.BuyerTargetAchivement} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BuyerTargetAchivementDTO implements Serializable {

    private Long id;

    private Long labour;

    private Long farmVisit;

    private Float totalCollection;

    private LocalDate targetDate;

    private Float attendenceHours;

    private Long achivementLabour;

    private Long achivementFarmVisit;

    private Float achivementTotalCollection;

    private Float achivementAttendenceHours;

    private Float achivementScore;

    private Float incentive;

    private Float kmDriven;

    private Float conveyance;

    private Boolean isActive;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private BuyerDTO buyer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLabour() {
        return labour;
    }

    public void setLabour(Long labour) {
        this.labour = labour;
    }

    public Long getFarmVisit() {
        return farmVisit;
    }

    public void setFarmVisit(Long farmVisit) {
        this.farmVisit = farmVisit;
    }

    public Float getTotalCollection() {
        return totalCollection;
    }

    public void setTotalCollection(Float totalCollection) {
        this.totalCollection = totalCollection;
    }

    public LocalDate getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(LocalDate targetDate) {
        this.targetDate = targetDate;
    }

    public Float getAttendenceHours() {
        return attendenceHours;
    }

    public void setAttendenceHours(Float attendenceHours) {
        this.attendenceHours = attendenceHours;
    }

    public Long getAchivementLabour() {
        return achivementLabour;
    }

    public void setAchivementLabour(Long achivementLabour) {
        this.achivementLabour = achivementLabour;
    }

    public Long getAchivementFarmVisit() {
        return achivementFarmVisit;
    }

    public void setAchivementFarmVisit(Long achivementFarmVisit) {
        this.achivementFarmVisit = achivementFarmVisit;
    }

    public Float getAchivementTotalCollection() {
        return achivementTotalCollection;
    }

    public void setAchivementTotalCollection(Float achivementTotalCollection) {
        this.achivementTotalCollection = achivementTotalCollection;
    }

    public Float getAchivementAttendenceHours() {
        return achivementAttendenceHours;
    }

    public void setAchivementAttendenceHours(Float achivementAttendenceHours) {
        this.achivementAttendenceHours = achivementAttendenceHours;
    }

    public Float getAchivementScore() {
        return achivementScore;
    }

    public void setAchivementScore(Float achivementScore) {
        this.achivementScore = achivementScore;
    }

    public Float getIncentive() {
        return incentive;
    }

    public void setIncentive(Float incentive) {
        this.incentive = incentive;
    }

    public Float getKmDriven() {
        return kmDriven;
    }

    public void setKmDriven(Float kmDriven) {
        this.kmDriven = kmDriven;
    }

    public Float getConveyance() {
        return conveyance;
    }

    public void setConveyance(Float conveyance) {
        this.conveyance = conveyance;
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
        if (!(o instanceof BuyerTargetAchivementDTO)) {
            return false;
        }

        BuyerTargetAchivementDTO buyerTargetAchivementDTO = (BuyerTargetAchivementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, buyerTargetAchivementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BuyerTargetAchivementDTO{" +
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
            ", buyer=" + getBuyer() +
            "}";
    }
}
