package com.reimbursement.approval_service.infrastructure.exceptions;

public class ResourceDuplicated extends RuntimeException {
    public ResourceDuplicated(String message) {
        super(message);
    }

}
