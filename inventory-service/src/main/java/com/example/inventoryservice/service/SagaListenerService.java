package com.example.inventoryservice.service;

import com.example.inventoryservice.common.saga.constants.KafkaTopic;
import com.example.inventoryservice.common.saga.payload.KafkaPayload;
import com.example.inventoryservice.common.saga.payload.SagaBuyItemPayload;
import com.example.inventoryservice.common.util.JsonUtil;
import com.example.inventoryservice.common.util.Utils;
import com.example.inventoryservice.service.dto.ItemDTO;
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
    ItemService itemService;

    @Incoming(KafkaTopic.DECREASE_STOCK)
    @Outgoing(KafkaTopic.DECREASE_STOCK_REPLY)
    @Blocking
    public KafkaPayload deductStockProcessor(KafkaPayload kafkaPayload) {
        try {
            SagaBuyItemPayload payload = JsonUtil.toObject(kafkaPayload.getPayload(), SagaBuyItemPayload.class);

            Utils.printLog(KafkaTopic.DECREASE_STOCK, payload, false);

            if (payload.getFailurePoint().equals("inventory")) {
                String error = "Simulating failure on inventory-service";

                Utils.printErrorLog(KafkaTopic.DECREASE_STOCK, error);

                kafkaPayload.setStatus(500);
                kafkaPayload.setMessage(error);

                return kafkaPayload;
            }

            ItemDTO dto = itemService.decrease(payload.getItemId(), payload.getQuantity());

            kafkaPayload.setStatus(200);
            kafkaPayload.setMessage("OK");
            kafkaPayload.setPayload(JsonUtil.toJson(payload));

            Utils.printLog(KafkaTopic.DECREASE_STOCK, payload, true);

            return kafkaPayload;
        } catch (Exception e) {
            Utils.printErrorLog(KafkaTopic.DECREASE_STOCK, e);

            kafkaPayload.setStatus(500);
            kafkaPayload.setMessage(e.getMessage());

            return kafkaPayload;
        }
    }

    @Incoming(KafkaTopic.INCREASE_STOCK)
    @Outgoing(KafkaTopic.INCREASE_STOCK_REPLY)
    @Blocking
    public KafkaPayload restoreStockProcessor(KafkaPayload kafkaPayload) {
        try {
            SagaBuyItemPayload payload = JsonUtil.toObject(kafkaPayload.getPayload(), SagaBuyItemPayload.class);

            Utils.printLog(KafkaTopic.INCREASE_STOCK, payload, false);

            ItemDTO dto = itemService.increase(payload.getItemId(), payload.getQuantity());

            kafkaPayload.setStatus(200);
            kafkaPayload.setMessage("OK");
            kafkaPayload.setPayload(JsonUtil.toJson(payload));

            Utils.printLog(KafkaTopic.INCREASE_STOCK, payload, true);

            return kafkaPayload;
        } catch (Exception e) {
            Utils.printErrorLog(KafkaTopic.INCREASE_STOCK, e);

            kafkaPayload.setStatus(500);
            kafkaPayload.setMessage(e.getMessage());

            return kafkaPayload;
        }
    }
}
