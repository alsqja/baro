package com.example.baro.service;

import com.example.baro.model.User;
import com.example.baro.model.dto.UserResDto;
import com.example.baro.model.dto.UserSignupDto;
import com.example.baro.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResDto signup(UserSignupDto dto) {

        if (userRepository.existByUsername(dto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is already in use");
        }

        Long id = userRepository.findNextId();

        User user = new User(id, dto.getUsername(), passwordEncoder.encode(dto.getPassword()), dto.getNickname());

        return new UserResDto(userRepository.save(user));
    }
}
