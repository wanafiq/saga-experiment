package com.example.paymentservice.service;

import com.example.paymentservice.common.enums.TransactionStatus;
import com.example.paymentservice.entity.Transaction;
import com.example.paymentservice.service.dto.CreateTransactionDTO;
import com.example.paymentservice.service.dto.TransactionDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class TransactionService {

    @Transactional
    public TransactionDTO createTransaction(CreateTransactionDTO dto) {
        Transaction transaction = new Transaction();
        transaction.type = dto.getType().name();
        transaction.amount = dto.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));

        transaction.detail = String.format(
            "%s, itemId: %s, price: %s, quantity: %s",
            dto.getType().name(),
            dto.getItemId(),
            dto.getPrice(),
            dto.getQuantity()
        );

        transaction.status = TransactionStatus.PENDING.name();
        transaction.persist();

        //        simulateLongRunningTask(Duration.ofSeconds(10).toMillis());

        transaction.status = TransactionStatus.SUCCESS.name();
        transaction.refNo = UUID.randomUUID().toString();
        transaction.remark = "Payment successful";

        return transaction.toDTO();
    }

    public List<TransactionDTO> getAll() {
        List<Transaction> transactions = Transaction.listAll();

        return transactions.stream().map(Transaction::toDTO).toList();
    }

    public TransactionDTO getById(String id) {
        Transaction transaction = Transaction.findByTransactionId(id);

        return transaction.toDTO();
    }

    @Transactional
    public TransactionDTO updateTransactionStatus(String id, TransactionStatus status) {
        Transaction transaction = Transaction.findByTransactionId(id);

        simulateLongRunningTask(Duration.ofSeconds(10).toMillis());

        transaction.status = status.name();
        transaction.remark = "Payment cancelled";

        return transaction.toDTO();
    }

    private void simulateLongRunningTask(long milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException ignored) {}
    }
}
