package com.reimbursement.approval_service.adapters.outbound.entities;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import com.reimbursement.approval_service.domain.approval.ApprovalStatus;

public class JpaApproval {

    private UUID id;

    private ApprovalStatus status;

    private UUID expense_id;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    private LocalDateTime deleted_at;

    public JpaApproval() {
        this.status = ApprovalStatus.PENDING;
        this.created_at = LocalDateTime.now(ZoneId.of("UTC"));
        this.updated_at = LocalDateTime.now(ZoneId.of("UTC"));
        this.deleted_at = null;
    }

    public JpaApproval(UUID id, ApprovalStatus status, UUID expense_id, LocalDateTime created_at,
            LocalDateTime updated_at, LocalDateTime deleted_at) {
        this.id = id;
        this.status = status;
        this.expense_id = expense_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }

    public UUID getExpense_id() {
        return expense_id;
    }

    public void setExpense_id(UUID expense_id) {
        this.expense_id = expense_id;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public LocalDateTime getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(LocalDateTime deleted_at) {
        this.deleted_at = deleted_at;
    }

}
