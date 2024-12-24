package br.com.gateway.aksnes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

public record PaymentDetails(
        PaymentMethod paymentMethod,
        @JsonInclude(JsonInclude.Include.NON_EMPTY) CardInfo cardInfo,
        @JsonInclude(JsonInclude.Include.NON_EMPTY) PixInfo pixInfo
) {
    public enum PaymentMethod {
        CREDIT_CARD,
        DEBIT_CARD,
        PIX
    }

    public record CardInfo(
            String cardNumber,
            String cardHolderName,
            String expirationDate,
            String cvv
    ) {}

    public record PixInfo(
            String key,
            String keyType,
            String qrCodeUrl
    ) {}
}
