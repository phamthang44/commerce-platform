package com.thang.product_service.service.impl;

import com.thang.product_service.constant.ErrorCode;
import com.thang.product_service.dto.request.CreateProductRequest;
import com.thang.product_service.dto.request.ProductDeductRequest;
import com.thang.product_service.dto.request.ProductFilter;
import com.thang.product_service.dto.request.UpdateProductRequest;
import com.thang.product_service.dto.response.ProductDTO;
import com.thang.product_service.dto.response.ProductResponse;
import com.thang.product_service.entity.Product;
import com.thang.product_service.entity.ProductCategory;
import com.thang.product_service.exception.ApplicationException;
import com.thang.product_service.mapper.ProductMapper;
import com.thang.product_service.repository.CategoryRepository;
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

        ProductCategory productCategory = ProductCategory.builder()
                .product(product)
                .category(category)
                .build();
        product.setCategories(List.of(productCategory));

        return productMapper.toProductResponse(productRepository.save(product));
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
    public ProductDTO deductStock(UUID id, ProductDeductRequest request) {
        log.info("Deducting {} unit(s) from product: {}", request.getQuantity(), id);

        Product product = productRepository.findByIdWithLock(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.PRODUCT_NOT_FOUND));

        if (product.getStockQuantity() < request.getQuantity()) {
            throw new ApplicationException(ErrorCode.INSUFFICIENT_STOCK);
        }

        product.setStockQuantity(product.getStockQuantity() - request.getQuantity());

        return productMapper.toProductDTO(productRepository.save(product));
    }

    @Override
    @Transactional
    public void deleteProduct(UUID id) {
        log.info("Deleting product: {}", id);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApplicationException(ErrorCode.PRODUCT_NOT_FOUND));
        productRepository.delete(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getProductsByIds(ProductFilter productFilter) {
        return productRepository.findByIdIn(productFilter.getProductIds())
                .stream()
                .map(productMapper::toProductDTO)
                .toList();
    }
}
