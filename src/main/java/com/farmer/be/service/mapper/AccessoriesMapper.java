package com.farmer.be.service.mapper;

import com.farmer.be.domain.Accessories;
import com.farmer.be.domain.Category;
import com.farmer.be.service.dto.AccessoriesDTO;
import com.farmer.be.service.dto.CategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Accessories} and its DTO {@link AccessoriesDTO}.
 */
@Mapper(componentModel = "spring")
public interface AccessoriesMapper extends EntityMapper<AccessoriesDTO, Accessories> {
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryId")
    AccessoriesDTO toDto(Accessories s);

    @Named("categoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoryDTO toDtoCategoryId(Category category);
}
