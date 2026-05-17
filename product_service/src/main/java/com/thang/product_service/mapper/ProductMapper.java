package com.thang.product_service.mapper;

import com.thang.product_service.dto.request.CreateProductRequest;
import com.thang.product_service.dto.response.CategoryDTO;
import com.thang.product_service.dto.response.CategoryResponse;
import com.thang.product_service.dto.response.ProductDTO;
import com.thang.product_service.dto.response.ProductResponse;
import com.thang.product_service.entity.Product;
import com.thang.product_service.entity.ProductCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProductEntity(CreateProductRequest request);

    ProductResponse toProductResponse(Product product);

    ProductDTO toProductDTO(Product product);

    @Mapping(target = "id",   source = "category.id")
    @Mapping(target = "name", source = "category.name")
    @Mapping(target = "slug", source = "category.slug")
    CategoryResponse toCategoryResponse(ProductCategory productCategory);

    @Mapping(target = "id",   source = "category.id")
    @Mapping(target = "name", source = "category.name")
    @Mapping(target = "slug", source = "category.slug")
    CategoryDTO toCategoryDTO(ProductCategory productCategory);
}
