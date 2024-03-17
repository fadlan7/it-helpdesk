package com.project.helpdesk.repository;

import com.project.helpdesk.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, String> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO m_complaint(id, complaint_date, complaint_title, complaint_description, complaint_status, employee_id, image_id) VALUES (:id, :date, :title, :description, :status, :employeeId, :imageId)", nativeQuery = true)
    void createComplaint(String id, String title, String description, Date date, String status, String employeeId, String imageId);
}
