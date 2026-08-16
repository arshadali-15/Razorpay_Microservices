package com.spring.razorpay_micro.common_lib.enums;

public enum PaymentStatus {
    CREATED,
    AUTHORIZING,
    AUTHORIZED,
    CAPTURING,
    CAPTURED,
    REFUNDED,
    FAILED,
    CANCELED,
    SETTLED,
    PARTIALLY_REFUNDED,
    AUTH_EXPIRED
}
