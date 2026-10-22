package net.threeisolutions.approvals.api.enums;

/// Post-processing steps applied to the resolved chain, in configured order.
public enum PipelineStepType {
    DROP_UNRESOLVED,
    EXCLUDE_PRINCIPAL,
    APPLY_DELEGATION,
    DEDUPE,
    RENUMBER
}
