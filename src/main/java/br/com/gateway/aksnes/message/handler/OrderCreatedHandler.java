package br.com.gateway.aksnes.message.handler;

import br.com.gateway.aksnes.domain.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderCreatedHandler {

    private static final String ORDER_CREATED_TOPIC = "orders.created";
    private final KafkaTemplate<String, Object> producer;

    public void process(Order order) {
        try {
            producer.send(ORDER_CREATED_TOPIC, order).get();
        } catch (InterruptedException e) {
            log.error("Erro de sei lá o que");
        } catch (ExecutionException e) {
            log.error("Esse aqui vai pra DLQ");
        }
    }
}
