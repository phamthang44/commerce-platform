package com.thang.product_service.service;

import com.thang.product_service.dto.request.CreateProductRequest;
import com.thang.product_service.dto.request.UpdateProductRequest;
import com.thang.product_service.dto.response.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest request);
    ProductResponse getProductById(UUID id);
    Page<ProductResponse> getAllProducts(int page, int size);
    ProductResponse updateProduct(UUID id, UpdateProductRequest request);
    void deleteProduct(UUID id);
}
