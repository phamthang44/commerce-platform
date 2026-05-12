package com.thang.product_service.mapper;

import com.thang.product_service.dto.response.CategoryDetailResponse;
import com.thang.product_service.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "parentId", source = "parent.id")
    CategoryDetailResponse toCategoryDetailResponse(Category category);
}
