package com.example.baro.controller;

import com.example.baro.model.dto.UpdateUserRoleReqDto;
import com.example.baro.model.dto.UserResDto;
import com.example.baro.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Admin User API", description = "관리자 전용 관리자 권한 부여 API")
@SecurityRequirement(name = "JWT")
public class AdminUserController {

    private final UserService userService;

    @PatchMapping("/{id}/roles")
    @Operation(summary = "사용자 역할 변경", description = "특정 사용자의 역할을 변경합니다.")
    public ResponseEntity<UserResDto> updateUser(
            @Parameter(description = "변경할 역할", example = "ADMIN") @RequestBody UpdateUserRoleReqDto dto,
            @Parameter(description = "사용자 ID", example = "1") @PathVariable Long id
    ) {

        return new ResponseEntity<>(userService.updateRole(id, dto.getUserRole()), HttpStatus.CREATED);
    }
}
