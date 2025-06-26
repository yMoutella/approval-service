package com.reimbursement.approval_service.clients;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import com.reimbursement.approval_service.enums.Status;

public class ExpensesClient {

    private final RestClient client;

    public ExpensesClient() {
        this.client = RestClient.create();
    }

    public ExpensesResponse updateExpense(Status status, UUID expense_id) {

        ExpensesRequest request = new ExpensesRequest(status);
        ExpensesResponse response = this.client.patch()
                .uri("http://localhost:8082/expenses/{expense_id}/status", expense_id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .exchange((req, res) -> {
                    if (res.getStatusCode().is4xxClientError()) {
                        throw new ExpensesException(res.getStatusCode(), res.getStatusText());
                    } else {
                        return res.bodyTo(ExpensesResponse.class);
                    }
                });

        return response;

    }

}
