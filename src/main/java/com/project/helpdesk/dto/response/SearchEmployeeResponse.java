package com.project.helpdesk.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchEmployeeResponse {
    private String id;
    private String name;
}