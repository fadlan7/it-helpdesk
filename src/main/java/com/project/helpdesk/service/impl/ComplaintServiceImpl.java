package com.project.helpdesk.service.impl;

import com.project.helpdesk.constant.ComplaintStatus;
import com.project.helpdesk.dto.request.NewComplaintRequest;
import com.project.helpdesk.dto.response.ComplaintDtoResponse;
import com.project.helpdesk.entity.Complaint;
import com.project.helpdesk.entity.Employee;
import com.project.helpdesk.entity.Image;
import com.project.helpdesk.entity.UserAccount;
import com.project.helpdesk.repository.ComplaintRepository;
import com.project.helpdesk.service.ComplaintService;
import com.project.helpdesk.service.EmployeeService;
import com.project.helpdesk.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {
    private final UserAccountService userAccountService;
    private final EmployeeService employeeService;
    private final ComplaintRepository complaintRepository;
    private final ImageServiceImpl imageService;

    @Override
    public ComplaintDtoResponse createComplaint(NewComplaintRequest request) {

        UserAccount account = userAccountService.getByContext();
        Employee currentEmployee = employeeService.getEmployeeByUserAccountId(account.getId());
        String complaintStatus = ComplaintStatus.IN_QUEUE.name();
        UUID generateComplaintId = UUID.randomUUID();
         UUID generateImageId = UUID.randomUUID();

        if (request.getComplaintImage() != null && !request.getComplaintImage().isEmpty()) {
            Image image =  imageService.create(request.getComplaintImage(), generateImageId.toString());
            complaintRepository.createComplaint(generateComplaintId.toString(), request.getTitle(), request.getDescription(), new Date(), complaintStatus, currentEmployee.getId(), generateImageId.toString());
        } else {
            complaintRepository.createComplaint(generateComplaintId.toString(), request.getTitle(), request.getDescription(), new Date(), complaintStatus, currentEmployee.getId(), null);
        }

//        UUID generateImageId = UUID.randomUUID();
//
//
//        Image image = request.getComplaintImage() != null ? imageService.create(request.getComplaintImage(), generateImageId.toString()) : null;
//
//        complaintRepository.createComplaint(generateComplaintId.toString(), request.getTitle(), request.getDescription(), new Date(), complaintStatus, currentEmployee.getId(), generateImageId.toString());

        return null;
    }
}
