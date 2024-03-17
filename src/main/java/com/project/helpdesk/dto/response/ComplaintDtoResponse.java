package com.project.helpdesk.dto.response;

import java.util.Date;

public class ComplaintDtoResponse {
    private String id;
    private EmployeeResponse employee;
    private Date complaintDate;
    private String title;
    private String description;
    private ImageResponse complaintImage;
    private String status;
    private ComplaintReplyDtoResponse reply;
}
