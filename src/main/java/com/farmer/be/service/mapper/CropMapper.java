package com.farmer.be.service.mapper;

import com.farmer.be.domain.Category;
import com.farmer.be.domain.Crop;
import com.farmer.be.service.dto.CategoryDTO;
import com.farmer.be.service.dto.CropDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Crop} and its DTO {@link CropDTO}.
 */
@Mapper(componentModel = "spring")
public interface CropMapper extends EntityMapper<CropDTO, Crop> {
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryId")
    CropDTO toDto(Crop s);

    @Named("categoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoryDTO toDtoCategoryId(Category category);
}
