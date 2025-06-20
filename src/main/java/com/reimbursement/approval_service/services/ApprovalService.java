package com.reimbursement.approval_service.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.reimbursement.approval_service.entities.ApprovalEntity;

public interface ApprovalService {

    public List<ApprovalEntity> listApprovals();

    public Optional<ApprovalEntity> getApproval(UUID approval_id);

    public void saveApproval(ApprovalEntity approval);
}
