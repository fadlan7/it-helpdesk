package com.project.helpdesk.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewComplaintReplyDtoRequest {
    private String title;
    private String Description;
    private String complaintId;
}
