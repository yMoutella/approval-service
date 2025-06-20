package com.reimbursement.approval_service.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reimbursement.approval_service.entities.ApprovalEntity;
import com.reimbursement.approval_service.repositories.ApprovalRepository;
import com.reimbursement.approval_service.services.ApprovalService;

@Service
public class ApprovalServiceImpl implements ApprovalService {

    @Autowired
    ApprovalRepository repository;

    @Override
    public List<ApprovalEntity> listApprovals() {
        return repository.findAll();
    }

    @Override
    public void saveApproval(ApprovalEntity approval) {
        try {
            repository.save(approval);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Optional<ApprovalEntity> getApproval(UUID approval_id) {
        try {
            return repository.findById(approval_id);
        } catch (Exception e) {
            throw e;
        }
    }
}
