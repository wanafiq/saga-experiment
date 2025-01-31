package com.example.orchestratorservice.service.dto;

import com.example.orchestratorservice.common.enums.TransactionType;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuyItemDTO {

    private String itemId;
    private Integer quantity;
    private BigDecimal price;
    private TransactionType transactionType;
    private String failurePoint;
}
