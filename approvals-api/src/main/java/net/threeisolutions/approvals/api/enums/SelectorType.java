package net.threeisolutions.approvals.api.enums;

/// How a rule finds its approvers in the client's directory
public enum SelectorType {
    PRINCIPAL,
    ROLE,
    GRAPH_WALK,
    ATTRIBUTE_OWNER
}
