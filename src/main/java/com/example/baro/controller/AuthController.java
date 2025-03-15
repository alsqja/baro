package com.example.baro.controller;

import com.example.baro.model.dto.TokenDto;
import com.example.baro.model.dto.UserLoginReqDto;
import com.example.baro.model.dto.UserResDto;
import com.example.baro.model.dto.UserSignupDto;
import com.example.baro.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResDto> signup(@Valid @RequestBody UserSignupDto dto) {

        return new ResponseEntity<>(userService.signup(dto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody UserLoginReqDto dto) {

        return new ResponseEntity<>(userService.login(dto), HttpStatus.OK);
    }
}
