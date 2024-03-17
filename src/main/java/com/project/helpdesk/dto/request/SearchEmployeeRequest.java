package com.project.helpdesk.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchEmployeeRequest {
    private Integer page;
    private Integer size;
    private String sortBy;
    private String direction;
    private String name;
    private String mobilePhoneNo;
    private Boolean isMember;
}

