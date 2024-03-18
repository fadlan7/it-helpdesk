package com.project.helpdesk.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchComplaintRequest {
    private Integer page;
    private Integer size;
    private String sortBy;
    private String direction;
}
