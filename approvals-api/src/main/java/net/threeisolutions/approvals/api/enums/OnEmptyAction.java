package net.threeisolutions.approvals.api.enums;

/// What happens when resolution yields no approvers. AUTO_APPROVE is opt-in only.
public enum OnEmptyAction {
    FAIL,
    FALLBACK,
    AUTO_APPROVE
}
