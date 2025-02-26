package io.github.duarte50.illiad.service.impl;

import io.github.duarte50.illiad.dto.AuthenticationDTO;
import io.github.duarte50.illiad.dto.UserDTO;
import io.github.duarte50.illiad.entity.User;
import io.github.duarte50.illiad.repository.UserRepository;
import io.github.duarte50.illiad.service.AuthenticationService;
import io.github.duarte50.illiad.service.JwtService;
import io.github.duarte50.illiad.utils.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationDTO register(UserDTO userDTO) {
        var user = User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        var extraClaims = new HashMap<String, Object>();
        extraClaims.put("username", user.getChannelName());
        var token = jwtService.generateToken(extraClaims, user);

        return AuthenticationDTO.builder().token(token).build();
    }

    @Override
    public AuthenticationDTO authenticate(UserDTO userDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));

        var user = userRepository.findByEmail(userDTO.getEmail()).orElseThrow();
        var extraClaims = new HashMap<String, Object>();
        extraClaims.put("username", user.getChannelName());
        var token = jwtService.generateToken(extraClaims, user);

        return AuthenticationDTO.builder().token(token).build();
    }
}
