package com.reimbursement.approval_service.entities;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.reimbursement.approval_service.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "approvals")
public class ApprovalEntity {

    public ApprovalEntity() {
        this.status = Status.PENDING;
        this.created_at = LocalDateTime.now(ZoneId.of("UTC"));
        this.updated_at = LocalDateTime.now(ZoneId.of("UTC"));
        this.deleted_at = null;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false, unique = true)
    private UUID expense_id;

    @Column(nullable = false)
    private LocalDateTime created_at;

    @Column(nullable = false)
    private LocalDateTime updated_at;

    @Column()
    private LocalDateTime deleted_at;
}
