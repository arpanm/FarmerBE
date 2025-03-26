package com.farmer.be.domain;

import com.farmer.be.domain.enumeration.UploadStatus;
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
 * A DemandDataFile.
 */
@Entity
@Table(name = "demand_data_file")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DemandDataFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "uploaded_date")
    private LocalDate uploadedDate;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "uploaded_by")
    private String uploadedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UploadStatus status;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "file")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "crop", "collectionCenter", "file" }, allowSetters = true)
    private Set<DemandData> demandData = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DemandDataFile id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getUploadedDate() {
        return this.uploadedDate;
    }

    public DemandDataFile uploadedDate(LocalDate uploadedDate) {
        this.setUploadedDate(uploadedDate);
        return this;
    }

    public void setUploadedDate(LocalDate uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public String getFileName() {
        return this.fileName;
    }

    public DemandDataFile fileName(String fileName) {
        this.setFileName(fileName);
        return this;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUploadedBy() {
        return this.uploadedBy;
    }

    public DemandDataFile uploadedBy(String uploadedBy) {
        this.setUploadedBy(uploadedBy);
        return this;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public UploadStatus getStatus() {
        return this.status;
    }

    public DemandDataFile status(UploadStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(UploadStatus status) {
        this.status = status;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public DemandDataFile isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public DemandDataFile createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public DemandDataFile createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public DemandDataFile updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public DemandDataFile updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<DemandData> getDemandData() {
        return this.demandData;
    }

    public void setDemandData(Set<DemandData> demandData) {
        if (this.demandData != null) {
            this.demandData.forEach(i -> i.setFile(null));
        }
        if (demandData != null) {
            demandData.forEach(i -> i.setFile(this));
        }
        this.demandData = demandData;
    }

    public DemandDataFile demandData(Set<DemandData> demandData) {
        this.setDemandData(demandData);
        return this;
    }

    public DemandDataFile addDemandData(DemandData demandData) {
        this.demandData.add(demandData);
        demandData.setFile(this);
        return this;
    }

    public DemandDataFile removeDemandData(DemandData demandData) {
        this.demandData.remove(demandData);
        demandData.setFile(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DemandDataFile)) {
            return false;
        }
        return getId() != null && getId().equals(((DemandDataFile) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandDataFile{" +
            "id=" + getId() +
            ", uploadedDate='" + getUploadedDate() + "'" +
            ", fileName='" + getFileName() + "'" +
            ", uploadedBy='" + getUploadedBy() + "'" +
            ", status='" + getStatus() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
