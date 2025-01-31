package com.example.paymentservice.service.dto;

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
public class TransactionDTO {

    private String id;
    private TransactionType type;
    private BigDecimal amount;
    private String detail;
    private String remark;
    private String refNo;
    private TransactionStatus status;
}
