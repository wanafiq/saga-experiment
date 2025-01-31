package com.example.orderservice.saga.payload;

import com.example.orderservice.saga.enums.OrderStatus;
import com.example.orderservice.saga.enums.PaymentType;
import com.example.orderservice.saga.enums.ShippingStatus;
import com.example.orderservice.saga.enums.TransactionStatus;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SagaCreateOrderPayload {

    // order
    private String orderId;
    private String itemId;
    private Integer quantity;
    private BigDecimal price;
    private OrderStatus orderStatus;

    // payment
    private String transactionId;
    private TransactionStatus transactionStatus;
    private PaymentType paymentType;
    private String paymentDetail;
    private BigDecimal amount;
    private String remark;
    private String refNo;

    // inventory
    private String inventoryId;

    // shipping
    private String shippingId;
    private ShippingStatus shippingStatus;
}
