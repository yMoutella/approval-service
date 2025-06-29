package com.reimbursement.approval_service.adapters.outbound.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.reimbursement.approval_service.domain.Approval.Approval;

public interface ApprovalRepository
        extends JpaRepository<Approval, UUID>, JpaSpecificationExecutor<Approval> {

    @Query(value = "SELECT * FROM approvals WHERE expense_id = :expense_id", nativeQuery = true)
    Optional<Approval> findByExpense_id(@Param("expense_id") UUID expense_id);

}
