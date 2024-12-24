package br.com.gateway.aksnes.message.handler;

import br.com.gateway.aksnes.dto.OrderRequest;
import br.com.gateway.aksnes.exception.KafkaException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreditCardPaymentHandler {
    private static final String ORDER_CREATED_TOPIC = "orders.payment.credit.card";
    private final KafkaTemplate<String, Object> producer;

    public void process(OrderRequest orderRequest) throws KafkaException {
        producer.send(ORDER_CREATED_TOPIC, orderRequest);
    }
}
