package br.com.gateway.aksnes.security.dto;

import org.springframework.http.HttpStatus;

public record ErrorResponseDTO(HttpStatus httpStatus, String message) {
}
