package br.com.gateway.aksnes.security.dto;

import org.springframework.http.HttpStatus;

public record ErrorResponseDto(HttpStatus httpStatus, String message) {
}
