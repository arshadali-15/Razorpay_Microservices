package com.spring.razorpay_micro.common_lib.dto;

public record SettlementBankDetails(
        String accountNumber,
        String ifsc,
        String accountHolderName
) {
}
