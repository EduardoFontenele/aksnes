package br.com.gateway.aksnes.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderRequest(
        PaymentDetails paymentDetails,
        DeliveryDetails deliveryDetails,
        List<Product> products,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime orderDate,
        BigDecimal totalPrice
        ) { }
