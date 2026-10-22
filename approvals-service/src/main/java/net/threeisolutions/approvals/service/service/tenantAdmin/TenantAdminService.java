package net.threeisolutions.approvals.service.service.tenantAdmin;

import net.threeisolutions.approvals.api.dto.admin.ChannelSettingsRequest;
import net.threeisolutions.approvals.api.dto.admin.ChannelSettingsResponse;
import net.threeisolutions.approvals.api.dto.admin.TenantCalendarRequest;
import net.threeisolutions.approvals.api.dto.admin.TenantCalendarResponse;
import net.threeisolutions.approvals.api.enums.ChannelType;

import java.util.List;

public interface TenantAdminService {
    TenantCalendarResponse putCalendar(String tenantId, TenantCalendarRequest request);

    TenantCalendarResponse getCalendar(String tenantId);

    ChannelSettingsResponse putChannel(String tenantId, ChannelType channel, ChannelSettingsRequest request);

    ChannelSettingsResponse getChannel(String tenantId, ChannelType channel);

    List<ChannelSettingsResponse> listChannels(String tenantId);
}
