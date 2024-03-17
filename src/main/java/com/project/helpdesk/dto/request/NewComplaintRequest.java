package com.project.helpdesk.dto.request;

import com.project.helpdesk.dto.response.ImageResponse;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

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
