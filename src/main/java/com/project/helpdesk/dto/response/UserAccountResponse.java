package com.project.helpdesk.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAccountResponse {
    private String username;
    private Boolean isEnable;
    private List<String> roles;
}