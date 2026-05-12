package com.thang.product_service.constant;

public final class ApiRoutes {

    public static final String V1 = "/v1";
    public static final String PRODUCTS = V1 + "/products";
    public static final String PRODUCT_BY_ID = PRODUCTS + "/{id}";
    public static final String CATEGORIES = V1 + "/categories";
    public static final String CATEGORY_BY_ID = CATEGORIES + "/{id}";

    private ApiRoutes() {}
}
