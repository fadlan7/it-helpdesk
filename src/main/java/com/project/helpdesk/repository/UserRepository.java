package com.project.helpdesk.repository;

import com.project.helpdesk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    @Modifying
    @Query(value = "INSERT INTO m_user(id, name, mobile_phone, company_division) VALUES (:id, :name, :mobilePhoneNo, :division)", nativeQuery = true)
    @Transactional
    void createUser(String id, String name, String mobilePhoneNo, String division);

    @Modifying
    @Query(value = "UPDATE m_user SET name = :name, mobile_phone = :mobilePhoneNo, company_division = :division WHERE id = :id", nativeQuery = true)
    @Transactional
    void updateUser(String id, String name, String mobilePhoneNo, String division);

    @Query(value = "SELECT * FROM m_user WHERE id = :id", nativeQuery = true)
    User getUserById(String id);

    @Modifying
    @Query(value = "DELETE FROM m_user WHERE id = :id", nativeQuery = true)
    @Transactional
    void deleteUser(String id);
}
