package com.example.orchestratorservice.common.saga.payload;

import com.example.orchestratorservice.common.enums.ShippingStatus;
import com.example.orchestratorservice.common.enums.TransactionStatus;
import com.example.orchestratorservice.common.enums.TransactionType;
import com.example.orchestratorservice.service.dto.BuyItemResponseDTO;
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

    public BuyItemResponseDTO toDTO() {
        return BuyItemResponseDTO.builder()
            .itemId(itemId)
            .transactionType(transactionType)
            .transactionId(transactionId)
            .transactionStatus(transactionStatus)
            .shippingId(shippingId)
            .shippingStatus(shippingStatus)
            .build();
    }
}
