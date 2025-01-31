package com.example.paymentservice.common.saga.payload;

import com.example.paymentservice.common.enums.ShippingStatus;
import com.example.paymentservice.common.enums.TransactionStatus;
import com.example.paymentservice.common.enums.TransactionType;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SagaBuyItemPayload {

    // orchestrator
    private String itemId;
    private Integer quantity;
    private BigDecimal price;
    private TransactionType transactionType;
    private String failurePoint;

    // payment
    private String transactionId;
    private TransactionStatus transactionStatus;

    // shipping
    private String shippingId;
    private ShippingStatus shippingStatus;
}
