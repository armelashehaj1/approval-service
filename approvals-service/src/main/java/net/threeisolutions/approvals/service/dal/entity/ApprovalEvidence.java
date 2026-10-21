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
import net.threeisolutions.approvals.api.enums.EvidenceKind;
import net.threeisolutions.approvals.api.enums.ScanStatus;
import net.threeisolutions.approvals.api.enums.ValidationStatus;
import net.threeisolutions.approvals.service.dal.entity.core.BaseEntity;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "approval_evidence",
        indexes = @Index(name = "ix_evidence_assignment", columnList = "assignment_id"))
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalEvidence extends BaseEntity {

    @Column(name = "request_id", nullable = false, updatable = false)
    private UUID requestId;

    @Column(name = "assignment_id", nullable = false, updatable = false)
    private UUID assignmentId;

    /// The TO_SIGN document this file claims to be a signed copy of
    @Column(name = "document_id")
    private UUID documentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "kind", nullable = false)
    private EvidenceKind kind;

    @Column(name = "filename", nullable = false)
    private String filename;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "size_bytes", nullable = false)
    private long sizeBytes;

    @Column(name = "sha256", nullable = false)
    private byte[] sha256;

    /// Id of the file in file-management, null once the file is purged, so the row still shows one existed.
    @Column(name = "file_id")
    private UUID fileId;

    @Enumerated(EnumType.STRING)
    @Column(name = "scan_status", nullable = false)
    @Builder.Default
    private ScanStatus scanStatus = ScanStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "validation_status", nullable = false)
    @Builder.Default
    private ValidationStatus validationStatus = ValidationStatus.PENDING;

    @Column(name = "signature_level")
    private String signatureLevel;

    @Column(name = "signer_subject")
    private String signerSubject;

    @Column(name = "signer_serial")
    private String signerSerial;

    @Column(name = "signer_email")
    private String signerEmail;

    @Column(name = "signed_at")
    private Instant signedAt;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "validation_report")
    private Map<String, Object> validationReport;

    @Column(name = "uploaded_at", nullable = false, updatable = false)
    @Builder.Default
    private Instant uploadedAt = Instant.now();
}
