package com.farmer.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FieldVisit.
 */
@Entity
@Table(name = "field_visit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FieldVisit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "field_visit_date")
    private LocalDate fieldVisitDate;

    @Column(name = "field_visit_time")
    private Instant fieldVisitTime;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fieldVisit")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "farmer", "farm", "address", "panDetails", "bankDetails", "fieldVisit" }, allowSetters = true)
    private Set<Document> documents = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = { "farms", "attendences", "fieldVisits", "buyerTargetAchivements", "collectionCenter" },
        allowSetters = true
    )
    private Buyer buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(
        value = {
            "addresses",
            "documents",
            "hervestPlanRules",
            "hervestPlans",
            "supplyConfirmations",
            "pickUpConfirmations",
            "fieldVisits",
            "crops",
            "accessories",
            "farmer",
            "collectionCenter",
            "buyer",
        },
        allowSetters = true
    )
    private Farm farm;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FieldVisit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFieldVisitDate() {
        return this.fieldVisitDate;
    }

    public FieldVisit fieldVisitDate(LocalDate fieldVisitDate) {
        this.setFieldVisitDate(fieldVisitDate);
        return this;
    }

    public void setFieldVisitDate(LocalDate fieldVisitDate) {
        this.fieldVisitDate = fieldVisitDate;
    }

    public Instant getFieldVisitTime() {
        return this.fieldVisitTime;
    }

    public FieldVisit fieldVisitTime(Instant fieldVisitTime) {
        this.setFieldVisitTime(fieldVisitTime);
        return this;
    }

    public void setFieldVisitTime(Instant fieldVisitTime) {
        this.fieldVisitTime = fieldVisitTime;
    }

    public Float getLat() {
        return this.lat;
    }

    public FieldVisit lat(Float lat) {
        this.setLat(lat);
        return this;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return this.lon;
    }

    public FieldVisit lon(Float lon) {
        this.setLon(lon);
        return this;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public FieldVisit isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public FieldVisit createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public FieldVisit createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public FieldVisit updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public FieldVisit updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<Document> getDocuments() {
        return this.documents;
    }

    public void setDocuments(Set<Document> documents) {
        if (this.documents != null) {
            this.documents.forEach(i -> i.setFieldVisit(null));
        }
        if (documents != null) {
            documents.forEach(i -> i.setFieldVisit(this));
        }
        this.documents = documents;
    }

    public FieldVisit documents(Set<Document> documents) {
        this.setDocuments(documents);
        return this;
    }

    public FieldVisit addDocument(Document document) {
        this.documents.add(document);
        document.setFieldVisit(this);
        return this;
    }

    public FieldVisit removeDocument(Document document) {
        this.documents.remove(document);
        document.setFieldVisit(null);
        return this;
    }

    public Buyer getBuyer() {
        return this.buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public FieldVisit buyer(Buyer buyer) {
        this.setBuyer(buyer);
        return this;
    }

    public Farm getFarm() {
        return this.farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    public FieldVisit farm(Farm farm) {
        this.setFarm(farm);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldVisit)) {
            return false;
        }
        return getId() != null && getId().equals(((FieldVisit) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldVisit{" +
            "id=" + getId() +
            ", fieldVisitDate='" + getFieldVisitDate() + "'" +
            ", fieldVisitTime='" + getFieldVisitTime() + "'" +
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
