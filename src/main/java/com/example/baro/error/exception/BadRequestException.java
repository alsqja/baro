package com.example.baro.error.exception;

import com.example.baro.error.ErrorCode;
import lombok.Getter;

@Getter
public class BadRequestException extends CustomException {

    public BadRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
