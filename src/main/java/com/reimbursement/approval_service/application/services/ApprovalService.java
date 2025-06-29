package com.reimbursement.approval_service.application.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.reimbursement.approval_service.adapters.inbound.dtos.ApprovalDto;
import com.reimbursement.approval_service.domain.Approval.Approval;
import com.reimbursement.approval_service.domain.Approval.ApprovalStatus;

public interface ApprovalService {

    public Page<Approval> listApprovals(Specification<Approval> spec, Pageable page);

    public Approval getApproval(UUID approval_id);

    public Approval saveApproval(ApprovalDto approvalDto);

    public Approval saveApproval(ApprovalStatus status, UUID approval_id);

    public void deleteApproval(UUID approval_id);
}
