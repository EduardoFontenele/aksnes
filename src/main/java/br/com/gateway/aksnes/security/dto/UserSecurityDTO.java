package br.com.gateway.aksnes.security.dto;

import br.com.gateway.aksnes.security.persistence.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


@RequiredArgsConstructor
public final class UserSecurityDTO implements UserDetails {
    private final UserEntity userEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userEntity.getUserRoles()
                .stream()
                .map(permissionEntity -> new SimpleGrantedAuthority(permissionEntity.getRole().name()))
                .toList();
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
