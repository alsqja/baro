package com.example.baro.model.dto;

import com.example.baro.enums.UserRole;
import com.example.baro.model.User;
import lombok.Getter;

@Getter
public class UserResDto {

    private final Long id;
    private final String username;
    private final String nickname;
    private final UserRole userRole;

    public UserResDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.userRole = user.getUserRole();
    }
}
