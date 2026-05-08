package com.thang.product_service.controller;

import com.thang.product_service.constant.ApiRoutes;
import com.thang.product_service.dto.BaseResponse;
import com.thang.product_service.dto.request.CreateProductRequest;
import com.thang.product_service.dto.response.ProductResponse;
import com.thang.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRoutes.PRODUCTS)
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<BaseResponse<ProductResponse>> createProduct(@RequestBody @Valid CreateProductRequest request) {
        log.info("POST {}", ApiRoutes.PRODUCTS);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                BaseResponse.<ProductResponse>builder()
                        .data(productService.createProduct(request))
                        .message("Product Created Success")
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ProductResponse>> getProduct(@PathVariable UUID id) {
        log.info("GET {}", ApiRoutes.PRODUCT_BY_ID);

        return ResponseEntity.ok(
                BaseResponse.<ProductResponse>builder()
                        .data(productService.getProductById(id))
                        .message("Product Fetched Success")
                        .build());
    }
}
