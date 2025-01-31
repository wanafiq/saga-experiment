package com.example.orderservice.saga;

import com.example.orderservice.saga.payload.SagaCreateOrderPayload;
import com.example.orderservice.saga.service.SagaOrderService;
import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@ApplicationScoped
@Slf4j
public class SagaListener {

    @Inject
    SagaOrderService service;

    @Incoming("create-order")
    @Outgoing("create-order-replies")
    @Blocking
    public SagaCreateOrderPayload createOrderProcessor(SagaCreateOrderPayload payload) {
        printLog("create-order", payload);

        try {
            log.info("Creating new order");

            payload = service.createOrder(payload);

            log.info("Order created");

            return payload;
        } catch (Exception e) {
            printErrorLog("create-order", e);
            throw new RuntimeException(e);
        }
    }

    @Incoming("cancel-order")
    @Outgoing("cancel-order-replies")
    @Blocking
    public SagaCreateOrderPayload cancelOrderProcessor(SagaCreateOrderPayload payload) {
        printLog("cancel-order", payload);

        try {
            log.info("Canceling order");

            payload = service.cancelOrder(payload);

            log.info("Order cancelled");

            return payload;
        } catch (Exception e) {
            printErrorLog("cancel-order", e);
            throw new RuntimeException(e);
        }
    }

    private void printLog(String topicName, Object payload) {
        log.info("Processing {}", topicName);
        log.info("Payload: {}", payload);
    }

    private void printErrorLog(String topicName, Exception e) {
        log.info("Error while processing {}: {}", topicName, e.getMessage());
    }
}
