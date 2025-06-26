package com.reimbursement.approval_service.clients;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import com.reimbursement.approval_service.enums.Status;

public class ExpensesClient {

    RestClient client = RestClient.create();

    public ResponseEntity<Object> updateExpense(Status status) {

        ExpensesRequest request = new ExpensesRequest(status);
        ExpensesResponse response = client.patch()
                .uri("http://localhost:8083")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .exchange((req, res) -> {
                    if (res.getStatusCode().is4xxClientError()) {
                        throw new ExpensesException(res.getStatusCode(), res.getStatusText());
                    } else {
                        return res.bodyTo(ExpensesResponse.class);
                    }
                });

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

}
