package com.project.helpdesk.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewComplaintRequest {
    private String title;
    private String description;
    private MultipartFile complaintImage;
}
