package br.com.gateway.aksnes.dto;

import java.math.BigDecimal;

public record DeliveryDetails (
        String recipientName,
        String addressLine1,
        String addressLine2,
        String city,
        String state,
        String postalCode,
        String country,
        String phoneNumber,
        BigDecimal shippingPrice,
        String carrier
) {}