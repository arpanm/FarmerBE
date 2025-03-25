package com.farmer.be.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.farmer.be.domain.BannerContent} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BannerContentDTO implements Serializable {

    private Long id;

    @NotNull
    private String bannerTag;

    private String logoPath;

    private String imagePath;

    private String heading;

    private String subHeading;

    private String description;

    private String landingLink;

    private String landingUtm;

    private String pixelLink;

    private Instant startTime;

    private Instant endTime;

    private Boolean isActive;

    @NotNull
    private String createddBy;

    @NotNull
    private Instant createdTime;

    @NotNull
    private String updatedBy;

    @NotNull
    private Instant updatedTime;

    private CarouselContentDTO holdingCarousel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBannerTag() {
        return bannerTag;
    }

    public void setBannerTag(String bannerTag) {
        this.bannerTag = bannerTag;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSubHeading() {
        return subHeading;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLandingLink() {
        return landingLink;
    }

    public void setLandingLink(String landingLink) {
        this.landingLink = landingLink;
    }

    public String getLandingUtm() {
        return landingUtm;
    }

    public void setLandingUtm(String landingUtm) {
        this.landingUtm = landingUtm;
    }

    public String getPixelLink() {
        return pixelLink;
    }

    public void setPixelLink(String pixelLink) {
        this.pixelLink = pixelLink;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
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

    public CarouselContentDTO getHoldingCarousel() {
        return holdingCarousel;
    }

    public void setHoldingCarousel(CarouselContentDTO holdingCarousel) {
        this.holdingCarousel = holdingCarousel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BannerContentDTO)) {
            return false;
        }

        BannerContentDTO bannerContentDTO = (BannerContentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bannerContentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BannerContentDTO{" +
            "id=" + getId() +
            ", bannerTag='" + getBannerTag() + "'" +
            ", logoPath='" + getLogoPath() + "'" +
            ", imagePath='" + getImagePath() + "'" +
            ", heading='" + getHeading() + "'" +
            ", subHeading='" + getSubHeading() + "'" +
            ", description='" + getDescription() + "'" +
            ", landingLink='" + getLandingLink() + "'" +
            ", landingUtm='" + getLandingUtm() + "'" +
            ", pixelLink='" + getPixelLink() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createddBy='" + getCreateddBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updatedTime='" + getUpdatedTime() + "'" +
            ", holdingCarousel=" + getHoldingCarousel() +
            "}";
    }
}
