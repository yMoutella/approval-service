package com.reimbursement.approval_service.services.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.reimbursement.approval_service.dtos.ApprovalDto;
import com.reimbursement.approval_service.entities.ApprovalEntity;
import com.reimbursement.approval_service.enums.Status;
import com.reimbursement.approval_service.exceptions.ResourceNotFound;
import com.reimbursement.approval_service.mappers.ApprovalMapper;
import com.reimbursement.approval_service.repositories.ApprovalRepository;
import com.reimbursement.approval_service.services.ApprovalService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {

    private final ApprovalRepository repository;
    private final ApprovalMapper mapper;

    @Override
    public List<ApprovalEntity> listApprovals() {
        return repository.findAll();
    }

    @Override
    public void saveApproval(ApprovalDto approvalDto) {

        ApprovalEntity approval = this.mapper.toApprovalEntity(approvalDto);
        approval.setStatus(Status.PENDING);

        try {
            repository.save(approval);
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public void saveApproval(Status status, UUID approval_id) {

        var approvalOp = repository.findById(approval_id);

        if (approvalOp.isEmpty()) {
            throw new ResourceNotFound("approval_id: " + approval_id + " not found!");
        }

        try {
            ApprovalEntity approval = approvalOp.get();
            approval.setStatus(status);
            approval.setUpdated_at(LocalDateTime.now(ZoneId.of("UTC")));

            repository.save(approval);
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public ApprovalEntity getApproval(UUID approval_id) {

        var approvalOp = repository.findById(approval_id);

        if (approvalOp.isEmpty()) {
            throw new ResourceNotFound("approval_id: " + approval_id + " not found!");
        }

        return approvalOp.get();

    }

    @Override
    public void deleteApproval(UUID approval_id) {

        var approvalOp = repository.findById(approval_id);

        if (approvalOp.isEmpty()) {
            throw new ResourceNotFound("approval_id: " + approval_id + " not found!");
        }

        ApprovalEntity approval = approvalOp.get();
        approval.setDeleted_at(LocalDateTime.now(ZoneId.of("UTC")));

        try {
            repository.save(approval);
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }

    }

}
