package net.threeisolutions.approvals.service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.threeisolutions.approvals.api.dto.admin.RulesetVersionResponse;
import net.threeisolutions.approvals.api.dto.policy.RulesetDto;
import net.threeisolutions.approvals.service.service.rulesetAdmin.RulesetAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/admin/v1/tenants/{tenantId}/rulesets")
@RequiredArgsConstructor
public class RulesetAdminController {

    private final RulesetAdminService service;

    @PostMapping
    ResponseEntity<RulesetVersionResponse> createVersion(@PathVariable String tenantId, @Valid @RequestBody RulesetDto body, Principal admin) {
        RulesetVersionResponse created = service.createVersion(tenantId, body, admin.getName());
        return ResponseEntity.ok(created);
    }

    @GetMapping
    List<RulesetVersionResponse> list(@PathVariable String tenantId, @RequestParam(required = false) String name) {
        return service.list(tenantId, name);
    }

    @GetMapping("/{name}/versions/{version}")
    RulesetVersionResponse get(@PathVariable String tenantId, @PathVariable String name, @PathVariable int version) {
        return service.get(tenantId, name, version);
    }

    @PostMapping("/{name}/versions/{version}:publish")
    RulesetVersionResponse publish(@PathVariable String tenantId, @PathVariable String name, @PathVariable int version) {
        return service.publish(tenantId, name, version);
    }
}
