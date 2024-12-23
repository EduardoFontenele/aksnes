package br.com.gateway.aksnes.security.controller;

import br.com.gateway.aksnes.security.dto.UserRequestDto;
import br.com.gateway.aksnes.security.dto.UserResponseDto;
import br.com.gateway.aksnes.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody UserRequestDto user) {
        return authenticationService.register(user);
    }

    @PostMapping("/login")
    public UserResponseDto login(@RequestBody UserRequestDto user) {
        return authenticationService.login(user);
    }
}