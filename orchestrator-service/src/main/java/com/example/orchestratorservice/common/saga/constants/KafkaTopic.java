package com.example.orchestratorservice.common.saga.constants;

public class KafkaTopic {

    public static final String MAKE_PAYMENT = "make-payment";
    public static final String MAKE_PAYMENT_REPLY = "make-payment-replies";

    public static final String CANCEL_PAYMENT = "cancel-payment";
    public static final String CANCEL_PAYMENT_REPLY = "cancel-payment-replies";

    public static final String DECREASE_STOCK = "decrease-stock";
    public static final String DECREASE_STOCK_REPLY = "decrease-stock-replies";

    public static final String INCREASE_STOCK = "increase-stock";
    public static final String INCREASE_STOCK_REPLY = "increase-stock-replies";

    public static final String SHIP_ORDER = "ship-order";
    public static final String SHIP_ORDER_REPLY = "ship-order-replies";
}
