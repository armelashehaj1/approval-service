package net.threeisolutions.approvals.service.mapper;

import net.threeisolutions.approvals.api.dto.admin.RulesetVersionResponse;
import net.threeisolutions.approvals.service.dal.entity.ChainRuleset;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChainRulesetMapper {
    RulesetVersionResponse toResponse(ChainRuleset ruleset);

    @Mapping(target = "body", ignore = true)
    RulesetVersionResponse toSummary(ChainRuleset ruleset);
}
