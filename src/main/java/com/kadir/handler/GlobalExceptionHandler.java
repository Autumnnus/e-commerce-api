package com.kadir.handler;

import com.kadir.exception.BaseException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<ApiError<?>> handleBaseException(BaseException ex, WebRequest request) {
        return ResponseEntity.badRequest().body(createApiError(ex.getMessage(), request));
    }

    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity<ApiError<?>> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createApiError("Token has expired.", request));
    }
    
    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<ApiError<?>> handleJwtRuntimeException(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createApiError("JWT processing error: " + ex.getMessage(), request));
    }


    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError<Map<String, List<String>>>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e, WebRequest request) {

        Map<String, List<String>> map = new HashMap<>();
        for (ObjectError objError : e.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError) objError).getField();
            if (map.containsKey(fieldName)) {
                map.put(fieldName, addValue(map.get(fieldName), objError.getDefaultMessage()));
            } else {
                map.put(fieldName, addValue(new ArrayList<>(), objError.getDefaultMessage()));
            }
        }

        return ResponseEntity.badRequest().body(createApiError(map, request));
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<ApiError<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e,
                                                                             WebRequest request) {
        String message = e.getMessage();

        return ResponseEntity.badRequest().body(createApiError(message, request));
    }

    private List<String> addValue(List<String> list, String value) {
        list.add(value);
        return list;
    }

    private String getHostName() {
        try {
            return Inet4Address.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "";
    }

    public <E> ApiError<E> createApiError(E message, WebRequest request) {
        ApiError<E> apiError = new ApiError<>();
        apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        Exception<E> exception = new Exception<>();
        exception.setPath(request.getDescription(false));
        exception.setMessage(message);
        exception.setCreatedAt(new Date());
        exception.setHostName(getHostName());

        apiError.setException(exception);

        return apiError;
    }
}
