package com.example.orderservice.entity;

import com.example.orderservice.saga.enums.OrderStatus;
import com.example.orderservice.saga.payload.SagaCreateOrderPayload;
import jakarta.persistence.*;
import jakarta.ws.rs.NotFoundException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "orders")
@Slf4j
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    public UUID itemId;
    public Integer quantity;
    public String status;

    public static Order findByOrderId(String orderId) {
        Order order = Order.findById(orderId);
        if (order == null) {
            log.error("Order with id {} not found", orderId);
            throw new NotFoundException();
        }

        return order;
    }
}
