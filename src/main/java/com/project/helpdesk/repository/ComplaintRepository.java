package com.project.helpdesk.repository;

import com.project.helpdesk.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, String> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO m_complaint(id, complaint_date, complaint_title, complaint_description, complaint_status, employee_id, image_id) VALUES (:id, :date, :title, :description, :status, :employeeId, :imageId)", nativeQuery = true)
    void createComplaint(String id, String title, String description, Date date, String status, String employeeId, String imageId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE m_complaint SET complaint_title = :title, complaint_description = :description, image_id = :imageId WHERE id = :id", nativeQuery = true)
    void updateComplaintWithImage(String id, String title, String description, String imageId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE m_complaint SET complaint_title = :title, complaint_description = :description WHERE id = :id", nativeQuery = true)
    void updateComplaintWithoutImage(String id, String title, String description);

    @Query(value = "SELECT * FROM m_complaint WHERE id = :id", nativeQuery = true)
    Optional<Complaint> getComplaintById(String id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM m_complaint WHERE id = :id", nativeQuery = true)
    void deleteComplaint(String id);
}
