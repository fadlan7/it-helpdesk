package com.project.helpdesk.service;

import com.project.helpdesk.dto.request.NewComplaintRequest;
import com.project.helpdesk.dto.request.SearchComplaintRequest;
import com.project.helpdesk.dto.request.UpdateComplaintRequest;
import com.project.helpdesk.dto.response.ComplaintDtoResponse;
import com.project.helpdesk.dto.response.GetComplaintDtoResponse;
import com.project.helpdesk.dto.response.GetComplaintReplyDtoResponse;
import com.project.helpdesk.entity.Complaint;
import org.springframework.data.domain.Page;

public interface ComplaintService {
    ComplaintDtoResponse createComplaint(NewComplaintRequest request);

    ComplaintDtoResponse updateComplaint(UpdateComplaintRequest request);

    void deleteComplaint(String id);

    void updateComplaintStatusWithPatch(String id, Integer status);

    void updateComplaintStatus(String id, String status);

    Complaint getComplaintById(String id);

    Page<GetComplaintDtoResponse> getAllComplaints(SearchComplaintRequest request);
}
