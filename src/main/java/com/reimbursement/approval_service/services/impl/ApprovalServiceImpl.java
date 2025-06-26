package com.reimbursement.approval_service.services.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.reimbursement.approval_service.clients.ExpensesClient;
import com.reimbursement.approval_service.dtos.ApprovalDto;
import com.reimbursement.approval_service.entities.ApprovalEntity;
import com.reimbursement.approval_service.enums.Status;
import com.reimbursement.approval_service.exceptions.ResourceDuplicated;
import com.reimbursement.approval_service.exceptions.ResourceNotFound;
import com.reimbursement.approval_service.repositories.ApprovalRepository;
import com.reimbursement.approval_service.services.ApprovalService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {

    @Autowired
    ApprovalRepository repository;

    @Override
    public Page<ApprovalEntity> listApprovals(Specification<ApprovalEntity> spec,
            Pageable page) {
        return repository.findAll(spec, page);
    }

    @Override
    public ApprovalEntity saveApproval(ApprovalDto approvalDto) {

        var existingId_expense_id = repository.findByExpense_id(approvalDto.getExpense_id());

        if (existingId_expense_id.isPresent()) {
            throw new ResourceDuplicated("expense id: " + approvalDto.getExpense_id() + " already exists!");
        }

        ApprovalEntity approval = new ApprovalEntity();
        approval.setExpense_id(approvalDto.getExpense_id());
        approval.setStatus(Status.PENDING);

        try {
            return repository.save(approval);
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public ApprovalEntity saveApproval(Status status, UUID approval_id) {

        var approvalOp = repository.findById(approval_id);

        if (approvalOp.isEmpty()) {
            throw new ResourceNotFound("approval_id: " + approval_id + " not found!");
        }

        try {
            ApprovalEntity approval = approvalOp.get();

            ExpensesClient client = new ExpensesClient();
            client.updateExpense(status, approval.getExpense_id());

            approval.setStatus(status);
            approval.setUpdated_at(LocalDateTime.now(ZoneId.of("UTC")));

            return repository.save(approval);
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
