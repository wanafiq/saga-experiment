package com.example.orderservice.saga.enums;

public enum CreateOrderStep {
    CREATE_ORDER,
    MAKE_PAYMENT,
    DEDUCT_STOCK_QUANTITY,
    SHIP_ORDER,
    CANCEL_ORDER,
    REFUND_PAYMENT,
    RESTORE_STOCK_QUANTITY,
}
