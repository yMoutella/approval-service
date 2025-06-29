package com.reimbursement.approval_service.clients;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

public class ExpensesException extends HttpClientErrorException {

    public ExpensesException(HttpStatusCode statusCode, String message) {
        super(statusCode, message);
    }

}
