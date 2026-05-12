package com.thang.product_service.service.impl;

import com.thang.product_service.constant.ErrorCode;
import com.thang.product_service.dto.request.CreateProductRequest;
import com.thang.product_service.dto.request.UpdateProductRequest;
import com.thang.product_service.dto.response.ProductResponse;
import com.thang.product_service.entity.Product;
import com.thang.product_service.entity.ProductCategory;
import com.thang.product_service.exception.ApplicationException;
import com.thang.product_service.mapper.ProductMapper;
import com.thang.product_service.repository.CategoryRepository;
import com.thang.product_service.repository.ProductCategoryRepository;
import com.thang.product_service.repository.ProductRepository;
import com.thang.product_service.service.ProductService;
import com.thang.product_service.utils.SlugUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductResponse createProduct(CreateProductRequest request) {
        log.info("Creating product: {}", request.getName());

        var category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ApplicationException(ErrorCode.CATEGORY_NOT_FOUND));

        if (Boolean.FALSE.equals(category.getIsActive())) {
            throw new ApplicationException(ErrorCode.CATEGORY_INACTIVE);
        }

        Product product = productMapper.toProductEntity(request);
        product.setSlug(SlugUtils.toSlug(product.getName()));
        Product savedProduct = productRepository.save(product);

        ProductCategory productCategory = ProductCategory.builder()
                .product(savedProduct)
                .category(category)
                .build();
        productCategoryRepository.save(productCategory);

        savedProduct.setCategories(List.of(productCategory));

        return productMapper.toProductResponse(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(UUID id) {
        log.info("Fetching product: {}", id);
        return productMapper.toProductResponse(
                productRepository.findById(id)
                        .orElseThrow(() -> new ApplicationException(ErrorCode.PRODUCT_NOT_FOUND)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(int page, int size) {
        log.info("Fetching all products page={} size={}", page, size);
        var pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return productRepository.findAll(pageable).map(productMapper::toProductResponse);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(UUID id, UpdateProductRequest request) {
        log.info("Updating product: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.PRODUCT_NOT_FOUND));

        product.setName(request.getName());
        product.setSlug(SlugUtils.toSlug(request.getName()));
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCompareAtPrice(request.getCompareAtPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setStatus(request.getStatus());

        return productMapper.toProductResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    public void deleteProduct(UUID id) {
        log.info("Deleting product: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.PRODUCT_NOT_FOUND));
        productRepository.delete(product);
    }
}
