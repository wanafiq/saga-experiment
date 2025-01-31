package com.example.shippingservice.service;

import com.example.shippingservice.common.saga.constants.KafkaTopic;
import com.example.shippingservice.common.saga.payload.KafkaPayload;
import com.example.shippingservice.common.saga.payload.SagaBuyItemPayload;
import com.example.shippingservice.common.util.JsonUtil;
import com.example.shippingservice.common.util.Utils;
import com.example.shippingservice.service.dto.CreateShippingDTO;
import com.example.shippingservice.service.dto.ShippingDTO;
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
    ShippingService shippingService;

    @Incoming(KafkaTopic.SHIP_ORDER)
    @Outgoing(KafkaTopic.SHIP_ORDER_REPLY)
    @Blocking
    public KafkaPayload shipOrderProcessor(KafkaPayload kafkaPayload) {
        try {
            SagaBuyItemPayload payload = JsonUtil.toObject(kafkaPayload.getPayload(), SagaBuyItemPayload.class);

            Utils.printLog(KafkaTopic.SHIP_ORDER, payload, false);

            if (payload.getFailurePoint().equals("shipping")) {
                String error = "Simulating failure on shipping-service";

                Utils.printErrorLog(KafkaTopic.SHIP_ORDER, error);

                kafkaPayload.setStatus(500);
                kafkaPayload.setMessage(error);

                return kafkaPayload;
            }

            CreateShippingDTO createDTO = CreateShippingDTO.builder()
                .itemId(payload.getItemId())
                .transactionId(payload.getTransactionId())
                .build();

            ShippingDTO dto = shippingService.create(createDTO);

            payload.setShippingId(dto.getId());
            payload.setShippingStatus(dto.getStatus());

            kafkaPayload.setStatus(200);
            kafkaPayload.setMessage("OK");
            kafkaPayload.setPayload(JsonUtil.toJson(payload));

            Utils.printLog(KafkaTopic.SHIP_ORDER, payload, true);

            return kafkaPayload;
        } catch (Exception e) {
            Utils.printErrorLog(KafkaTopic.SHIP_ORDER, e);

            kafkaPayload.setStatus(500);
            kafkaPayload.setMessage(e.getMessage());

            return kafkaPayload;
        }
    }
}
