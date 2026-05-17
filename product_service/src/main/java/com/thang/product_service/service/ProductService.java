package com.thang.product_service.service;

import com.thang.product_service.dto.request.CreateProductRequest;
import com.thang.product_service.dto.request.ProductDeductRequest;
import com.thang.product_service.dto.request.ProductFilter;
import com.thang.product_service.dto.request.UpdateProductRequest;
import com.thang.product_service.dto.response.ProductDTO;
import com.thang.product_service.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest request);
    ProductResponse getProductById(UUID id);
    Page<ProductResponse> getAllProducts(int page, int size);
    ProductResponse updateProduct(UUID id, UpdateProductRequest request);
    ProductDTO deductStock(UUID id, ProductDeductRequest request);
    void deleteProduct(UUID id);
    List<ProductDTO> getProductsByIds(ProductFilter productFilter);
}
