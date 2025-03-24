package com.farmer.be.domain;

import com.farmer.be.domain.enumeration.Language;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SessionContext.
 */
@Entity
@Table(name = "session_context")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SessionContext implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "session_id")
    private String sessionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @Column(name = "is_logged_in")
    private Boolean isLoggedIn;

    @Column(name = "farmer_id")
    private String farmerId;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SessionContext id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public SessionContext sessionId(String sessionId) {
        this.setSessionId(sessionId);
        return this;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Language getLanguage() {
        return this.language;
    }

    public SessionContext language(Language language) {
        this.setLanguage(language);
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Boolean getIsLoggedIn() {
        return this.isLoggedIn;
    }

    public SessionContext isLoggedIn(Boolean isLoggedIn) {
        this.setIsLoggedIn(isLoggedIn);
        return this;
    }

    public void setIsLoggedIn(Boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public String getFarmerId() {
        return this.farmerId;
    }

    public SessionContext farmerId(String farmerId) {
        this.setFarmerId(farmerId);
        return this;
    }

    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public SessionContext isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public SessionContext createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public SessionContext createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public SessionContext updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public SessionContext updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SessionContext)) {
            return false;
        }
        return getId() != null && getId().equals(((SessionContext) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SessionContext{" +
            "id=" + getId() +
            ", sessionId='" + getSessionId() + "'" +
            ", language='" + getLanguage() + "'" +
            ", isLoggedIn='" + getIsLoggedIn() + "'" +
            ", farmerId='" + getFarmerId() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
