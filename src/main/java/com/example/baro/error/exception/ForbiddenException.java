package com.example.baro.error.exception;

import com.example.baro.error.ErrorCode;
import lombok.Getter;

@Getter
public class ForbiddenException extends CustomException {

    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
