package br.com.gateway.aksnes.dto;

import java.util.UUID;

public record Product(
        UUID id,
        int quantity
) {}
