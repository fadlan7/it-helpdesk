package com.project.helpdesk.dto.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetComplaintReplyDtoResponse {
    private String id;
    private Date replyDate;
    private String title;
    private String description;
    private SearchEmployeeResponse technician;
}
