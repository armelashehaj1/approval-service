package net.threeisolutions.approvals.service.mapper;

import net.threeisolutions.approvals.api.dto.admin.ClientResponse;
import net.threeisolutions.approvals.api.dto.admin.CreateClientRequest;
import net.threeisolutions.approvals.api.dto.admin.UpdateClientRequest;
import net.threeisolutions.approvals.service.dal.entity.ApprovalClient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ApprovalClientMapper {

    @Mapping(target = "authMode", source = "config.auth.mode")
    @Mapping(target = "policyScope", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    ApprovalClient toEntity(CreateClientRequest request);

    @Mapping(target = "authMode", source = "config.auth.mode")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "tenantId", ignore = true)
    @Mapping(target = "policyScope", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    void update(UpdateClientRequest request, @MappingTarget ApprovalClient client);

    ClientResponse toResponse(ApprovalClient client);
}
