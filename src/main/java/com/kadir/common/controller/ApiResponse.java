package com.kadir.common.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private Integer status;

    private T payload;

    private String errorMessage;

    public static <T> ApiResponse<T> success(T payload) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(200);
        apiResponse.setErrorMessage(null);
        apiResponse.setPayload(payload);
        return apiResponse;
    }

    public static <T> ApiResponse<T> error(String errorMessage) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(500);
        apiResponse.setErrorMessage(errorMessage);
        apiResponse.setPayload(null);
        return apiResponse;
    }
}
