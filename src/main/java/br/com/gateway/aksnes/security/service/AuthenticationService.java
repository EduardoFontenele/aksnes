package br.com.gateway.aksnes.security.service;

import br.com.gateway.aksnes.security.dto.UserRequestDto;
import br.com.gateway.aksnes.security.dto.UserResponseDto;
import br.com.gateway.aksnes.security.persistence.model.UserEntity;
import br.com.gateway.aksnes.security.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApiUserDetailsService userDetailsService;
    private final JwtService jwtService;

    public UserResponseDto register(UserRequestDto user) {
        if (userRepository.findByUsername(user.username()).isPresent())
            throw new BadCredentialsException("User already exists");

        UserEntity userEntity = UserEntity.builder()
                .username(user.username())
                .password(passwordEncoder.encode(user.password()))
                .email(passwordEncoder.encode(user.password()))
                .roles(new HashSet<>())
                .build();
        userRepository.save(userEntity);

        return new UserResponseDto(userEntity.getUsername(), jwtService.generateToken(userEntity.getUsername()));
    }

    public UserResponseDto login(UserRequestDto userRequestData) {
        var fetchedUserData = userDetailsService.loadUserByUsername(userRequestData.username());
        if (!passwordEncoder.matches(userRequestData.password(), fetchedUserData.getPassword())) throw new BadCredentialsException("Password is wrong");

        return new UserResponseDto(fetchedUserData.getUsername(), jwtService.generateToken(fetchedUserData.getUsername()));
    }
}
