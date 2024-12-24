package br.com.gateway.aksnes.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        DeliveryDetails deliveryDetails,
        PaymentDetails paymentDetails,
        BigDecimal totalPrice,
        LocalDateTime orderDate,
        List<Product> products
) {
}
