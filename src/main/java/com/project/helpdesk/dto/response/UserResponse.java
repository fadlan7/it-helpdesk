package com.project.helpdesk.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private String id;
    private String name;
    private String mobilePhone;
    private String division;
//    private String userAccountId;
}
