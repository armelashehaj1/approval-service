package net.threeisolutions.approvals.service.dal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.threeisolutions.approvals.api.enums.DocumentRole;
import net.threeisolutions.approvals.service.dal.entity.core.BaseEntity;

import java.util.UUID;

@Entity
@Table(name = "approval_document",
        indexes = @Index(name = "ix_document_request", columnList = "request_id"))
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalDocument extends BaseEntity {

    @Column(name = "request_id", nullable = false, updatable = false)
    private UUID requestId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private DocumentRole role;

    @Column(name = "filename", nullable = false)
    private String filename;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "size_bytes", nullable = false)
    private long sizeBytes;

    @Column(name = "sha256", nullable = false)
    private byte[] sha256;

    @Column(name = "storage_key", nullable = false)
    private String storageKey;
}
