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
@Table(name = TableName.RESPONSE)
public class ComplaintReply {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reply_date", updatable = false)
    private Date replyDate;

    @Column(name = "reply_title")
    private String title;

    @Column(name = "reply_description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    //    @OneToOne(mappedBy = "reply")
    @OneToOne
    @JoinColumn(name = "complaint_id", referencedColumnName = "id")
    private Complaint complaint;
}
