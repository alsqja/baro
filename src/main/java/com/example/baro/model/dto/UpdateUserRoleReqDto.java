package com.example.baro.model.dto;

import com.example.baro.enums.UserRole;
import lombok.Getter;

@Getter
public class UpdateUserRoleReqDto {

    private final UserRole userRole;

    public UpdateUserRoleReqDto(UserRole userRole) {
        this.userRole = userRole;
    }
}
