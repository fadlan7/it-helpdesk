package com.project.helpdesk.dto.request;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateComplaintReplyDtoRequest {
    private String id;
    private String title;
    private String description;
}
