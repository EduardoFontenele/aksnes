package br.com.gateway.aksnes.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record Order (
        PaymentMethod paymentMethod,
        DeliveryDetails deliveryDetails,
        List<Product> products,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")LocalDateTime orderDate,
        BigDecimal totalPrice
        ) {

    public enum PaymentMethod {
        CREDIT_CARD,
        DEBIT_CARD,
        PIX
    }

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

    public record Product(
            UUID id,
            int quantity
    ) {}
}
