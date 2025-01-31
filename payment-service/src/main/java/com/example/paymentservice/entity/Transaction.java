package com.example.paymentservice.entity;

import com.example.paymentservice.common.enums.TransactionStatus;
import com.example.paymentservice.common.enums.TransactionType;
import com.example.paymentservice.service.dto.TransactionDTO;
import jakarta.persistence.*;
import jakarta.ws.rs.NotFoundException;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "transactions")
@Slf4j
public class Transaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    public String type;
    public BigDecimal amount;
    public String detail;
    public String remark;
    public String refNo;
    public String status;

    public static Transaction findByTransactionId(String transactionId) {
        Transaction transaction = Transaction.findById(UUID.fromString(transactionId));
        if (transaction == null) {
            log.error("Transaction with id {} not found", transactionId);
            throw new NotFoundException();
        }

        return transaction;
    }

    public TransactionDTO toDTO() {
        return TransactionDTO.builder()
            .id(id.toString())
            .type(TransactionType.valueOf(type))
            .amount(amount)
            .detail(detail)
            .remark(remark)
            .refNo(refNo)
            .status(TransactionStatus.valueOf(status))
            .build();
    }
}
