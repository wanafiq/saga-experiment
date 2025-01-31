package com.example.orderservice.saga.constants;

public class KafkaTopic {

    public static final String CREATE_ORDER = "create-order";
    public static final String CREATE_REPLY = "create-order-replies";

    public static final String MAKE_PAYMENT = "make-payment";
    public static final String MAKE_PAYMENT_REPLY = "make-payment-replies";

    public static final String DEDUCT_STOCK_QUANTITY = "deduct-stock-quantity";
    public static final String DEDUCT_STOCK_QUANTITY_REPLY = "deduct-stock-quantity-replies";

    public static final String SHIP_ORDER = "ship-order";
    public static final String SHIP_ORDER_REPLY = "ship-order-replies";

    public static final String CANCEL_ORDER = "cancel-order";
    public static final String CANCEL_ORDER_REPLY = "cancel-order-replies";

    public static final String REFUND_PAYMENT = "refund-payment";
    public static final String REFUND_PAYMENT_REPLY = "refund-payment-replies";

    public static final String RESTORE_STOCK_QUANTITY = "restore-stock-quantity";
    public static final String RESTORE_STOCK_QUANTITY_REPLY = "restore-stock-quantity-replies";
}
