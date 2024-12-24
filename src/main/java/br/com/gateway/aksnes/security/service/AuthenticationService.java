package br.com.gateway.aksnes.security.service;

import br.com.gateway.aksnes.security.dto.UserLoginDTO;
import br.com.gateway.aksnes.security.dto.UserRegisterDTO;
import br.com.gateway.aksnes.security.dto.UserResponseDTO;
import br.com.gateway.aksnes.security.persistence.model.RoleEntity;
import br.com.gateway.aksnes.security.persistence.model.Role;
import br.com.gateway.aksnes.security.persistence.model.UserEntity;
import br.com.gateway.aksnes.security.persistence.repository.RoleRepository;
import br.com.gateway.aksnes.security.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApiUserDetailsService userDetailsService;
    private final JwtService jwtService;

    public UserResponseDTO register(UserRegisterDTO user) {
        if (userRepository.findByEmail(user.email()).isPresent())
            throw new BadCredentialsException("User already exists");

        var roles = new HashSet<RoleEntity>();
        roles.add(roleRepository.findByRole(Role.CUSTOMER).get());

        var userEntity = UserEntity.builder()
                .name(user.name())
                .surname(user.surname())
                .email(user.email())
                .cpf(user.cpf())
                .birthday(user.birthday())
                .password(passwordEncoder.encode(user.password()))
                .userRoles(roles)
                .build();
        var savedUser = userRepository.save(userEntity);

        return new UserResponseDTO(userEntity.getName(), jwtService.generateToken(savedUser));
    }

    public UserResponseDTO login(UserLoginDTO user) {
        var fetchedUserData = userDetailsService.loadUserByUsername(user.email());

        if (!passwordEncoder.matches(user.password(), fetchedUserData.getPassword())) {
            throw new BadCredentialsException("Password is wrong");
        }

        var fetchedUsed = userRepository.findByEmail(user.email()).orElseThrow(() -> new NoSuchElementException("User not found"));
        return new UserResponseDTO(fetchedUserData.getUsername(), jwtService.generateToken(fetchedUsed));
    }
}
