package net.threeisolutions.approvals.service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.threeisolutions.approvals.api.dto.admin.ClientResponse;
import net.threeisolutions.approvals.api.dto.admin.CreateClientRequest;
import net.threeisolutions.approvals.api.dto.admin.UpdateClientRequest;
import net.threeisolutions.approvals.service.service.clientAdmin.ClientAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/admin/v1/clients")
@RequiredArgsConstructor
public class ClientAdminController {

    private final ClientAdminService service;

    @PostMapping
    ResponseEntity<ClientResponse> create(@Valid @RequestBody CreateClientRequest request) {
        ClientResponse created = service.create(request);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{code}").buildAndExpand(created.getCode()).toUri())
                .body(created);
    }

    @GetMapping
    List<ClientResponse> list(@RequestParam(required = false) String tenantId) {
        return service.list(tenantId);
    }

    @GetMapping("/{code}")
    ClientResponse get(@PathVariable String code) {
        return service.get(code);
    }

    @PutMapping("/{code}")
    ClientResponse update(@PathVariable String code, @Valid @RequestBody UpdateClientRequest request) {
        return service.update(code, request);
    }

    @PostMapping("/{code}:enable")
    ClientResponse enable(@PathVariable String code) {
        return service.setEnabled(code, true);
    }

    @PostMapping("/{code}:disable")
    ClientResponse disable(@PathVariable String code) {
        return service.setEnabled(code, false);
    }
}
