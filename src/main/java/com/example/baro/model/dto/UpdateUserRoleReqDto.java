package com.example.baro.model.dto;

import com.example.baro.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class UpdateUserRoleReqDto {

    private final UserRole userRole;

    @JsonCreator
    public UpdateUserRoleReqDto(UserRole userRole) {
        this.userRole = userRole;
    }
}
