package com.example.baro.controller;

import com.example.baro.model.dto.UpdateUserRoleReqDto;
import com.example.baro.model.dto.UserResDto;
import com.example.baro.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;

    @PatchMapping("/{id}/roles")
    public ResponseEntity<UserResDto> updateUser(
            @RequestBody UpdateUserRoleReqDto dto,
            @PathVariable Long id
    ) {

        return new ResponseEntity<>(userService.updateRole(id, dto.getUserRole()), HttpStatus.CREATED);
    }
}
