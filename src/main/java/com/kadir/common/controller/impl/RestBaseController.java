package com.kadir.common.controller.impl;

import com.kadir.common.controller.ApiResponse;

public class RestBaseController {

    public <T> ApiResponse<T> ok(T payload) {
        return ApiResponse.success(payload);
    }

    public <T> ApiResponse<T> error(String errorMessage) {
        return ApiResponse.error(errorMessage);
    }
}
