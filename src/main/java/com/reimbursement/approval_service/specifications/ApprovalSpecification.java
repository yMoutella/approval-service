package com.reimbursement.approval_service.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.reimbursement.approval_service.entities.ApprovalEntity;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.NotNull;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@And({
        @Spec(path = "deleted_at", params = "isDeleted", spec = NotNull.class),
        @Spec(path = "status", spec = Equal.class)
})
public interface ApprovalSpecification extends Specification<ApprovalEntity> {
}
