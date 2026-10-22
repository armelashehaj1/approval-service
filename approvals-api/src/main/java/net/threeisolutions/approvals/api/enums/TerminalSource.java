package net.threeisolutions.approvals.api.enums;

/// What moved a request into its terminal status
public enum TerminalSource {
    DECISION,
    EXPIRY,
    CANCEL,
    SYSTEM
}
