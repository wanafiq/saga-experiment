package com.example.orderservice.saga.service;

import com.example.orderservice.entity.Order;
import com.example.orderservice.saga.enums.OrderStatus;
import com.example.orderservice.saga.payload.SagaCreateOrderPayload;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class SagaOrderService {

    @Transactional
    public SagaCreateOrderPayload createOrder(SagaCreateOrderPayload payload) {
        Order order = new Order();
        order.itemId = UUID.fromString(payload.getItemId());
        order.quantity = payload.getQuantity();
        order.status = OrderStatus.PENDING_PAYMENT.name();
        order.persist();

        payload.setOrderId(order.id.toString());
        payload.setOrderStatus(OrderStatus.valueOf(order.status));

        return payload;
    }

    @Transactional
    public SagaCreateOrderPayload cancelOrder(SagaCreateOrderPayload payload) {
        Order order = Order.findByOrderId(payload.getOrderId());
        order.status = OrderStatus.CANCELLED.name();

        payload.setOrderStatus(OrderStatus.valueOf(order.status));

        return payload;
    }
}
