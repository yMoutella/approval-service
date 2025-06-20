package com.reimbursement.approval_service.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.reimbursement.approval_service.dtos.ApprovalDto;
import com.reimbursement.approval_service.entities.ApprovalEntity;
import com.reimbursement.approval_service.services.ApprovalService;
import com.reimbursement.approval_service.views.ApprovalView;

@RestController
@RequestMapping(value = "approvals")
@Validated
public class ApprovalController {

    @Autowired
    ApprovalService approvalService;

    @GetMapping()
    public ResponseEntity<Object> listApprovals() {
        List<ApprovalEntity> approvals = approvalService.listApprovals();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(approvals);
    }

    @PostMapping()
    @JsonView(ApprovalView.CreateApproval.class)
    public ResponseEntity<?> createApproval(
            @RequestBody @Validated(ApprovalView.CreateApproval.class) ApprovalDto approvalDto) {

        ApprovalEntity approval = new ApprovalEntity();

        approval.setExpense_id(approvalDto.getExpense_id());
        approvalService.saveApproval(approval);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("");
    }

    @PatchMapping(value = "/{approval_id}")
    @JsonView(ApprovalView.UpdateApproval.class)
    public ResponseEntity<?> updateApproval(
            @RequestBody @Validated(ApprovalView.UpdateApproval.class) ApprovalDto approvalDto,
            @PathVariable UUID approval_id) {

        var approval = approvalService.getApproval(approval_id);

        if (approval.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Approval not found!");
        }

        ApprovalEntity approvalEntity = approval.get();
        approvalEntity.setUpdated_at(LocalDateTime.now(ZoneId.of("UTC")));
        approvalEntity.setStatus(approvalDto.getStatus());

        approvalService.saveApproval(approvalEntity);

        return ResponseEntity.status(HttpStatus.OK).body("Approval updated successfully");
    }

}
