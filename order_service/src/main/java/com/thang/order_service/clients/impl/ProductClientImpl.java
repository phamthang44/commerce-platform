package com.thang.order_service.clients.impl;

import com.thang.order_service.clients.ProductClient;
import com.thang.order_service.dto.ApiResult;
import com.thang.order_service.dto.client.request.ProductDeductRequest;
import com.thang.order_service.dto.client.request.ProductFilter;
import com.thang.order_service.dto.client.response.ProductDTO;
import com.thang.order_service.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

import static com.thang.order_service.constant.ErrorCode.PRODUCT_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class ProductClientImpl implements ProductClient {

    private final WebClient.Builder webClientBuilder;

    @Override
    public List<ProductDTO> getProductsByIds(ProductFilter productFilter) {
        ApiResult<List<ProductDTO>> response = webClientBuilder.build()
                .post()
                .uri("http://localhost:8888/v1/products/search")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(productFilter)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResult<List<ProductDTO>>>(){})
                .block();
        if (response == null || response.getData() == null || response.getData().isEmpty()) {
            throw new ApplicationException(PRODUCT_NOT_FOUND);
        }

        return response.getData();
    }

    @Override
    public void deductStock(ProductDeductRequest request) {
        ApiResult<ProductDTO> response = webClientBuilder.build()
                .patch()
                .uri("http://localhost:8888/v1/products/{id}/deduct", request.getProductId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResult<ProductDTO>>(){})
                .block();
        if (response == null || response.getData() == null) {
            throw new ApplicationException(PRODUCT_NOT_FOUND);
        }
    }

    @Override
    public void deductStockBatch(List<ProductDeductRequest> requests) {
        webClientBuilder.build()
                .patch()
                .uri("http://localhost:8888/v1/products/deduct/batch")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requests)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ApiResult<?>>(){})
                .block();
    }
}
