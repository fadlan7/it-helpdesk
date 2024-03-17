package com.project.helpdesk.repository;

import com.project.helpdesk.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO m_role (id, role) VALUES (:id, :role)", nativeQuery = true)
    void createRole(String id, String role);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO m_user_account_role (user_account_id, role_id) VALUES (:userId, :roleId)", nativeQuery = true)
    void createUserRole(String userId, String roleId);

    @Query(value = "SELECT * FROM m_role WHERE role = :role", nativeQuery = true)
    Role findByRoleName(String role);
}

