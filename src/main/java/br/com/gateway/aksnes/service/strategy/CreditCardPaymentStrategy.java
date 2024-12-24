package br.com.gateway.aksnes.service.strategy;

import br.com.gateway.aksnes.dto.OrderRequest;
import br.com.gateway.aksnes.message.handler.CreditCardPaymentHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditCardPaymentStrategy implements PaymentStrategy {
    private final CreditCardPaymentHandler handler;

    @Override
    public void processPayment(OrderRequest orderRequest) {
        handler.process(orderRequest);
    }
}
