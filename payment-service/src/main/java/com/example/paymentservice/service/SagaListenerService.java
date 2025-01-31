package com.example.paymentservice.service;

import com.example.paymentservice.common.enums.TransactionStatus;
import com.example.paymentservice.common.saga.constants.KafkaTopic;
import com.example.paymentservice.common.saga.payload.KafkaPayload;
import com.example.paymentservice.common.saga.payload.SagaBuyItemPayload;
import com.example.paymentservice.common.util.JsonUtil;
import com.example.paymentservice.common.util.Utils;
import com.example.paymentservice.service.dto.CreateTransactionDTO;
import com.example.paymentservice.service.dto.TransactionDTO;
import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

@ApplicationScoped
@Slf4j
public class SagaListenerService {

    @Inject
    TransactionService transactionService;

    @Incoming(KafkaTopic.MAKE_PAYMENT)
    @Outgoing(KafkaTopic.MAKE_PAYMENT_REPLY)
    @Blocking
    public KafkaPayload makePaymentProcessor(KafkaPayload kafkaPayload) {
        try {
            SagaBuyItemPayload payload = JsonUtil.toObject(kafkaPayload.getPayload(), SagaBuyItemPayload.class);

            Utils.printLog(KafkaTopic.MAKE_PAYMENT, payload, false);

            if (payload.getFailurePoint().equals("payment")) {
                String error = "Simulating failure on payment-service";

                Utils.printErrorLog(KafkaTopic.MAKE_PAYMENT, error);

                kafkaPayload.setStatus(500);
                kafkaPayload.setMessage(error);

                return kafkaPayload;
            }

            CreateTransactionDTO createDTO = CreateTransactionDTO.builder()
                .type(payload.getTransactionType())
                .itemId(payload.getItemId())
                .quantity(payload.getQuantity())
                .price(payload.getPrice())
                .build();

            TransactionDTO dto = transactionService.createTransaction(createDTO);

            payload.setTransactionId(dto.getId());
            payload.setTransactionStatus(dto.getStatus());

            kafkaPayload.setStatus(200);
            kafkaPayload.setMessage("OK");
            kafkaPayload.setPayload(JsonUtil.toJson(payload));

            Utils.printLog(KafkaTopic.MAKE_PAYMENT, payload, true);

            return kafkaPayload;
        } catch (Exception e) {
            Utils.printErrorLog(KafkaTopic.MAKE_PAYMENT, e);

            kafkaPayload.setStatus(500);
            kafkaPayload.setMessage(e.getMessage());

            return kafkaPayload;
        }
    }

    @Incoming(KafkaTopic.CANCEL_PAYMENT)
    @Outgoing(KafkaTopic.CANCEL_PAYMENT_REPLY)
    @Blocking
    public KafkaPayload refundPaymentProcessor(KafkaPayload kafkaPayload) {
        try {
            SagaBuyItemPayload payload = JsonUtil.toObject(kafkaPayload.getPayload(), SagaBuyItemPayload.class);

            Utils.printLog(KafkaTopic.CANCEL_PAYMENT, payload, false);

            TransactionDTO dto = transactionService.updateTransactionStatus(payload.getTransactionId(), TransactionStatus.CANCELLED);

            payload.setTransactionStatus(dto.getStatus());

            kafkaPayload.setStatus(200);
            kafkaPayload.setMessage("OK");
            kafkaPayload.setPayload(JsonUtil.toJson(payload));

            Utils.printLog(KafkaTopic.CANCEL_PAYMENT, payload, true);

            return kafkaPayload;
        } catch (Exception e) {
            Utils.printErrorLog(KafkaTopic.CANCEL_PAYMENT, e);

            kafkaPayload.setStatus(500);
            kafkaPayload.setMessage(e.getMessage());

            return kafkaPayload;
        }
    }
}
