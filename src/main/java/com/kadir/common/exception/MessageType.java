package com.kadir.common.exception;

import lombok.Getter;

@Getter
public enum MessageType {
    USERNAME_ALREADY_EXISTS("1001", "Username already exists"),
    EMAIL_ALREADY_EXISTS("1002", "Email already exists"),
    PHONE_NUMBER_ALREADY_EXISTS("1003", "Phone number already exists"),
    NO_RECORD_EXIST("1004", "No record exist"),
    TOKEN_IS_EXPIRED("1005", "Token is expired"),
    USERNAME_NOT_FOUND("1006", "Username not found"),
    REFRESH_TOKEN_NOT_FOUND("1008", "Refresh token not found"),
    REFRESH_TOKEN_IS_EXPIRED("1009", "Refresh token is expired"),
    USERNAME_OR_PASSWORD_INVALID("1007", "Username or password invalid"),
    FIRST_NAME_AND_LAST_NAME_REQUIRED("1010", "First name and last name required"),
    COMPANY_NAME_REQUIRED("1011", "Company name required"),
    GENERAL_EXCEPTION("9999", "General exception");


    private String code;

    private String message;

    MessageType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
