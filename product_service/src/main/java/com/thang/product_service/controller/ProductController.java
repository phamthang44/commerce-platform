package com.thang.product_service.controller;

import com.thang.product_service.dto.BaseResponse;
import com.thang.product_service.dto.request.CreateProductRequest;
import com.thang.product_service.entity.Product;
import com.thang.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<BaseResponse<Product>> createProduct(@RequestBody @Valid CreateProductRequest request) {
        log.info("POST /v1/products called");

        var response = productService.createProduct(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                BaseResponse.<Product>builder()
                        .data(response)
                        .message("Product Created Success")
                        .build());
    }

}
