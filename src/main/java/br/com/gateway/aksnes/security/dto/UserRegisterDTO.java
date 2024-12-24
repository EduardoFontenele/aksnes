package br.com.gateway.aksnes.security.dto;

import java.time.LocalDate;

public record UserRegisterDTO(String name, String surname, LocalDate birthday, String cpf, String password, String email) {}
