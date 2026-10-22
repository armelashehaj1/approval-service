package net.threeisolutions.approvals.service.mapper;

import net.threeisolutions.approvals.api.dto.admin.ChannelSettingsRequest;
import net.threeisolutions.approvals.api.dto.admin.ChannelSettingsResponse;
import net.threeisolutions.approvals.api.dto.admin.TenantCalendarRequest;
import net.threeisolutions.approvals.api.dto.admin.TenantCalendarResponse;
import net.threeisolutions.approvals.service.dal.entity.TenantCalendar;
import net.threeisolutions.approvals.service.dal.entity.TenantChannelConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TenantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    void update(TenantCalendarRequest request, @MappingTarget TenantCalendar calendar);

    TenantCalendarResponse toResponse(TenantCalendar calendar);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "channel", ignore = true)
    void update(ChannelSettingsRequest request, @MappingTarget TenantChannelConfiguration configuration);

    ChannelSettingsResponse toResponse(TenantChannelConfiguration configuration);
}
