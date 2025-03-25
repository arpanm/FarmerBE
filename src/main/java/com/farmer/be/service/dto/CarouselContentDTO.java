package com.farmer.be.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.farmer.be.domain.CarouselContent} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CarouselContentDTO implements Serializable {

    private Long id;

    @NotNull
    private String carouselTag;

    private Boolean showViewMore;

    private String viewMoreLink;

    private String viewMoreUtm;

    private String pixelLink;

    private Boolean isActive;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarouselTag() {
        return carouselTag;
    }

    public void setCarouselTag(String carouselTag) {
        this.carouselTag = carouselTag;
    }

    public Boolean getShowViewMore() {
        return showViewMore;
    }

    public void setShowViewMore(Boolean showViewMore) {
        this.showViewMore = showViewMore;
    }

    public String getViewMoreLink() {
        return viewMoreLink;
    }

    public void setViewMoreLink(String viewMoreLink) {
        this.viewMoreLink = viewMoreLink;
    }

    public String getViewMoreUtm() {
        return viewMoreUtm;
    }

    public void setViewMoreUtm(String viewMoreUtm) {
        this.viewMoreUtm = viewMoreUtm;
    }

    public String getPixelLink() {
        return pixelLink;
    }

    public void setPixelLink(String pixelLink) {
        this.pixelLink = pixelLink;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarouselContentDTO)) {
            return false;
        }

        CarouselContentDTO carouselContentDTO = (CarouselContentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, carouselContentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarouselContentDTO{" +
            "id=" + getId() +
            ", carouselTag='" + getCarouselTag() + "'" +
            ", showViewMore='" + getShowViewMore() + "'" +
            ", viewMoreLink='" + getViewMoreLink() + "'" +
            ", viewMoreUtm='" + getViewMoreUtm() + "'" +
            ", pixelLink='" + getPixelLink() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            "}";
    }
}
