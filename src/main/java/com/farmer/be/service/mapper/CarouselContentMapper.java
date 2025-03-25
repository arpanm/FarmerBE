package com.farmer.be.service.mapper;

import com.farmer.be.domain.CarouselContent;
import com.farmer.be.service.dto.CarouselContentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CarouselContent} and its DTO {@link CarouselContentDTO}.
 */
@Mapper(componentModel = "spring")
public interface CarouselContentMapper extends EntityMapper<CarouselContentDTO, CarouselContent> {}
