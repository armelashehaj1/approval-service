package net.threeisolutions.approvals.api.enums;

/// How a client receives the outcome of its requests
public enum DeliveryMode {
    KAFKA,
    WEBHOOK,
    POLL
}
