package com.reimbursement.approval_service.mappers;

import org.mapstruct.Mapper;

import com.reimbursement.approval_service.dtos.ApprovalDto;
import com.reimbursement.approval_service.entities.ApprovalEntity;

@Mapper(componentModel = "spring")
public interface ApprovalMapper {
    ApprovalEntity toApprovalEntity(ApprovalDto approvalDto);
}
