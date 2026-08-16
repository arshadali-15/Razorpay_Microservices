package com.spring.razorpay_micro.common_lib.enums;

public enum PaymentEvent {
    AUTHORIZE_ATTEMPT,
    AUTHORIZE_SUCCESS,
    AUTHORIZE_FAIL,
    CAPTURE_SUCCESS,
    CAPTURE_FAIL,
    CAPTURE_REQUEST,
    REFUND_INIT,
    REFUND_COMPLETE,
    CANCEL,
    SETTLE,
    CAPTURE_TIMEOUT
}
