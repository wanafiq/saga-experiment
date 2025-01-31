package com.example.orchestratorservice.service.dto;

import com.example.orchestratorservice.common.enums.ShippingStatus;
import com.example.orchestratorservice.common.enums.TransactionStatus;
import com.example.orchestratorservice.common.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuyItemResponseDTO {

    private String itemId;
    private TransactionType transactionType;
    private String failurePoint;
    private String transactionId;
    private TransactionStatus transactionStatus;
    private String shippingId;
    private ShippingStatus shippingStatus;
}
