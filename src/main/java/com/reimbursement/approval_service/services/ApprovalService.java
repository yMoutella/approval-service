package com.reimbursement.approval_service.services;

import java.util.List;
import java.util.UUID;

import com.reimbursement.approval_service.dtos.ApprovalDto;
import com.reimbursement.approval_service.entities.ApprovalEntity;
import com.reimbursement.approval_service.enums.Status;

public interface ApprovalService {

    public List<ApprovalEntity> listApprovals();

    public ApprovalEntity getApproval(UUID approval_id);

    public void saveApproval(ApprovalDto approvalDto);

    public void saveApproval(Status status, UUID approval_id);
}
