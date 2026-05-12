package com.thang.product_service.repository;

import com.thang.product_service.entity.ProductCategory;
import com.thang.product_service.entity.ProductCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, ProductCategoryId> {
    boolean existsByIdCategoryId(UUID categoryId);
}
