package net.threeisolutions.approvals.service.service.clientAdmin;

import lombok.RequiredArgsConstructor;
import net.threeisolutions.approvals.api.dto.admin.ClientResponse;
import net.threeisolutions.approvals.api.dto.admin.CreateClientRequest;
import net.threeisolutions.approvals.api.dto.admin.UpdateClientRequest;
import net.threeisolutions.approvals.api.dto.client.ClientConfigDto;
import net.threeisolutions.approvals.api.enums.ClientAuthMode;
import net.threeisolutions.approvals.service.dal.entity.ApprovalClient;
import net.threeisolutions.approvals.service.dal.repository.ApprovalClientRepository;
import net.threeisolutions.approvals.service.mapper.ApprovalClientMapper;
import net.threeisolutions.approvals.service.util.exception.ConflictException;
import net.threeisolutions.approvals.service.util.exception.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientAdminServiceImpl implements ClientAdminService {

    private final ApprovalClientRepository clients;
    private final ApprovalClientMapper mapper;

    @Override
    @Transactional
    public ClientResponse create(CreateClientRequest request) {
        if (clients.existsByCode(request.getCode())) {
            throw new ConflictException("DUPLICATE_CLIENT", "A client with code " + request.getCode() + " exists");
        }
        requireUniqueOAuthClient(request.getCode(), null, request.getConfig());
        return mapper.toResponse(clients.save(mapper.toEntity(request)));
    }


    @Override
    @Transactional
    public ClientResponse update(String code, UpdateClientRequest request) {
        ApprovalClient client = find(code);
        requireUniqueOAuthClient(code, client.getId(), request.getConfig());
        mapper.update(request, client);
        return mapper.toResponse(client);
    }

    @Override
    @Transactional
    public ClientResponse setEnabled(String code, boolean enabled) {
        ApprovalClient client = find(code);
        client.setEnabled(enabled);
        return mapper.toResponse(client);
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponse get(String code) {
        return mapper.toResponse(find(code));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> list(String tenantId) {
        List<ApprovalClient> found = tenantId == null
                ? clients.findAll(Sort.by("code"))
                : clients.findByTenantIdOrderByCode(tenantId);
        return found.stream().map(mapper::toResponse).toList();
    }

    private ApprovalClient find(String code) {
        return clients.findByCode(code).orElseThrow(() -> new NotFoundException("Client", code));
    }

    /// Tokens are matched to clients by azp / client_id alone, so two OAUTH_CC clients must not share it
    private void requireUniqueOAuthClient(String code, UUID id, ClientConfigDto config) {
        if (config.getAuth().getMode() != ClientAuthMode.OAUTH_CC) {
            return;
        }
        String oauthClientId = config.getAuth().getClientId() != null ? config.getAuth().getClientId() : code;
        clients.findByOAuthClient(oauthClientId)
                .filter(other -> !other.getId().equals(id))
                .ifPresent(other -> {
                    throw new ConflictException("DUPLICATE_OAUTH_CLIENT", "OAuth client id " + oauthClientId + " is used by client " + other.getCode());
                });
    }
}
