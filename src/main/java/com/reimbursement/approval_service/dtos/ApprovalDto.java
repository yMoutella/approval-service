package com.reimbursement.approval_service.dtos;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.reimbursement.approval_service.enums.Status;
import com.reimbursement.approval_service.views.ApprovalView;

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
    private Status status;

}
