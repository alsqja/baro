package com.example.baro.controller;

import com.example.baro.enums.UserRole;
import com.example.baro.model.User;
import com.example.baro.repository.UserRepository;
import com.example.baro.service.UserService;
import com.example.baro.util.JwtProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    private String adminJwtToken;
    private String userJwtToken;
    private String expiredJwtToken;

    @BeforeEach
    void setUp() {
        adminJwtToken = createJwt("admin", UserRole.ADMIN, 3600L);
        userJwtToken = createJwt("user", UserRole.USER, 3600L);
        expiredJwtToken = createJwt("expired", UserRole.ADMIN, 0L);

        User user = new User(1L, "user", "test", "test");
        User admin = new User(2L, "admin", "admin", "admin");

        userRepository.save(user);
        userRepository.save(admin);
        userRepository.updateRoleById(admin.getId(), UserRole.ADMIN);
    }

    @Test
    @DisplayName("update success")
    void updateUser() throws Exception {

        mockMvc.perform(patch("/admin/users/1/roles")
                        .header("Authorization", "Bearer " + adminJwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userRole\": \"ADMIN\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userRole").value("ADMIN"));
    }

    @Test
    @DisplayName("update fail no jwt")
    void updateUserFailNoJwt() throws Exception {

        mockMvc.perform(patch("/admin/users/1/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userRole\": \"ADMIN\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error.code").value("INVALID_TOKEN"));
    }

    @Test
    @DisplayName("update fail wrong jwt")
    void updateUserFailWrongJwt() throws Exception {

        mockMvc.perform(patch("/admin/users/1/roles")
                        .header("Authorization", "Bearer " + adminJwtToken + "wrong")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userRole\": \"ADMIN\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error.code").value("INVALID_TOKEN"));
    }

    @Test
    @DisplayName("update fail expired jwt")
    void updateUserFailExpiredJwt() throws Exception {

        mockMvc.perform(patch("/admin/users/1/roles")
                        .header("Authorization", "Bearer " + expiredJwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userRole\": \"ADMIN\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.error.code").value("INVALID_TOKEN"));
    }

    @Test
    @DisplayName("update fail access denied")
    void updateUserFailAccessDenied() throws Exception {

        mockMvc.perform(patch("/admin/users/1/roles")
                        .header("Authorization", "Bearer " + userJwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userRole\": \"ADMIN\"}"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error.code").value("ACCESS_DENIED"));
    }

    private String createJwt(String username, UserRole userRole, Long expiry) {
        String secret = "036c4fe3ec667532545b9e8fa7e2a98a22f439dff102623c097715060e2da68c";
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + expiry))
                .claim("role", userRole)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)), Jwts.SIG.HS256)
                .compact();
    }
}