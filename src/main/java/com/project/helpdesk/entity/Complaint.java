package com.project.helpdesk.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @Column(name = "user_id")
    private User user;

    @Column(name = "report_title")
    private String title;

    @Column(name = "report_description", columnDefinition = "TEXT")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "report_date", updatable = false)
    private Date reportDate;

    @Column(name = "report_status")
    private String status;

    @OneToOne
    @JoinColumn(name = "response_id", unique = true)
    private ReportResponse response;
}
