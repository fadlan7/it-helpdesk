package com.project.helpdesk.dto.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplaintReplyDtoResponse {
    private String id;
    private Date replyDate;
    private String title;
    private String description;
    private String complaintId;
    private String employeeId;
}
