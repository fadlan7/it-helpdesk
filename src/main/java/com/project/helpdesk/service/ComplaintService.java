package com.project.helpdesk.service;

import com.project.helpdesk.dto.request.NewComplaintRequest;
import com.project.helpdesk.dto.request.SearchComplaintRequest;
import com.project.helpdesk.dto.request.UpdateComplaintRequest;
import com.project.helpdesk.dto.response.ComplaintDtoResponse;
import com.project.helpdesk.entity.Complaint;
import org.springframework.data.domain.Page;

public interface ComplaintService {
    ComplaintDtoResponse createComplaint(NewComplaintRequest request);
    ComplaintDtoResponse updateComplaint(UpdateComplaintRequest request);
    void deleteComplaint(String id);
    void updateComplaintStatus(String id, Integer status);
    Complaint getComplaintById(String id);
    Page<ComplaintDtoResponse> getAllComplaints(SearchComplaintRequest request);
}
