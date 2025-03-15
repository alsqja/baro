package com.example.baro.config;

import com.example.baro.enums.UserRole;
import com.example.baro.model.User;
import com.example.baro.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        User user = new User(1L, "admin", passwordEncoder.encode("admin"), "admin");
        userRepository.save(user);

        userRepository.updateRoleById(1L, UserRole.ADMIN);
    }
}
