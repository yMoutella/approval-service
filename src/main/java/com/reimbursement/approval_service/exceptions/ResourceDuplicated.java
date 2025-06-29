package com.reimbursement.approval_service.exceptions;

public class ResourceDuplicated extends RuntimeException {
    public ResourceDuplicated(String message) {
        super(message);
    }

}
