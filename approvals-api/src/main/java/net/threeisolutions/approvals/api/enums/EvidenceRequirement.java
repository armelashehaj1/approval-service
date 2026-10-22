package net.threeisolutions.approvals.api.enums;

/// What an approver must provide before a decision is accepted
public enum EvidenceRequirement {
    NONE,
    COMMENT,
    SIGNED_FILE,
    STEP_UP_MFA
}
