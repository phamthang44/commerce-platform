package com.thang.product_service.service;

import com.thang.product_service.dto.request.CreateCategoryRequest;
import com.thang.product_service.dto.request.UpdateCategoryRequest;
import com.thang.product_service.dto.response.CategoryDetailResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface CategoryService {
    CategoryDetailResponse createCategory(CreateCategoryRequest request);
    CategoryDetailResponse getCategoryById(UUID id);
    Page<CategoryDetailResponse> getAllCategories(int page, int size);
    CategoryDetailResponse updateCategory(UUID id, UpdateCategoryRequest request);
    void deleteCategory(UUID id);
}
