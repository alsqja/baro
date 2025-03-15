package com.example.baro.error.exception;

import com.example.baro.error.ErrorCode;
import lombok.Getter;

@Getter
public class UnAuthorizedException extends CustomException {

    public UnAuthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
