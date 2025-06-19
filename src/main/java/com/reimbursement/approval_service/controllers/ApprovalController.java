package com.reimbursement.approval_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "approvals")
public class ApprovalController {

    @GetMapping()
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Hello world!");
    }

}
