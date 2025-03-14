package com.example.baro.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserLoginReqDto {

    @NotBlank
    private final String username;

    @NotBlank
    private final String password;

    public UserLoginReqDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
