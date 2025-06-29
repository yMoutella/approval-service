package com.reimbursement.approval_service.clients;

import com.reimbursement.approval_service.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpensesRequest {

    private Status status;

}
