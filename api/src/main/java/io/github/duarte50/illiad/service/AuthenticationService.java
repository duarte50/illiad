package io.github.duarte50.illiad.service;

import io.github.duarte50.illiad.dto.AuthenticationDTO;
import io.github.duarte50.illiad.dto.UserDTO;

public interface AuthenticationService {
    AuthenticationDTO register(UserDTO userDTO);
    AuthenticationDTO authenticate(UserDTO userDTO);
}
