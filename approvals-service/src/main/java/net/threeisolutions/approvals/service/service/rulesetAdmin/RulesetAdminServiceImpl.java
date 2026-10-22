package net.threeisolutions.approvals.service.service.rulesetAdmin;

import lombok.RequiredArgsConstructor;
import net.threeisolutions.approvals.api.dto.admin.RulesetVersionResponse;
import net.threeisolutions.approvals.api.dto.policy.RulesetDto;
import net.threeisolutions.approvals.service.dal.entity.ChainRuleset;
import net.threeisolutions.approvals.service.dal.repository.ChainRulesetRepository;
import net.threeisolutions.approvals.service.mapper.ChainRulesetMapper;
import net.threeisolutions.approvals.service.util.exception.ConflictException;
import net.threeisolutions.approvals.service.util.exception.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RulesetAdminServiceImpl implements RulesetAdminService {

    private final ChainRulesetRepository rulesets;
    private final ChainRulesetMapper mapper;

    @Override
    @Transactional
    public RulesetVersionResponse createVersion(String tenantId, RulesetDto body, String createdBy) {
        int expected = rulesets.findTopByTenantIdAndNameOrderByVersionDesc(tenantId, body.getName())
                .map(latest -> latest.getVersion() + 1)
                .orElse(1);
        if (body.getVersion() != expected) {
            throw versionConflict(body.getName(), expected);
        }
        try {
            return mapper.toResponse(rulesets.saveAndFlush(ChainRuleset.builder()
                    .tenantId(tenantId)
                    .name(body.getName())
                    .version(body.getVersion())
                    .body(body)
                    .createdBy(createdBy)
                    .build()));
        } catch (DataIntegrityViolationException e) {
            throw versionConflict(body.getName(), expected + 1);
        }
    }

    @Override
    @Transactional
    public RulesetVersionResponse publish(String tenantId, String name, int version) {
        ChainRuleset ruleset = find(tenantId, name, version);
        ruleset.setPublished(true);
        return mapper.toResponse(ruleset);
    }

    @Override
    @Transactional(readOnly = true)
    public RulesetVersionResponse get(String tenantId, String name, int version) {
        return mapper.toResponse(find(tenantId, name, version));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RulesetVersionResponse> list(String tenantId, String name) {
        List<ChainRuleset> found = name == null
                ? rulesets.findByTenantIdOrderByNameAscVersionDesc(tenantId)
                : rulesets.findByTenantIdAndNameOrderByVersionDesc(tenantId, name);
        return found.stream().map(mapper::toSummary).toList();
    }

    private ChainRuleset find(String tenantId, String name, int version) {
        return rulesets.findByTenantIdAndNameAndVersion(tenantId, name, version)
                .orElseThrow(() -> new NotFoundException("Ruleset", name + " v" + version));
    }

    private static ConflictException versionConflict(String name, int expected) {
        return new ConflictException("VERSION_CONFLICT", "Next version of ruleset " + name + " must be " + expected);
    }
}
