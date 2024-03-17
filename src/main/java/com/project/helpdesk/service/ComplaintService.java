package com.project.helpdesk.service;

import com.project.helpdesk.dto.request.NewComplaintRequest;
import com.project.helpdesk.dto.response.ComplaintDtoResponse;

public interface ComplaintService {
    ComplaintDtoResponse createComplaint(NewComplaintRequest request);
}
