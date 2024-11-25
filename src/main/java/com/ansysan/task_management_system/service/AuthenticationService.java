package com.ansysan.task_management_system.service;

import com.ansysan.task_management_system.dto.JwtResponseDto;
import com.ansysan.task_management_system.dto.UserCreateDto;
import com.ansysan.task_management_system.entity.User;
import com.ansysan.task_management_system.entity.UserDetailsImpl;
import com.ansysan.task_management_system.entity.enums.Role;
import com.ansysan.task_management_system.exception.UserException;
import com.ansysan.task_management_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public JwtResponseDto register(UserCreateDto request) {
        if(userRepository.findUserByEmail(request.getEmail()).isPresent()){
            throw new UserException(request.getEmail());
        }
        UserDetailsImpl userDetails =
                UserDetailsImpl.builder()
                        .user(User.builder()
                                .username(request.getUsername())
                                .email(request.getEmail())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .role(Role.USER)
                                .build())
                        .build();
        userRepository.save(userDetails.getUser());
        String jwtToken = jwtService.generateToken((Authentication) userDetails);
        log.debug("JWT Token registration: {}", jwtToken);
        return new JwtResponseDto(jwtToken);
    }

    public JwtResponseDto authenticate(UserCreateDto request) {

        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new UserException(request.getEmail()));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        String jwtToken = jwtService.generateToken((Authentication) UserDetailsImpl.builder()
                .user(user)
                .build());
        log.debug("JWT Token authentication: {}", jwtToken);
        return JwtResponseDto.builder()
                .tokenType(jwtToken)
                .build();
    }
}