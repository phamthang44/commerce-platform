package com.thang.product_service.service.impl;

import com.thang.product_service.constant.ErrorCode;
import com.thang.product_service.dto.request.CreateCategoryRequest;
import com.thang.product_service.dto.request.UpdateCategoryRequest;
import com.thang.product_service.dto.response.CategoryDetailResponse;
import com.thang.product_service.entity.Category;
import com.thang.product_service.exception.ApplicationException;
import com.thang.product_service.mapper.CategoryMapper;
import com.thang.product_service.repository.CategoryRepository;
import com.thang.product_service.repository.ProductCategoryRepository;
import com.thang.product_service.service.CategoryService;
import com.thang.product_service.utils.SlugUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryDetailResponse createCategory(CreateCategoryRequest request) {
        log.info("Creating category: {}", request.getName());

        Category.CategoryBuilder builder = Category.builder()
                .name(request.getName())
                .slug(SlugUtils.toSlug(request.getName()))
                .isActive(true);

        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ApplicationException(ErrorCode.CATEGORY_NOT_FOUND));
            builder.parent(parent);
        }

        return categoryMapper.toCategoryDetailResponse(categoryRepository.save(builder.build()));
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDetailResponse getCategoryById(UUID id) {
        log.info("Fetching category: {}", id);
        return categoryMapper.toCategoryDetailResponse(
                categoryRepository.findById(id)
                        .orElseThrow(() -> new ApplicationException(ErrorCode.CATEGORY_NOT_FOUND)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryDetailResponse> getAllCategories(int page, int size) {
        log.info("Fetching all categories page={} size={}", page, size);
        var pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return categoryRepository.findAll(pageable).map(categoryMapper::toCategoryDetailResponse);
    }

    @Override
    @Transactional
    public CategoryDetailResponse updateCategory(UUID id, UpdateCategoryRequest request) {
        log.info("Updating category: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.CATEGORY_NOT_FOUND));

        category.setName(request.getName());
        category.setSlug(SlugUtils.toSlug(request.getName()));
        category.setIsActive(request.getIsActive());
        category.setParent(request.getParentId() != null
                ? categoryRepository.findById(request.getParentId())
                        .orElseThrow(() -> new ApplicationException(ErrorCode.CATEGORY_NOT_FOUND))
                : null);

        return categoryMapper.toCategoryDetailResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void deleteCategory(UUID id) {
        log.info("Deleting category: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.CATEGORY_NOT_FOUND));

        if (productCategoryRepository.existsByIdCategoryId(id)) {
            throw new ApplicationException(ErrorCode.CATEGORY_HAS_PRODUCTS);
        }

        categoryRepository.delete(category);
    }
}
