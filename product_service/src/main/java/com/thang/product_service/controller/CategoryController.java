package com.thang.product_service.controller;

import com.thang.product_service.constant.ApiRoutes;
import com.thang.product_service.dto.ApiResult;
import com.thang.product_service.dto.request.CreateCategoryRequest;
import com.thang.product_service.dto.request.UpdateCategoryRequest;
import com.thang.product_service.dto.response.CategoryDetailResponse;
import com.thang.product_service.service.CategoryService;
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
@RequestMapping(ApiRoutes.CATEGORIES)
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ApiResult<CategoryDetailResponse>> createCategory(
            @RequestBody @Valid CreateCategoryRequest request) {
        log.info("POST {}", ApiRoutes.CATEGORIES);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResult.success(categoryService.createCategory(request), "Category created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResult<CategoryDetailResponse>> getCategoryById(@PathVariable UUID id) {
        log.info("GET {}/{}", ApiRoutes.CATEGORIES, id);
        return ResponseEntity.ok(
                ApiResult.success(categoryService.getCategoryById(id), "Category fetched successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResult<List<CategoryDetailResponse>>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET {} page={} size={}", ApiRoutes.CATEGORIES, page, size);
        return ResponseEntity.ok(
                ApiResult.successPage(categoryService.getAllCategories(page, size), "Categories fetched successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResult<CategoryDetailResponse>> updateCategory(
            @PathVariable UUID id, @RequestBody @Valid UpdateCategoryRequest request) {
        log.info("PUT {}/{}", ApiRoutes.CATEGORIES, id);
        return ResponseEntity.ok(
                ApiResult.success(categoryService.updateCategory(id, request), "Category updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResult<?>> deleteCategory(@PathVariable UUID id) {
        log.info("DELETE {}/{}", ApiRoutes.CATEGORIES, id);
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(ApiResult.success("Category deleted successfully"));
    }
}
