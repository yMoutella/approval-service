package com.reimbursement.approval_service.controllers;

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
        return ResponseEntity.status(HttpStatus.OK).body(approvals);
    }

    @GetMapping(value = "/{approval_id}")
    public ResponseEntity<Object> getApproval(@PathVariable UUID approval_id) {

        var approval = approvalService.getApproval(approval_id);
        return ResponseEntity.status(HttpStatus.OK).body(approval);
    }

    @PostMapping()
    @JsonView(ApprovalView.CreateApproval.class)
    public ResponseEntity<?> createApproval(
            @RequestBody @Validated(ApprovalView.CreateApproval.class) ApprovalDto approvalDto) {

        approvalService.saveApproval(approvalDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Approval created!");
    }

    @PatchMapping(value = "/{approval_id}")
    @JsonView(ApprovalView.UpdateApproval.class)
    public ResponseEntity<?> updateApproval(
            @RequestBody @Validated(ApprovalView.UpdateApproval.class) ApprovalDto approvalDto,
            @PathVariable UUID approval_id) {

        approvalService.saveApproval(approvalDto.getStatus(), approval_id);

        return ResponseEntity.status(HttpStatus.OK).body("Approval updated successfully");
    }

}
