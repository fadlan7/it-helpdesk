package com.project.helpdesk.service.impl;

import com.project.helpdesk.dto.request.NewComplaintReplyDtoRequest;
import com.project.helpdesk.dto.request.UpdateComplaintReplyDtoRequest;
import com.project.helpdesk.dto.response.ComplaintDtoResponse;
import com.project.helpdesk.dto.response.ComplaintReplyDtoResponse;
import com.project.helpdesk.entity.Complaint;
import com.project.helpdesk.entity.Employee;
import com.project.helpdesk.entity.UserAccount;
import com.project.helpdesk.repository.ComplaintReplyRepository;
import com.project.helpdesk.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComplaintReplyServiceImpl implements ComplaintReplyService {
    private final UserAccountService userAccountService;
    private final EmployeeService employeeService;
    private final ComplaintService complaintService;
    private final ComplaintReplyRepository complaintReplyRepository;

    @Override
    public ComplaintReplyDtoResponse createComplaintReply(NewComplaintReplyDtoRequest request) {
        UserAccount account = userAccountService.getByContext();
        Employee currentEmployee = employeeService.getEmployeeByUserAccountId(account.getId());
        UUID generatedId = UUID.randomUUID();

        Complaint complaintId = complaintService.getComplaintById(request.getComplaintId());

        ComplaintReplyDtoResponse response = ComplaintReplyDtoResponse.builder()
                .id(generatedId.toString())
                .complaintId(request.getComplaintId())
                .employeeId(currentEmployee.getId())
                .replyDate(new Date())
                .title(request.getTitle())
                .description(request.getDescription())
                .build();

        complaintReplyRepository.createComplaintReply(generatedId.toString(), new Date(), request.getTitle(), request.getDescription(), complaintId.getId(), currentEmployee.getId());

        return response;
    }

    @Override
    public ComplaintDtoResponse updateComplaintReply(UpdateComplaintReplyDtoRequest request) {
        return null;
    }

    @Override
    public void deleteComplaintReply(String id) {

    }
}
