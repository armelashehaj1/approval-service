package net.threeisolutions.approvals.service.service.rulesetAdmin;

import net.threeisolutions.approvals.api.dto.admin.RulesetVersionResponse;
import net.threeisolutions.approvals.api.dto.policy.RulesetDto;

import java.util.List;

public interface RulesetAdminService {
    RulesetVersionResponse createVersion(String tenantId, RulesetDto body, String createdBy);

    RulesetVersionResponse publish(String tenantId, String name, int version);

    RulesetVersionResponse get(String tenantId, String name, int version);

    List<RulesetVersionResponse> list(String tenantId, String name);
}
