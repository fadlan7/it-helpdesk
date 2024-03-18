package com.project.helpdesk.dto.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetComplaintDtoResponse {
    private String id;
    private SearchEmployeeResponse employee;
    private Date complaintDate;
    private String title;
    private String description;
    private ImageResponse complaintImage;
    private String status;
    private GetComplaintReplyDtoResponse complaintReply;
}
