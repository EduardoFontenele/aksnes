package br.com.gateway.aksnes.service.strategy;

import br.com.gateway.aksnes.dto.OrderRequest;
import br.com.gateway.aksnes.message.handler.PixPaymentHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PixPaymentStrategy implements PaymentStrategy{
    private final PixPaymentHandler pixPaymentHandler;

    @Override
    public void processPayment(OrderRequest orderRequest) {
        pixPaymentHandler.process(orderRequest);
    }
}
