package com.farmer.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BannerContent.
 */
@Entity
@Table(name = "banner_content")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BannerContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "banner_tag", nullable = false)
    private String bannerTag;

    @Column(name = "logo_path")
    private String logoPath;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "heading")
    private String heading;

    @Column(name = "sub_heading")
    private String subHeading;

    @Column(name = "description")
    private String description;

    @Column(name = "landing_link")
    private String landingLink;

    @Column(name = "landing_utm")
    private String landingUtm;

    @Column(name = "pixel_link")
    private String pixelLink;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

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
    @JsonIgnoreProperties(value = { "banners" }, allowSetters = true)
    private CarouselContent holdingCarousel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BannerContent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBannerTag() {
        return this.bannerTag;
    }

    public BannerContent bannerTag(String bannerTag) {
        this.setBannerTag(bannerTag);
        return this;
    }

    public void setBannerTag(String bannerTag) {
        this.bannerTag = bannerTag;
    }

    public String getLogoPath() {
        return this.logoPath;
    }

    public BannerContent logoPath(String logoPath) {
        this.setLogoPath(logoPath);
        return this;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public BannerContent imagePath(String imagePath) {
        this.setImagePath(imagePath);
        return this;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getHeading() {
        return this.heading;
    }

    public BannerContent heading(String heading) {
        this.setHeading(heading);
        return this;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSubHeading() {
        return this.subHeading;
    }

    public BannerContent subHeading(String subHeading) {
        this.setSubHeading(subHeading);
        return this;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }

    public String getDescription() {
        return this.description;
    }

    public BannerContent description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLandingLink() {
        return this.landingLink;
    }

    public BannerContent landingLink(String landingLink) {
        this.setLandingLink(landingLink);
        return this;
    }

    public void setLandingLink(String landingLink) {
        this.landingLink = landingLink;
    }

    public String getLandingUtm() {
        return this.landingUtm;
    }

    public BannerContent landingUtm(String landingUtm) {
        this.setLandingUtm(landingUtm);
        return this;
    }

    public void setLandingUtm(String landingUtm) {
        this.landingUtm = landingUtm;
    }

    public String getPixelLink() {
        return this.pixelLink;
    }

    public BannerContent pixelLink(String pixelLink) {
        this.setPixelLink(pixelLink);
        return this;
    }

    public void setPixelLink(String pixelLink) {
        this.pixelLink = pixelLink;
    }

    public Instant getStartTime() {
        return this.startTime;
    }

    public BannerContent startTime(Instant startTime) {
        this.setStartTime(startTime);
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return this.endTime;
    }

    public BannerContent endTime(Instant endTime) {
        this.setEndTime(endTime);
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public BannerContent isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public BannerContent createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public BannerContent createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public BannerContent updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public BannerContent updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public CarouselContent getHoldingCarousel() {
        return this.holdingCarousel;
    }

    public void setHoldingCarousel(CarouselContent carouselContent) {
        this.holdingCarousel = carouselContent;
    }

    public BannerContent holdingCarousel(CarouselContent carouselContent) {
        this.setHoldingCarousel(carouselContent);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BannerContent)) {
            return false;
        }
        return getId() != null && getId().equals(((BannerContent) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BannerContent{" +
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
            "}";
    }
}
