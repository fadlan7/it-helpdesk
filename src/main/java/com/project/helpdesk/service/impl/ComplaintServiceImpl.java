package com.project.helpdesk.service.impl;

import com.project.helpdesk.constant.ApiUrl;
import com.project.helpdesk.constant.ComplaintStatus;
import com.project.helpdesk.constant.ResponseMessage;
import com.project.helpdesk.dto.request.NewComplaintRequest;
import com.project.helpdesk.dto.request.UpdateComplaintRequest;
import com.project.helpdesk.dto.response.ComplaintDtoResponse;
import com.project.helpdesk.dto.response.EmployeeResponse;
import com.project.helpdesk.dto.response.ImageResponse;
import com.project.helpdesk.entity.Complaint;
import com.project.helpdesk.entity.Employee;
import com.project.helpdesk.entity.Image;
import com.project.helpdesk.entity.UserAccount;
import com.project.helpdesk.repository.ComplaintRepository;
import com.project.helpdesk.service.ComplaintService;
import com.project.helpdesk.service.EmployeeService;
import com.project.helpdesk.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {
    private final UserAccountService userAccountService;
    private final EmployeeService employeeService;
    private final ComplaintRepository complaintRepository;
    private final ImageServiceImpl imageService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ComplaintDtoResponse createComplaint(NewComplaintRequest request) {

        UserAccount account = userAccountService.getByContext();
        Employee currentEmployee = employeeService.getEmployeeByUserAccountId(account.getId());
        String complaintStatus = ComplaintStatus.IN_QUEUE.name();
        UUID generateComplaintId = UUID.randomUUID();
        UUID generateImageId = UUID.randomUUID();

        if (request.getComplaintImage() != null && !request.getComplaintImage().isEmpty()) {
            Image image = imageService.create(request.getComplaintImage(), generateImageId.toString());
            complaintRepository.createComplaint(generateComplaintId.toString(), request.getTitle(), request.getDescription(), new Date(), complaintStatus, currentEmployee.getId(), generateImageId.toString());
        } else {
            complaintRepository.createComplaint(generateComplaintId.toString(), request.getTitle(), request.getDescription(), new Date(), complaintStatus, currentEmployee.getId(), null);
        }

        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ComplaintDtoResponse updateComplaint(UpdateComplaintRequest request) {
        Complaint currentComplaint = findByIdOrThrowNotFound(request.getId());

        if (!currentComplaint.getStatus().equals(ComplaintStatus.IN_QUEUE.name())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ResponseMessage.ERROR_COMPLAINT_CANNOT_BE_EDITED);
        }

        currentComplaint.setTitle(request.getTitle());
        currentComplaint.setDescription(request.getDescription());

        if (request.getComplaintImage() != null && !request.getComplaintImage().isEmpty()) {
            String currentImageId = currentComplaint.getComplaintImage().getId();
            UUID imageId = UUID.randomUUID();

            Image image = imageService.create(request.getComplaintImage(), imageId.toString());
            complaintRepository.updateComplaintWithImage(request.getId(), request.getTitle(), request.getDescription(), imageId.toString());

            imageService.deleteById(currentImageId);

            return convertComplaintToComplaintResponse(currentComplaint);
        }

        complaintRepository.updateComplaintWithoutImage(request.getId(), request.getTitle(), request.getDescription());

        return convertComplaintToComplaintResponse(currentComplaint);
    }

    @Override
    public void deleteComplaint(String id) {
        Complaint currentComplaint = findByIdOrThrowNotFound(id);
        String imageId = currentComplaint.getComplaintImage().getId();

        if (!currentComplaint.getStatus().equals(ComplaintStatus.IN_QUEUE.name())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ResponseMessage.ERROR_COMPLAINT_CANNOT_BE_DELETED);
        }

        complaintRepository.deleteComplaint(id);
        imageService.deleteById(imageId);
    }

    @Override
    public void updateComplaintStatus(String id, Integer status) {
        findByIdOrThrowNotFound(id);

        String complaintStatus = null;

        switch (status) {
            case 0:
                complaintStatus = ComplaintStatus.IN_QUEUE.name();
                break;
            case 1:
                complaintStatus = ComplaintStatus.IN_PROGRESS.name();
                break;
            case 2:
                complaintStatus = ComplaintStatus.COMPLETED.name();
                break;
            case 3:
                complaintStatus = ComplaintStatus.CANCELLED.name();
                break;
            default:
                break;
        }

        complaintRepository.updateComplaintStatus(id, complaintStatus);
    }


    private Complaint findByIdOrThrowNotFound(String id) {
        return complaintRepository.getComplaintById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
    }


    private ComplaintDtoResponse convertComplaintToComplaintResponse(Complaint complaint) {
        return ComplaintDtoResponse.builder()
                .id(complaint.getId())
                .employee(EmployeeResponse.builder()
                        .id(complaint.getEmployee().getId())
                        .build())
                .complaintDate(complaint.getComplaintDate())
                .title(complaint.getTitle())
                .description(complaint.getDescription())
                .status(complaint.getStatus())
                .complaintImage(
                        Optional.ofNullable(complaint.getComplaintImage())
                                .map(image -> ImageResponse.builder()
                                        .url(ApiUrl.COMPLAINT_IMAGE_DOWNLOAD_API + image.getId())
                                        .name(image.getName())
                                        .build())
                                .orElse(null)
                )
                .build();
    }
}
