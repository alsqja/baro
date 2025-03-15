package com.example.baro.error;

import lombok.Getter;

@Getter
public class ErrorDto {

    private final String code;
    private final String message;

    public ErrorDto(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
