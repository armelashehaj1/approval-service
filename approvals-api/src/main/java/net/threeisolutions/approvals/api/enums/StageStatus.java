package net.threeisolutions.approvals.api.enums;

/// Lifecycle of a single stage in the chain
public enum StageStatus {
    WAITING,
    ACTIVE,
    APPROVED,
    REJECTED,
    EXPIRED,
    SKIPPED
}
