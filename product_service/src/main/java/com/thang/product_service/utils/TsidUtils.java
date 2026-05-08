package com.thang.product_service.utils;


import com.github.f4b6a3.tsid.TsidCreator;

public final class TsidUtils {

    private TsidUtils() {}

    public static Long nextId() {
        // getTsid256() là hàm nhanh nhất và thread-safe của thư viện
        return TsidCreator.getTsid256().toLong();
    }

    // (Tùy chọn) Hàm trả về String Base62 nếu cần dùng ở DTO
    // public static String nextIdString() { ... }
}
