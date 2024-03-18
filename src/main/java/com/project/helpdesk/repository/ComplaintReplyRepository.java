package com.project.helpdesk.repository;

import com.project.helpdesk.entity.ComplaintReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface ComplaintReplyRepository extends JpaRepository<ComplaintReply, String> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO t_complaint_reply(id, reply_date, reply_title, reply_description, complaint_id, employee_id) VALUES (:id, :date, :title, :description, :complaintId, :employeeId)", nativeQuery = true)
    void createComplaintReply(String id, Date date, String title, String description, String complaintId, String employeeId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE t_complaint_reply SET reply_title = :title, reply_description = :description WHERE id = :id", nativeQuery = true)
    void updateComplaintReply(String id, String title, String description);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM t_complaint_reply WHERE id = :id", nativeQuery = true)
    void deleteComplaintReply(String id);

//    @Query(value = "SELECT * FROM t_complaint_reply WHERE complaint_id = :complaintId", nativeQuery = true)
//    Page<ComplaintReply> getComplaintReplies(String complaintId, Pageable pageable);
}
