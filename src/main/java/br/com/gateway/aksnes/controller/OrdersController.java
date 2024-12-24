package br.com.gateway.aksnes.controller;

import br.com.gateway.aksnes.dto.OrderRequest;
import br.com.gateway.aksnes.dto.OrderResponse;
import br.com.gateway.aksnes.service.OrdersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    @PostMapping("/api/v1/orders")
    @PreAuthorize("")
    public OrderResponse getOrders(@RequestBody @Valid OrderRequest orderRequest) {
        ordersService.processPayment(orderRequest);
        return new OrderResponse(orderRequest.deliveryDetails(), orderRequest.paymentDetails(), orderRequest.totalPrice(), orderRequest.orderDate(), orderRequest.products());
    }
}
