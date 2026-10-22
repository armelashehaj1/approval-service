package net.threeisolutions.approvals.api.enums;

/// Signature validation result of uploaded evidence
public enum ValidationStatus {
    PENDING,
    VALID,
    INVALID,
    SIGNER_MISMATCH,
    UNTRUSTED,
    DOCUMENT_MISMATCH,
    ERROR
}
