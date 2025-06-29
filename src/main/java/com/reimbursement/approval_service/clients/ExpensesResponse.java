package com.reimbursement.approval_service.clients;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpensesResponse implements Serializable {
    private UUID id;
    private String description;
    private String userId;
    private BigDecimal amount;
    private String type;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;
}
