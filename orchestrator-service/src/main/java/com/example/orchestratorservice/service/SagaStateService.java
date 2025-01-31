package com.example.orchestratorservice.service;

import com.example.orchestratorservice.entity.SagaState;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class SagaStateService {

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public SagaState begin(String stateId, String initialStep, String payload) {
        try {
            SagaState state = new SagaState();
            state.id = stateId;
            state.step = initialStep;
            state.payload = payload;
            state.persistAndFlush();

            return state;
        } catch (Exception e) {
            log.error("Failed to save saga state");
            throw new InternalServerErrorException();
        }
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public SagaState getState(String stateId) {
        SagaState state = SagaState.findById(stateId);
        if (state == null) {
            log.error("Saga state with id {} not found", stateId);
            throw new NotFoundException();
        }

        return state;
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public SagaState updateStep(String stateId, String step, String payload) {
        SagaState state = SagaState.findById(stateId);
        if (state != null) {
            state.step = step;
            state.payload = payload;

            return state;
        }

        return null;
    }
}
