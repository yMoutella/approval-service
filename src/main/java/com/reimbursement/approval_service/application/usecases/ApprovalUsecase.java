package com.reimbursement.approval_service.application.usecases;

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

import com.reimbursement.approval_service.adapters.inbound.dtos.ApprovalDto;
import com.reimbursement.approval_service.adapters.outbound.repositories.ApprovalRepository;
import com.reimbursement.approval_service.application.services.ApprovalService;
import com.reimbursement.approval_service.domain.Approval.Approval;
import com.reimbursement.approval_service.domain.Approval.ApprovalStatus;
import com.reimbursement.approval_service.infrastructure.exceptions.ResourceDuplicated;
import com.reimbursement.approval_service.infrastructure.exceptions.ResourceNotFound;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApprovalUsecase implements ApprovalService {

    @Autowired
    ApprovalRepository repository;

    @Override
    public Page<Approval> listApprovals(Specification<Approval> spec,
            Pageable page) {
        return repository.findAll(spec, page);
    }

    @Override
    public Approval saveApproval(ApprovalDto approvalDto) {

        var existingId_expense_id = repository.findByExpense_id(approvalDto.getExpense_id());

        if (existingId_expense_id.isPresent()) {
            throw new ResourceDuplicated("expense id: " + approvalDto.getExpense_id() + " already exists!");
        }

        Approval approval = new Approval();
        approval.setExpense_id(approvalDto.getExpense_id());
        approval.setStatus(ApprovalStatus.PENDING);

        try {
            return repository.save(approval);
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public Approval saveApproval(ApprovalStatus status, UUID approval_id) {

        var approvalOp = repository.findById(approval_id);

        if (approvalOp.isEmpty()) {
            throw new ResourceNotFound("approval_id: " + approval_id + " not found!");
        }

        try {
            Approval approval = approvalOp.get();

            // ExpensesClient client = new ExpensesClient();
            // client.updateExpense(status, approval.getExpense_id());

            approval.setStatus(status);
            approval.setUpdated_at(LocalDateTime.now(ZoneId.of("UTC")));

            return repository.save(approval);
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public Approval getApproval(UUID approval_id) {

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

        Approval approval = approvalOp.get();
        approval.setDeleted_at(LocalDateTime.now(ZoneId.of("UTC")));

        try {
            repository.save(approval);
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }

    }

}
