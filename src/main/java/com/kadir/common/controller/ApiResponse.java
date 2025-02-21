package com.kadir.common.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private HttpStatus status;

    private T payload;

    private String errorMessage;

    public static <T> ApiResponse<T> success(T payload) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setErrorMessage(null);
        apiResponse.setPayload(payload);
        return apiResponse;
    }

    public static <T> ApiResponse<T> error(String errorMessage) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(HttpStatus.BAD_REQUEST);
        apiResponse.setErrorMessage(errorMessage);
        apiResponse.setPayload(null);
        return apiResponse;
    }
}
