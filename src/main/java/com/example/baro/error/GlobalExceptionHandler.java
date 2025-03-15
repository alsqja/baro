package com.example.baro.error;

import com.example.baro.error.exception.BadRequestException;
import com.example.baro.error.exception.CustomException;
import com.example.baro.error.exception.ForbiddenException;
import com.example.baro.error.exception.NotFoundException;
import com.example.baro.error.exception.UnAuthorizedException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CustomException.class, BadRequestException.class, ForbiddenException.class, NotFoundException.class, UnAuthorizedException.class})
    public ResponseEntity<ErrorResponse> handleBadRequestException(CustomException e) {

        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        return ErrorResponse.toResponseEntity(e);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(AuthenticationException e) {

        return ErrorResponse.toResponseEntity(ErrorCode.INVALID_TOKEN);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {

        return ErrorResponse.toResponseEntity(ErrorCode.ACCESS_DENIED);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
        return ErrorResponse.toResponseEntity(ErrorCode.ACCESS_DENIED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> handleJwtException(JwtException e) {
        return ErrorResponse.toResponseEntity(ErrorCode.INVALID_TOKEN);
    }
}
