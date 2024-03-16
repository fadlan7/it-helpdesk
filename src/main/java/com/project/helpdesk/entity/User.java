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
@Table(name = TableName.USER)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "mobile_phone")
    private String mobilePhoneNo;

    @Column(name = "company_division")
    private String division;

    @OneToOne
    @JoinColumn(name = "user_account_id", unique = true)
    private UserAccount userAccount;
}
