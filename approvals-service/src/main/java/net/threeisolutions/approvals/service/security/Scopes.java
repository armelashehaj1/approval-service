package net.threeisolutions.approvals.service.security;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class Scopes {

    public static final String PREFIX = "SCOPE_";

    public static final String REQUESTS_WRITE = PREFIX + "approvals.requests.write";
    public static final String REQUESTS_READ = PREFIX + "approvals.requests.read";
    public static final String TASKS = PREFIX + "approvals.tasks";
    public static final String ADMIN = PREFIX + "approvals.admin";
}
