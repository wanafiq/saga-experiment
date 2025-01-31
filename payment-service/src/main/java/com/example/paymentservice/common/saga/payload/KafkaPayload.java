package com.example.paymentservice.common.saga.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KafkaPayload {

    private int status;
    private String message;
    private String payload;
}
