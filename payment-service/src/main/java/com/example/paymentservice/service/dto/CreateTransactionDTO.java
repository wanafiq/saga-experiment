package com.example.paymentservice.service.dto;

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
public class CreateTransactionDTO {

    private TransactionType type;
    private String itemId;
    private Integer quantity;
    private BigDecimal price;
}
