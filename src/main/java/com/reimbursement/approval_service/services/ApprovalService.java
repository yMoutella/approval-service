package com.reimbursement.approval_service.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.reimbursement.approval_service.dtos.ApprovalDto;
import com.reimbursement.approval_service.entities.ApprovalEntity;
import com.reimbursement.approval_service.enums.Status;

public interface ApprovalService {

    public Page<ApprovalEntity> listApprovals(Specification<ApprovalEntity> spec, Pageable page);

    public ApprovalEntity getApproval(UUID approval_id);

    public ApprovalEntity saveApproval(ApprovalDto approvalDto);

    public void saveApproval(Status status, UUID approval_id);

    public void deleteApproval(UUID approval_id);
}
