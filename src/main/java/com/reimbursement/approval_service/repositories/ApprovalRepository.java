package com.reimbursement.approval_service.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.reimbursement.approval_service.entities.ApprovalEntity;

public interface ApprovalRepository extends JpaRepository<ApprovalEntity, UUID> , JpaSpecificationExecutor<ApprovalEntity> {

}
