package com.project.helpdesk.entity;


import com.project.helpdesk.constant.TableName;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = TableName.REPORT)
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "complaint_date", updatable = false)
    private Date ComplaintDate;

    @Column(name = "complaint_title")
    private String title;

    @Column(name = "complaint_description", columnDefinition = "TEXT")
    private String description;

    @OneToOne
    @JoinColumn(name = "image_id", unique = true)
    private Image complaintImage;

    @Column(name = "complaint_status")
    private String status;

    @OneToOne
    @JoinColumn(name = "response_id", unique = true)
    private ComplaintReply reply;
}
