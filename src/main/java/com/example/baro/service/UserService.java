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
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public UserResDto signup(UserSignupDto dto) {

        if (userRepository.existByUsername(dto.getUsername())) {
            throw new BadRequestException(ErrorCode.USER_ALREADY_EXISTS);
        }

        Long id = userRepository.findNextId();

        User user = new User(id, dto.getUsername(), passwordEncoder.encode(dto.getPassword()), dto.getNickname());

        return new UserResDto(userRepository.save(user));
    }

    public TokenDto login(UserLoginReqDto dto) {

        User user = userRepository.findByUsername(dto.getUsername()).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BadRequestException(ErrorCode.INVALID_CREDENTIALS);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtProvider.generateToken(user);

        return new TokenDto(accessToken);
    }

    public UserResDto updateRole(Long id, UserRole userRole) {

        userRepository.findById(id).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND));

        User updated = userRepository.updateRoleById(id, userRole);

        return new UserResDto(updated);
    }
}
