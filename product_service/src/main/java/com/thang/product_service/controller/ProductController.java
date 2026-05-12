package com.thang.product_service.controller;

import com.thang.product_service.constant.ApiRoutes;
import com.thang.product_service.dto.ApiResult;
import com.thang.product_service.dto.request.CreateProductRequest;
import com.thang.product_service.dto.request.UpdateProductRequest;
import com.thang.product_service.dto.response.ProductResponse;
import com.thang.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRoutes.PRODUCTS)
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResult<ProductResponse>> createProduct(
            @RequestBody @Valid CreateProductRequest request) {
        log.info("POST {}", ApiRoutes.PRODUCTS);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResult.success(productService.createProduct(request), "Product created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<ProductResponse>> getProduct(@PathVariable UUID id) {
        log.info("GET {}/{}", ApiRoutes.PRODUCTS, id);
        return ResponseEntity.ok(
                ApiResult.success(productService.getProductById(id), "Product fetched successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResult<List<ProductResponse>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET {} page={} size={}", ApiRoutes.PRODUCTS, page, size);
        return ResponseEntity.ok(
                ApiResult.successPage(productService.getAllProducts(page, size), "Products fetched successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<ProductResponse>> updateProduct(
            @PathVariable UUID id, @RequestBody @Valid UpdateProductRequest request) {
        log.info("PUT {}/{}", ApiRoutes.PRODUCTS, id);
        return ResponseEntity.ok(
                ApiResult.success(productService.updateProduct(id, request), "Product updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<?>> deleteProduct(@PathVariable UUID id) {
        log.info("DELETE {}/{}", ApiRoutes.PRODUCTS, id);
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResult.success("Product deleted successfully"));
    }
}
