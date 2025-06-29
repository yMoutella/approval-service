package com.reimbursement.approval_service.adapters.inbound.dtos;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.reimbursement.approval_service.adapters.inbound.views.ApprovalView;
import com.reimbursement.approval_service.domain.Approval.ApprovalStatus;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApprovalDto {

    @JsonView(ApprovalView.CreateApproval.class)
    @NotNull(message = "expense_id cannot be null!", groups = { ApprovalView.CreateApproval.class })
    private UUID expense_id;

    @JsonView(ApprovalView.UpdateApproval.class)
    @NotNull(message = "status must be: PENDING, APPROVED OR REJECTED", groups = { ApprovalView.UpdateApproval.class })
    private ApprovalStatus status;

}
