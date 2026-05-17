package com.thang.order_service.clients;

import com.thang.order_service.dto.client.request.ProductDeductRequest;
import com.thang.order_service.dto.client.request.ProductFilter;
import com.thang.order_service.dto.client.response.ProductDTO;

import java.util.List;
import java.util.UUID;

public interface ProductClient {
    List<ProductDTO> getProductsByIds(ProductFilter productFilter);

    void deductStock(ProductDeductRequest productDeductRequest);

    void deductStockBatch(List<ProductDeductRequest> requests);
}
