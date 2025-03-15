package com.example.baro.model;

import com.example.baro.enums.UserRole;
import lombok.Getter;

@Getter
public class User {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private UserRole userRole;

    public User(Long id, String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.id = id;
        this.userRole = UserRole.USER;
    }

    public void updateRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
