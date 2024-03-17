package com.project.helpdesk.service;

import com.project.helpdesk.dto.request.NewComplaintRequest;
import com.project.helpdesk.dto.request.UpdateComplaintRequest;
import com.project.helpdesk.dto.response.ComplaintDtoResponse;

public interface ComplaintService {
    ComplaintDtoResponse createComplaint(NewComplaintRequest request);
    ComplaintDtoResponse updateComplaint(UpdateComplaintRequest request);
    void deleteComplaint(String id);
    void updateComplaintStatus(String id, Integer status);
}
