package com.project.helpdesk.service;

import com.project.helpdesk.dto.request.NewComplaintReplyDtoRequest;
import com.project.helpdesk.dto.request.UpdateComplaintReplyDtoRequest;
import com.project.helpdesk.dto.response.ComplaintDtoResponse;
import com.project.helpdesk.dto.response.ComplaintReplyDtoResponse;

public interface ComplaintReplyService {
    ComplaintReplyDtoResponse createComplaintReply(NewComplaintReplyDtoRequest request);
    ComplaintDtoResponse updateComplaintReply(UpdateComplaintReplyDtoRequest request);
    void deleteComplaintReply(String id);
}
