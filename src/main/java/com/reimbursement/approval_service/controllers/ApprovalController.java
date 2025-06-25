package com.reimbursement.approval_service.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.reimbursement.approval_service.specifications.ApprovalSpecification;
import com.reimbursement.approval_service.views.ApprovalView;

@RestController
@RequestMapping(value = "approvals")
@Validated
public class ApprovalController {

    @Autowired
    ApprovalService approvalService;

    @GetMapping()
    public ResponseEntity<Object> listApprovals(ApprovalSpecification spec,
            @PageableDefault(page = 0, size = 10, direction = Sort.Direction.ASC) Pageable page) {

        Page<ApprovalEntity> approvals = approvalService.listApprovals(spec, page);
        return ResponseEntity.status(HttpStatus.OK).body(approvals.getContent());
    }

    @GetMapping(value = "/{approval_id}")
    public ResponseEntity<Object> getApproval(@PathVariable UUID approval_id) {

        var approval = approvalService.getApproval(approval_id);
        return ResponseEntity.status(HttpStatus.OK).body(approval);
    }

    @PostMapping()
    public ResponseEntity<Object> createApproval(
            @RequestBody @Validated(ApprovalView.CreateApproval.class) @JsonView(ApprovalView.CreateApproval.class) ApprovalDto approvalDto) {

        ApprovalEntity approval = approvalService.saveApproval(approvalDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(approval);
    }

    @PatchMapping(value = "/{approval_id}")
    @JsonView(ApprovalView.UpdateApproval.class)
    public ResponseEntity<?> updateApproval(
            @RequestBody @Validated(ApprovalView.UpdateApproval.class) ApprovalDto approvalDto,
            @PathVariable UUID approval_id) {

        approvalService.saveApproval(approvalDto.getStatus(), approval_id);

        // Update Expenses-service

        return ResponseEntity.status(HttpStatus.OK).body("Approval updated successfully");
    }

    @DeleteMapping(value = "/{approval_id}")
    public ResponseEntity<?> deleteApproval(
            @PathVariable UUID approval_id) {

        approvalService.deleteApproval(approval_id);

        return ResponseEntity.status(HttpStatus.OK).body("Approval deleted!");
    }

}
