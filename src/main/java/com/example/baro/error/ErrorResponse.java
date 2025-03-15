package com.example.baro.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Objects;

@Getter
public class ErrorResponse {

    private final ErrorDto error;

    public ErrorResponse(String message, String code) {
        this.error = new ErrorDto(message, code);
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {

        return new ResponseEntity<>(new ErrorResponse(errorCode.name(), errorCode.getMessage()), errorCode.getHttpStatus());
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(MethodArgumentNotValidException e) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(
                new ErrorResponse(
                        "BAD_INPUT",
                        Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage()
                ),
                status
        );
    }
}
