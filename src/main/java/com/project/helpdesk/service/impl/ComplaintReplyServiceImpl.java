package com.project.helpdesk.service.impl;

import com.project.helpdesk.constant.ComplaintStatus;
import com.project.helpdesk.constant.ResponseMessage;
import com.project.helpdesk.dto.request.NewComplaintReplyDtoRequest;
import com.project.helpdesk.dto.request.UpdateComplaintReplyDtoRequest;
import com.project.helpdesk.dto.response.ComplaintDtoResponse;
import com.project.helpdesk.dto.response.ComplaintReplyDtoResponse;
import com.project.helpdesk.entity.*;
import com.project.helpdesk.repository.ComplaintReplyRepository;
import com.project.helpdesk.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComplaintReplyServiceImpl implements ComplaintReplyService {
    private final UserAccountService userAccountService;
    private final EmployeeService employeeService;
    private final ComplaintService complaintService;
    private final ComplaintReplyRepository complaintReplyRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ComplaintReplyDtoResponse createComplaintReply(NewComplaintReplyDtoRequest request) {
        UserAccount account = userAccountService.getByContext();
        Employee currentEmployee = employeeService.getEmployeeByUserAccountId(account.getId());
        UUID generatedId = UUID.randomUUID();

        Complaint currentComplaint = complaintService.getComplaintById(request.getComplaintId());

        if (currentComplaint.getStatus().equals(ComplaintStatus.CANCELLED.name())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ResponseMessage.ERROR_COMPLAINT_CANNOT_BE_CREATED);
        }

        ComplaintReplyDtoResponse response = ComplaintReplyDtoResponse.builder()
                .id(generatedId.toString())
                .complaintId(request.getComplaintId())
                .employeeId(currentEmployee.getId())
                .replyDate(new Date())
                .title(request.getTitle())
                .description(request.getDescription())
                .build();

        complaintReplyRepository.createComplaintReply(generatedId.toString(), new Date(), request.getTitle(), request.getDescription(), currentComplaint.getId(), currentEmployee.getId());
        complaintService.updateComplaintStatus(currentComplaint.getId(), ComplaintStatus.COMPLETED.name());
        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ComplaintReplyDtoResponse updateComplaintReply(UpdateComplaintReplyDtoRequest request) {
        ComplaintReply currentReply = findByIdOrThrowNotFound(request.getId());

        ComplaintReplyDtoResponse response = ComplaintReplyDtoResponse.builder()
                .id(currentReply.getId())
                .replyDate(currentReply.getReplyDate())
                .title(request.getTitle())
                .description(request.getDescription())
                .complaintId(currentReply.getComplaint().getId())
                .employeeId(currentReply.getEmployee().getId())
                .build();

        complaintReplyRepository.updateComplaintReply(request.getId(), request.getTitle(), request.getDescription());
        return response;
    }

    private ComplaintReply findByIdOrThrowNotFound(String id) {
        return complaintReplyRepository.getComplaintReplyById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }
}
