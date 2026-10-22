package net.threeisolutions.approvals.service.service.tenantAdmin;

import lombok.RequiredArgsConstructor;
import net.threeisolutions.approvals.api.dto.admin.ChannelSettingsRequest;
import net.threeisolutions.approvals.api.dto.admin.ChannelSettingsResponse;
import net.threeisolutions.approvals.api.dto.admin.TenantCalendarRequest;
import net.threeisolutions.approvals.api.dto.admin.TenantCalendarResponse;
import net.threeisolutions.approvals.api.enums.ChannelType;
import net.threeisolutions.approvals.service.dal.entity.TenantCalendar;
import net.threeisolutions.approvals.service.dal.entity.TenantChannelConfiguration;
import net.threeisolutions.approvals.service.dal.repository.TenantCalendarRepository;
import net.threeisolutions.approvals.service.dal.repository.TenantChannelConfigurationRepository;
import net.threeisolutions.approvals.service.mapper.TenantMapper;
import net.threeisolutions.approvals.service.util.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TenantAdminServiceImpl implements TenantAdminService {

    private final TenantCalendarRepository calendars;
    private final TenantChannelConfigurationRepository channels;
    private final TenantMapper mapper;

    /// One calendar per tenant: created on first call, replaced afterwards
    @Override
    @Transactional
    public TenantCalendarResponse putCalendar(String tenantId, TenantCalendarRequest request) {
        TenantCalendar calendar = calendars.findByTenantId(tenantId)
                .orElseGet(() -> TenantCalendar.builder().tenantId(tenantId).build());
        mapper.update(request, calendar);
        calendar.setWorkDays(calendar.getWorkDays().stream().distinct().sorted().collect(Collectors.toCollection(ArrayList::new)));
        calendar.setHolidays(calendar.getHolidays().stream().distinct().sorted().collect(Collectors.toCollection(ArrayList::new)));
        return mapper.toResponse(calendars.save(calendar));
    }

    @Override
    @Transactional(readOnly = true)
    public TenantCalendarResponse getCalendar(String tenantId) {
        return mapper.toResponse(calendars.findByTenantId(tenantId)
                .orElseThrow(() -> new NotFoundException("Tenant calendar", tenantId)));
    }

    /// One configuration per tenant and channel: created on first call, replaced afterwards
    @Override
    @Transactional
    public ChannelSettingsResponse putChannel(String tenantId, ChannelType channel, ChannelSettingsRequest request) {
        TenantChannelConfiguration configuration = channels.findByTenantIdAndChannel(tenantId, channel)
                .orElseGet(() -> TenantChannelConfiguration.builder().tenantId(tenantId).channel(channel).build());
        mapper.update(request, configuration);
        return mapper.toResponse(channels.save(configuration));
    }

    @Override
    @Transactional(readOnly = true)
    public ChannelSettingsResponse getChannel(String tenantId, ChannelType channel) {
        return mapper.toResponse(channels.findByTenantIdAndChannel(tenantId, channel)
                .orElseThrow(() -> new NotFoundException("Channel settings", tenantId + "/" + channel)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChannelSettingsResponse> listChannels(String tenantId) {
        return channels.findByTenantIdOrderByChannel(tenantId).stream().map(mapper::toResponse).toList();
    }
}
