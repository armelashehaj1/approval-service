package net.threeisolutions.approvals.service.service.clientAdmin;


import net.threeisolutions.approvals.api.dto.admin.ClientResponse;
import net.threeisolutions.approvals.api.dto.admin.CreateClientRequest;
import net.threeisolutions.approvals.api.dto.admin.UpdateClientRequest;

import java.util.List;

public interface ClientAdminService {
    ClientResponse create(CreateClientRequest request);

    ClientResponse update(String code, UpdateClientRequest request);

    ClientResponse setEnabled(String code, boolean enabled);

    ClientResponse get(String code);

    List<ClientResponse> list(String tenantId);
}
