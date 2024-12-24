package br.com.gateway.aksnes.service.strategy;

import br.com.gateway.aksnes.dto.PaymentDetails.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentStrategyFactory {
    private final BeanFactory beanFactory;

    public PaymentStrategy getPaymentStrategy(PaymentMethod paymentMethod) {
        return switch (paymentMethod) {
            case PIX -> beanFactory.getBean(PixPaymentStrategy.class);
            case CREDIT_CARD -> beanFactory.getBean(CreditCardPaymentStrategy.class);
            default -> null;
        };
    }
}
