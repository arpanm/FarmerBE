package com.farmer.be.domain;

import com.farmer.be.domain.enumeration.AttendenceType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Attendence.
 */
@Entity
@Table(name = "attendence")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Attendence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "attendence_type")
    private AttendenceType attendenceType;

    @Column(name = "attendence_date")
    private LocalDate attendenceDate;

    @Column(name = "attendence_time")
    private Instant attendenceTime;

    @Column(name = "lat")
    private Float lat;

    @Column(name = "lon")
    private Float lon;

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

    public Attendence id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AttendenceType getAttendenceType() {
        return this.attendenceType;
    }

    public Attendence attendenceType(AttendenceType attendenceType) {
        this.setAttendenceType(attendenceType);
        return this;
    }

    public void setAttendenceType(AttendenceType attendenceType) {
        this.attendenceType = attendenceType;
    }

    public LocalDate getAttendenceDate() {
        return this.attendenceDate;
    }

    public Attendence attendenceDate(LocalDate attendenceDate) {
        this.setAttendenceDate(attendenceDate);
        return this;
    }

    public void setAttendenceDate(LocalDate attendenceDate) {
        this.attendenceDate = attendenceDate;
    }

    public Instant getAttendenceTime() {
        return this.attendenceTime;
    }

    public Attendence attendenceTime(Instant attendenceTime) {
        this.setAttendenceTime(attendenceTime);
        return this;
    }

    public void setAttendenceTime(Instant attendenceTime) {
        this.attendenceTime = attendenceTime;
    }

    public Float getLat() {
        return this.lat;
    }

    public Attendence lat(Float lat) {
        this.setLat(lat);
        return this;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return this.lon;
    }

    public Attendence lon(Float lon) {
        this.setLon(lon);
        return this;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Attendence isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public Attendence createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public Attendence createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public Attendence updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public Attendence updatedTime(Instant updatedTime) {
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

    public Attendence buyer(Buyer buyer) {
        this.setBuyer(buyer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attendence)) {
            return false;
        }
        return getId() != null && getId().equals(((Attendence) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attendence{" +
            "id=" + getId() +
            ", attendenceType='" + getAttendenceType() + "'" +
            ", attendenceDate='" + getAttendenceDate() + "'" +
            ", attendenceTime='" + getAttendenceTime() + "'" +
            ", lat=" + getLat() +
            ", lon=" + getLon() +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
