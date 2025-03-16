package com.example.baro.service;

import com.example.baro.enums.UserRole;
import com.example.baro.error.ErrorCode;
import com.example.baro.error.exception.BadRequestException;
import com.example.baro.error.exception.NotFoundException;
import com.example.baro.model.User;
import com.example.baro.model.dto.TokenDto;
import com.example.baro.model.dto.UserLoginReqDto;
import com.example.baro.model.dto.UserResDto;
import com.example.baro.model.dto.UserSignupDto;
import com.example.baro.repository.UserRepository;
import com.example.baro.util.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserService userService;

    private final String TEST_USERNAME = "test_username";
    private final String TEST_PASSWORD = "test_password";
    private final String TEST_NICKNAME = "test_nickname";

    @Test
    @DisplayName("signup success")
    void signup() {
        UserSignupDto dto = new UserSignupDto(TEST_USERNAME, TEST_PASSWORD, TEST_NICKNAME);
        User mockUser = new User(1L, TEST_USERNAME, TEST_PASSWORD, TEST_NICKNAME);

        when(userRepository.existByUsername(TEST_USERNAME)).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        UserResDto result = userService.signup(dto);

        assertEquals(1L, result.getId());
        assertEquals(TEST_USERNAME, result.getUsername());
        assertEquals(TEST_NICKNAME, result.getNickname());
    }

    @Test
    @DisplayName("signup fail case exist username")
    void signupFailUsername() {
        UserSignupDto dto = new UserSignupDto(TEST_USERNAME, TEST_PASSWORD, TEST_NICKNAME);

        when(userRepository.existByUsername(TEST_USERNAME)).thenReturn(true);

        BadRequestException e = assertThrows(BadRequestException.class, () -> userService.signup(dto));

        assertEquals(ErrorCode.USER_ALREADY_EXISTS, e.getErrorCode());
        assertEquals("이미 가입된 사용자입니다.", e.getErrorCode().getMessage());
    }

    @Test
    @DisplayName("login success")
    void login() {
        UserLoginReqDto dto = new UserLoginReqDto(TEST_USERNAME, TEST_PASSWORD);
        User mockUser = new User(1L, TEST_USERNAME, TEST_PASSWORD, TEST_NICKNAME);

        when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(TEST_PASSWORD, dto.getPassword())).thenReturn(true);
        when(jwtProvider.generateToken(mockUser)).thenReturn("token");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));

        TokenDto result = userService.login(dto);

        assertEquals("token", result.getToken());
    }

    @Test
    @DisplayName("login fail invalid username")
    void loginFailInvalidUsername() {
        UserLoginReqDto dto = new UserLoginReqDto(TEST_USERNAME, TEST_PASSWORD);

        when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.empty());

        NotFoundException e = assertThrows(NotFoundException.class, () -> userService.login(dto));

        assertEquals(ErrorCode.NOT_FOUND, e.getErrorCode());
        assertEquals("존재하지 않는 유저입니다.", e.getErrorCode().getMessage());
    }

    @Test
    @DisplayName("login fail invalid password")
    void loginFailInvalidPassword() {
        UserLoginReqDto dto = new UserLoginReqDto(TEST_USERNAME, TEST_PASSWORD);
        User mockUser = new User(1L, TEST_USERNAME, TEST_PASSWORD, TEST_NICKNAME);

        when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(TEST_PASSWORD, dto.getPassword())).thenReturn(false);

        BadRequestException e = assertThrows(BadRequestException.class, () -> userService.login(dto));

        assertEquals(ErrorCode.INVALID_CREDENTIALS, e.getErrorCode());
        assertEquals("아이디 또는 비밀번호가 올바르지 않습니다.", e.getErrorCode().getMessage());
    }

    @Test
    @DisplayName("update user role success")
    void updateUserRole() {
        User mockUser = new User(1L, TEST_USERNAME, TEST_PASSWORD, TEST_NICKNAME);
        User updatedUser = new User(1L, TEST_USERNAME, TEST_PASSWORD, TEST_NICKNAME);
        updatedUser.updateRole(UserRole.ADMIN);

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(userRepository.updateRoleById(1L, UserRole.ADMIN)).thenReturn(updatedUser);

        UserResDto result = userService.updateRole(1L, UserRole.ADMIN);

        assertEquals(1L, result.getId());
        assertEquals(TEST_USERNAME, result.getUsername());
        assertEquals(UserRole.ADMIN, result.getUserRole());
    }

    @Test
    @DisplayName("update fail not found")
    void updateUserRoleFail() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException e = assertThrows(NotFoundException.class, () -> userService.updateRole(1L, UserRole.ADMIN));

        assertEquals(ErrorCode.NOT_FOUND, e.getErrorCode());
    }
}