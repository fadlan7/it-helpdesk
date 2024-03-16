package com.project.helpdesk.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.helpdesk.constant.TableName;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = TableName.RESPONSE)
public class ReportResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "report_title")
    private String title;

    @Column(name = "report_description", columnDefinition = "TEXT")
    private String description;

    @OneToOne(mappedBy = "response")
    private Complaint complaint;
}
