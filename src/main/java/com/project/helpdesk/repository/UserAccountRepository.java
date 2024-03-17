package com.project.helpdesk.repository;

import com.project.helpdesk.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {
    Optional<UserAccount> findByUsername(String username);

    @Query(value = "SELECT * FROM m_user_account WHERE id = :id", nativeQuery = true)
    UserAccount getUserAccountById(String id);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO m_user_account(id, username, password, is_enable) VALUES (:id, :username, :password, :is_enable)", nativeQuery = true)
    void createUserAccount(String id, String username, String password, boolean is_enable);
}
