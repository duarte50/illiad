package io.github.duarte50.illiad.controller;

import io.github.duarte50.illiad.dto.AuthenticationDTO;
import io.github.duarte50.illiad.dto.UserDTO;
import io.github.duarte50.illiad.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    // POST /auth/signup
    // POST /auth/login

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationDTO> signup(
            @RequestBody UserDTO userDTO
    ) {
        if (userDTO.getUsername() == null || userDTO.getEmail() == null || userDTO.getPassword() == null ||
                userDTO.getUsername().isBlank() || userDTO.getEmail().isBlank() || userDTO.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body(AuthenticationDTO.builder().build());
        }

        return ResponseEntity.ok(authenticationService.register(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationDTO> login(
            @RequestBody UserDTO userDTO
    ) {
        if (userDTO.getEmail() == null || userDTO.getPassword() == null ||
                userDTO.getEmail().isBlank() || userDTO.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body(AuthenticationDTO.builder().build());
        }

        return ResponseEntity.ok(authenticationService.authenticate(userDTO));
    }
}
