package com.farmer.be.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CarouselContent.
 */
@Entity
@Table(name = "carousel_content")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CarouselContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "carousel_tag", nullable = false)
    private String carouselTag;

    @Column(name = "show_view_more")
    private Boolean showViewMore;

    @Column(name = "view_more_link")
    private String viewMoreLink;

    @Column(name = "view_more_utm")
    private String viewMoreUtm;

    @Column(name = "pixel_link")
    private String pixelLink;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "holdingCarousel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "holdingCarousel" }, allowSetters = true)
    private Set<BannerContent> banners = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CarouselContent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarouselTag() {
        return this.carouselTag;
    }

    public CarouselContent carouselTag(String carouselTag) {
        this.setCarouselTag(carouselTag);
        return this;
    }

    public void setCarouselTag(String carouselTag) {
        this.carouselTag = carouselTag;
    }

    public Boolean getShowViewMore() {
        return this.showViewMore;
    }

    public CarouselContent showViewMore(Boolean showViewMore) {
        this.setShowViewMore(showViewMore);
        return this;
    }

    public void setShowViewMore(Boolean showViewMore) {
        this.showViewMore = showViewMore;
    }

    public String getViewMoreLink() {
        return this.viewMoreLink;
    }

    public CarouselContent viewMoreLink(String viewMoreLink) {
        this.setViewMoreLink(viewMoreLink);
        return this;
    }

    public void setViewMoreLink(String viewMoreLink) {
        this.viewMoreLink = viewMoreLink;
    }

    public String getViewMoreUtm() {
        return this.viewMoreUtm;
    }

    public CarouselContent viewMoreUtm(String viewMoreUtm) {
        this.setViewMoreUtm(viewMoreUtm);
        return this;
    }

    public void setViewMoreUtm(String viewMoreUtm) {
        this.viewMoreUtm = viewMoreUtm;
    }

    public String getPixelLink() {
        return this.pixelLink;
    }

    public CarouselContent pixelLink(String pixelLink) {
        this.setPixelLink(pixelLink);
        return this;
    }

    public void setPixelLink(String pixelLink) {
        this.pixelLink = pixelLink;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public CarouselContent isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getCreateddBy() {
        return this.createddBy;
    }

    public CarouselContent createddBy(String createddBy) {
        this.setCreateddBy(createddBy);
        return this;
    }

    public void setCreateddBy(String createddBy) {
        this.createddBy = createddBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public CarouselContent createdTime(Instant createdTime) {
        this.setCreatedTime(createdTime);
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public CarouselContent updatedBy(String updatedBy) {
        this.setUpdatedBy(updatedBy);
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdatedTime() {
        return this.updatedTime;
    }

    public CarouselContent updatedTime(Instant updatedTime) {
        this.setUpdatedTime(updatedTime);
        return this;
    }

    public void setUpdatedTime(Instant updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Set<BannerContent> getBanners() {
        return this.banners;
    }

    public void setBanners(Set<BannerContent> bannerContents) {
        if (this.banners != null) {
            this.banners.forEach(i -> i.setHoldingCarousel(null));
        }
        if (bannerContents != null) {
            bannerContents.forEach(i -> i.setHoldingCarousel(this));
        }
        this.banners = bannerContents;
    }

    public CarouselContent banners(Set<BannerContent> bannerContents) {
        this.setBanners(bannerContents);
        return this;
    }

    public CarouselContent addBanners(BannerContent bannerContent) {
        this.banners.add(bannerContent);
        bannerContent.setHoldingCarousel(this);
        return this;
    }

    public CarouselContent removeBanners(BannerContent bannerContent) {
        this.banners.remove(bannerContent);
        bannerContent.setHoldingCarousel(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarouselContent)) {
            return false;
        }
        return getId() != null && getId().equals(((CarouselContent) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CarouselContent{" +
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
