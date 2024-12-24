package br.com.gateway.aksnes.security.dto;

public record UserRequestDto(String username, String password, String email) {
}
