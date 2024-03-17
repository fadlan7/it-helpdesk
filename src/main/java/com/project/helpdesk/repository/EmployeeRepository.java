package com.project.helpdesk.repository;

import com.project.helpdesk.dto.response.EmployeeResponse;
import com.project.helpdesk.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String>, JpaSpecificationExecutor<Employee> {

//    @Modifying
//    @Query(value = "INSERT INTO m_employee(id, name, mobile_phone, company_division) VALUES (:id, :name, :mobilePhoneNo, :division)", nativeQuery = true)
//    @Transactional
//    void createEmployee(String id, String name, String mobilePhoneNo, String division);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO m_employee(id, user_account_id) VALUES (:id, :userAccountId)", nativeQuery = true)
    void createEmployee(String id, String userAccountId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE m_employee SET name = :name, mobile_phone = :mobilePhoneNo, company_division = :division WHERE id = :id", nativeQuery = true)
    void updateEmployee(String id, String name, String mobilePhoneNo, String division);

    @Query(value = "SELECT * FROM m_employee WHERE id = :id", nativeQuery = true)
    Employee getEmployeeById(String id);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM m_employee WHERE id = :id", nativeQuery = true)
    void deleteEmployee(String id);

//    @Query(value = "SELECT * FROM m_employee", nativeQuery = true)
//    Page<EmployeeResponse> getAllEmployees();
}
