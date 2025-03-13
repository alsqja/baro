package com.example.baro.model.dto;

import com.example.baro.model.User;
import lombok.Getter;

@Getter
public class UserResDto {
    private final String username;
    private final String nickname;

    public UserResDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
    }
}
