package com.farmer.be.service.mapper;

import com.farmer.be.domain.BannerContent;
import com.farmer.be.domain.CarouselContent;
import com.farmer.be.service.dto.BannerContentDTO;
import com.farmer.be.service.dto.CarouselContentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BannerContent} and its DTO {@link BannerContentDTO}.
 */
@Mapper(componentModel = "spring")
public interface BannerContentMapper extends EntityMapper<BannerContentDTO, BannerContent> {
    @Mapping(target = "holdingCarousel", source = "holdingCarousel", qualifiedByName = "carouselContentId")
    BannerContentDTO toDto(BannerContent s);

    @Named("carouselContentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CarouselContentDTO toDtoCarouselContentId(CarouselContent carouselContent);
}
