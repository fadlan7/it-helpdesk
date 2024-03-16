package com.project.helpdesk.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewUserRequest {
    private String id;

    private String name;

    private String mobilePhone;

    private String division;
}
