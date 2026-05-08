package com.thang.product_service.mapper;

import com.thang.product_service.dto.request.CreateProductRequest;
import com.thang.product_service.entity.Product;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProductEntity(CreateProductRequest createProductRequest);

}
