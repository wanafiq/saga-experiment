package com.example.orchestratorservice.service.saga;

import com.example.orchestratorservice.common.saga.constants.KafkaTopic;
import com.example.orchestratorservice.common.saga.payload.KafkaPayload;
import com.example.orchestratorservice.common.saga.payload.SagaBuyItemPayload;
import com.example.orchestratorservice.common.util.JsonUtil;
import com.example.orchestratorservice.common.util.Utils;
import com.example.orchestratorservice.entity.SagaState;
import com.example.orchestratorservice.service.SagaStateService;
import com.example.orchestratorservice.service.dto.BuyItemDTO;
import com.example.orchestratorservice.service.dto.BuyItemResponseDTO;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.kafka.reply.KafkaRequestReply;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.InternalServerErrorException;
import java.time.Duration;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Channel;

@ApplicationScoped
@Slf4j
public class SagaBuyItemService {

    @ConfigProperty(name = "kafka.reply.timeout", defaultValue = "30000")
    int kafkaReplyTimeout;

    @Inject
    SagaStateService sagaStateService;

    @Inject
    @Channel(KafkaTopic.MAKE_PAYMENT)
    KafkaRequestReply<String, String> makePaymentEmitter;

    @Inject
    @Channel(KafkaTopic.CANCEL_PAYMENT)
    KafkaRequestReply<String, String> cancelPaymentEmitter;

    @Inject
    @Channel(KafkaTopic.DECREASE_STOCK)
    KafkaRequestReply<String, String> decreaseStockEmitter;

    @Inject
    @Channel(KafkaTopic.INCREASE_STOCK)
    KafkaRequestReply<String, String> increaseStockEmitter;

    @Inject
    @Channel(KafkaTopic.SHIP_ORDER)
    KafkaRequestReply<String, String> shipOrderEmitter;

    /*
        Happy flows:  MAKE_PAYMENT -> DECREASE_STOCK -> SHIP_ORDER -> COMPLETE

        Compensation Flows:
        MAKE_PAYMENT -> FAILED
        DECREASE_STOCK -> CANCEL_PAYMENT -> FAILED
        SHIP_ORDER -> INCREASE_STOCK -> CANCEL_PAYMENT -> FAILED
     */

    public enum Step {
        // Normal Steps
        BEGIN,
        MAKE_PAYMENT,
        DECREASE_STOCK,
        SHIP_ORDER,
        COMPLETED,

        // Compensation Steps
        INCREASE_STOCK,
        CANCEL_PAYMENT,
        FAILED,
    }

    @Transactional
    public BuyItemResponseDTO execute(BuyItemDTO dto) {
        log.info("\nStarting saga transaction");

        var stateId = UUID.randomUUID().toString();

        SagaBuyItemPayload payload = SagaBuyItemPayload.builder()
            .itemId(dto.getItemId())
            .quantity(dto.getQuantity())
            .price(dto.getPrice())
            .transactionType(dto.getTransactionType())
            .failurePoint(dto.getFailurePoint())
            .build();

        SagaState state = sagaStateService.begin(stateId, Step.MAKE_PAYMENT.name(), JsonUtil.toJson(payload));

        Step currentStep = Step.valueOf(state.step);

        while (!currentStep.equals(Step.COMPLETED)) {
            currentStep = nextStep(stateId, currentStep);

            if (currentStep.equals(Step.FAILED)) {
                log.error("Saga transaction failed");

                throw new InternalServerErrorException();
            }
        }

        SagaState finalState = sagaStateService.getState(stateId);

        payload = JsonUtil.toObject(finalState.payload, SagaBuyItemPayload.class);

        log.info("Saga transaction completed");

        return payload.toDTO();
    }

    private Step nextStep(String stateId, Step currentStep) {
        switch (currentStep) {
            case MAKE_PAYMENT:
                return executeStep(makePaymentEmitter, stateId, Step.MAKE_PAYMENT, Step.DECREASE_STOCK, Step.FAILED);
            case DECREASE_STOCK:
                return executeStep(decreaseStockEmitter, stateId, Step.DECREASE_STOCK, Step.SHIP_ORDER, Step.CANCEL_PAYMENT);
            case SHIP_ORDER:
                return executeStep(shipOrderEmitter, stateId, Step.SHIP_ORDER, Step.COMPLETED, Step.INCREASE_STOCK);
            case CANCEL_PAYMENT:
                return executeStep(cancelPaymentEmitter, stateId, Step.CANCEL_PAYMENT, Step.FAILED, Step.FAILED);
            case INCREASE_STOCK:
                return executeStep(increaseStockEmitter, stateId, Step.INCREASE_STOCK, Step.CANCEL_PAYMENT, Step.FAILED);
            default:
                log.error("Unknown step: {}", currentStep);

                SagaState state = sagaStateService.updateStep(stateId, Step.FAILED.name(), String.format("Unknown step: %s", currentStep));

                return Step.valueOf(state.step);
        }
    }

    private Step executeStep(KafkaRequestReply<String, String> emitter, String stateId, Step currentStep, Step nextStep, Step failedStep) {
        try {
            SagaState state = sagaStateService.getState(stateId);

            Utils.printLog(currentStep.name(), state.payload, false);

            String kafkaResponse = sendKafkaRequest(emitter, state.payload);
            KafkaPayload kafkaPayload = JsonUtil.toObject(kafkaResponse, KafkaPayload.class);

            if (kafkaPayload.getStatus() != 200) {
                Utils.printErrorLog(currentStep.name(), kafkaPayload.getPayload());

                state = sagaStateService.updateStep(stateId, failedStep.name(), kafkaPayload.getPayload());
                return Step.valueOf(state.step);
            } else {
                Utils.printLog(currentStep.name(), kafkaPayload.getPayload(), true);

                state = sagaStateService.updateStep(stateId, nextStep.name(), kafkaPayload.getPayload());
                return Step.valueOf(state.step);
            }
        } catch (Exception e) {
            Utils.printErrorLog(currentStep.name(), e);
            throw new InternalServerErrorException();
        }
    }

    private String sendKafkaRequest(KafkaRequestReply<String, String> emitter, String payload) {
        Duration timeout = Duration.ofMillis(kafkaReplyTimeout);

        try {
            KafkaPayload kafkaPayload = KafkaPayload.builder().payload(payload).build();

            Uni<String> uni = emitter
                .request(JsonUtil.toJson(kafkaPayload))
                .ifNoItem()
                .after(timeout)
                .fail()
                .onFailure()
                .transform(error -> {
                    log.error("Request timed out");
                    return new InternalServerErrorException();
                });

            return uni.await().atMost(timeout);
        } catch (Exception e) {
            log.error("Failed to send kafka request: {}", e.getMessage());
            throw new InternalServerErrorException();
        }
    }
}
