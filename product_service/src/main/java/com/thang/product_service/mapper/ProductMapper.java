package com.thang.product_service.mapper;

import com.thang.product_service.dto.request.CreateProductRequest;
import com.thang.product_service.dto.response.CategoryResponse;
import com.thang.product_service.dto.response.ProductResponse;
import com.thang.product_service.entity.Product;
import com.thang.product_service.entity.ProductCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProductEntity(CreateProductRequest request);

    ProductResponse toProductResponse(Product product);

    // Unwraps the join-table row so MapStruct can build List<CategoryResponse>
    // from List<ProductCategory> automatically when mapping Product.categories.
    @Mapping(target = "id",   source = "category.id")
    @Mapping(target = "name", source = "category.name")
    @Mapping(target = "slug", source = "category.slug")
    CategoryResponse toCategoryResponse(ProductCategory productCategory);
}
