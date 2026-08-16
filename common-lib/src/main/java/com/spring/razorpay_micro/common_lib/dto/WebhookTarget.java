package com.spring.razorpay_micro.common_lib.dto;

import java.util.UUID;

public record WebhookTarget(UUID confgId, String targetUrl, String webhookSecret) {
}
