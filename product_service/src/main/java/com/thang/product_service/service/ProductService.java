package com.thang.product_service.service;

import com.thang.product_service.dto.request.CreateProductRequest;
import com.thang.product_service.entity.Product;


public interface ProductService {
    Product createProduct(CreateProductRequest request);
}
