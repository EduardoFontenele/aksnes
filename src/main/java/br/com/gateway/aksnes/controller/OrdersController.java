package br.com.gateway.aksnes.controller;

import br.com.gateway.aksnes.domain.Order;
import br.com.gateway.aksnes.message.handler.OrderCreatedHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrdersController {

    private final OrderCreatedHandler orderCreatedHandler;

    @PostMapping("/api/v1/orders")
    public String getOrders(@RequestBody @Valid Order order) {
        orderCreatedHandler.process(order);
        return "legal ein xará";
    }
}
