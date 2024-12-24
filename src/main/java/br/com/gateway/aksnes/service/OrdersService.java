package br.com.gateway.aksnes.service;

import br.com.gateway.aksnes.dto.OrderRequest;

import br.com.gateway.aksnes.service.strategy.PaymentStrategyFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final PaymentStrategyFactory paymentStrategy;

    public void processPayment(OrderRequest orderRequest) {
        paymentStrategy.getPaymentStrategy(orderRequest.paymentDetails().paymentMethod())
                .processPayment(orderRequest);
    }
}
