package com.farmer.be.service.mapper;

import com.farmer.be.domain.Accessories;
import com.farmer.be.domain.Category;
import com.farmer.be.domain.Farm;
import com.farmer.be.service.dto.AccessoriesDTO;
import com.farmer.be.service.dto.CategoryDTO;
import com.farmer.be.service.dto.FarmDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Accessories} and its DTO {@link AccessoriesDTO}.
 */
@Mapper(componentModel = "spring")
public interface AccessoriesMapper extends EntityMapper<AccessoriesDTO, Accessories> {
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryId")
    @Mapping(target = "farms", source = "farms", qualifiedByName = "farmIdSet")
    AccessoriesDTO toDto(Accessories s);

    @Mapping(target = "farms", ignore = true)
    @Mapping(target = "removeFarm", ignore = true)
    Accessories toEntity(AccessoriesDTO accessoriesDTO);

    @Named("categoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoryDTO toDtoCategoryId(Category category);

    @Named("farmId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FarmDTO toDtoFarmId(Farm farm);

    @Named("farmIdSet")
    default Set<FarmDTO> toDtoFarmIdSet(Set<Farm> farm) {
        return farm.stream().map(this::toDtoFarmId).collect(Collectors.toSet());
    }
}
