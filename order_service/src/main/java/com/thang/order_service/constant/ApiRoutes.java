package com.thang.order_service.constant;

public final class ApiRoutes {

    public static final String V1 = "/v1";
    public static final String ORDERS = V1 + "/orders";
    public static final String ORDER_BY_ID = ORDERS + "/{id}";

    private ApiRoutes() {}
}
