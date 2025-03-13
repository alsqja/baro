package com.example.baro.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserSignupDto {

    @NotBlank
    private final String username;

    @NotBlank
    private final String password;

    @NotBlank
    private final String nickname;

    public UserSignupDto(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }
}
