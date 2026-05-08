package com.thang.product_service.service;

import com.thang.product_service.dto.request.CreateProductRequest;
import com.thang.product_service.dto.response.ProductResponse;

import java.util.UUID;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest request);
    ProductResponse getProductById(UUID id);
}
