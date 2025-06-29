package com.reimbursement.approval_service.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.reimbursement.approval_service.adapters.outbound.repositories.ApprovalRepository;
import com.reimbursement.approval_service.application.usecases.ApprovalUsecase;
import com.reimbursement.approval_service.domain.Approval.Approval;
import com.reimbursement.approval_service.infrastructure.exceptions.ResourceNotFound;

@ExtendWith(MockitoExtension.class)
public class ApprovalServiceTest {

    @InjectMocks
    private ApprovalUsecase service;

    @Mock
    private ApprovalRepository repository;

    @Test
    @DisplayName(value = "Should return a single approval")
    public void should_return_a_single_approval() {

        Approval approval = new Approval();
        approval.setId(UUID.fromString("f7435e98-9510-4d52-a561-d1bcf52a0089"));

        when(repository.findById(UUID.fromString("f7435e98-9510-4d52-a561-d1bcf52a0089")))
                .thenReturn(Optional.of(approval));

        Approval mockApprovals = service
                .getApproval(UUID.fromString("f7435e98-9510-4d52-a561-d1bcf52a0089"));

        assertNotNull(mockApprovals);
        assertEquals(approval, mockApprovals);

    }

    @Test
    @DisplayName(value = "Should return a not found exception")
    public void should_return_a_not_found_exception() {
        try {
            Approval mockApprovals = service
                    .getApproval(UUID.fromString("f7435e98-9510-4d52-a561-d1bcf52a0080"));
        } catch (Exception e) {
            assertInstanceOf(ResourceNotFound.class, e);
        }

    }

    @Test
    @DisplayName(value = "Should return a list of not deleted approvals")
    public void should_return_a_list_of_not_deleted_approvals() {

        Pageable page = PageRequest.of(0, 10);
        Specification<Approval> spec = (root, query, cr) -> cr.isNotNull(root.get("deleted_at"));

        Approval e1 = new Approval();
        Page mockPage = new PageImpl<>(List.of(e1));

        when(repository.findAll(spec, page)).thenReturn(mockPage);

        try {
            Page<Approval> approvalsReturned = service.listApprovals(spec, page);

            assertThat(approvalsReturned).hasSize(1);
            assertNotNull(approvalsReturned);
        } catch (Exception e) {
            assertInstanceOf(ResourceNotFound.class, e);
        }

    }

    // @Test
    // @DisplayName(value = "Should return a list of deleted approvals")
    // public void should_return_a_list_of_deleted_approvals() {

    // Pageable page = PageRequest.of(0, 10);
    // Specification<ApprovalEntity> spec = (root, query, cr) ->
    // cr.isNotNull(root.get("deleted_at"));

    // ApprovalEntity approvalMock = new ApprovalEntity();
    // approvalMock.setExpense_id(UUID.fromString("f7435e98-9510-4d52-a561-d1bcf52a0080"));
    // approvalMock.setId(UUID.randomUUID());

    // Page<ApprovalEntity> mockPage = new PageImpl<>(List.of(approvalMock));

    // when(repository.findAll(spec, page)).thenReturn(mockPage);

    // try {
    // Page<ApprovalEntity> approvalsReturned = service.listApprovals(spec, page);

    // assertEquals(mockPage, approvalsReturned);
    // assertNotNull(approvalMock.getId());
    // assertNotNull(approvalsReturned);
    // } catch (Exception e) {
    // assertInstanceOf(ResourceNotFound.class, e);
    // }

    // }

}
