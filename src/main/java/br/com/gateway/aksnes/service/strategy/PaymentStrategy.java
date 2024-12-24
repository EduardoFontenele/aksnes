package br.com.gateway.aksnes.service.strategy;

import br.com.gateway.aksnes.dto.OrderRequest;

public interface PaymentStrategy {
    void processPayment(OrderRequest orderRequest);
}
