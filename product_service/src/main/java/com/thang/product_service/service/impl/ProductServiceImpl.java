package com.thang.product_service.service.impl;

import com.thang.product_service.dto.request.CreateProductRequest;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Product createProduct(CreateProductRequest request) {
        log.info("Creating product: {}", request.getName());

        var existedCategory = categoryRepository.findById(request.getCategoryId());
        if (existedCategory.isEmpty()) {
            throw new ApplicationException("Category Not Found");
        }
        if (Boolean.FALSE.equals(existedCategory.get().getIsActive())) {
            throw new ApplicationException("Category is not active");
        }

        Product product = productMapper.toProductEntity(request);
        product.setSlug(SlugUtils.toSlug(product.getName()));
        Product savedProduct = productRepository.save(product);

        ProductCategory productCategory = ProductCategory.builder()
                .product(savedProduct)
                .category(existedCategory.get())
                .build();

        productCategoryRepository.save(productCategory);

        return savedProduct;
    }
}
