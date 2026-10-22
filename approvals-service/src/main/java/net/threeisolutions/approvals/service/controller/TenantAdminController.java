package net.threeisolutions.approvals.service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.threeisolutions.approvals.api.dto.admin.ChannelSettingsRequest;
import net.threeisolutions.approvals.api.dto.admin.ChannelSettingsResponse;
import net.threeisolutions.approvals.api.dto.admin.TenantCalendarRequest;
import net.threeisolutions.approvals.api.dto.admin.TenantCalendarResponse;
import net.threeisolutions.approvals.api.enums.ChannelType;
import net.threeisolutions.approvals.service.service.tenantAdmin.TenantAdminService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/v1/tenants/{tenantId}")
@RequiredArgsConstructor
public class TenantAdminController {

    private final TenantAdminService service;

    @PutMapping("/calendar")
    TenantCalendarResponse putCalendar(@PathVariable String tenantId, @Valid @RequestBody TenantCalendarRequest request) {
        return service.putCalendar(tenantId, request);
    }

    @GetMapping("/calendar")
    TenantCalendarResponse getCalendar(@PathVariable String tenantId) {
        return service.getCalendar(tenantId);
    }

    @PutMapping("/channels/{channel}")
    ChannelSettingsResponse putChannel(@PathVariable String tenantId, @PathVariable ChannelType channel,
                                       @Valid @RequestBody ChannelSettingsRequest request) {
        return service.putChannel(tenantId, channel, request);
    }

    @GetMapping("/channels/{channel}")
    ChannelSettingsResponse getChannel(@PathVariable String tenantId, @PathVariable ChannelType channel) {
        return service.getChannel(tenantId, channel);
    }

    @GetMapping("/channels")
    List<ChannelSettingsResponse> listChannels(@PathVariable String tenantId) {
        return service.listChannels(tenantId);
    }
}
