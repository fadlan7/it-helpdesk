package com.project.helpdesk.service;

import com.project.helpdesk.dto.request.SearchEmployeeRequest;
import com.project.helpdesk.dto.response.EmployeeResponse;
import com.project.helpdesk.entity.Employee;
import org.springframework.data.domain.Page;

public interface EmployeeService {
    EmployeeResponse createEmployee(String name, String mobilePhoneNo, String division);

    EmployeeResponse updateEmployee(String id, String name, String mobilePhoneNo, String division);

    Employee getEmployeeById(String id);

    void deleteEmployee(String id);

    Page<EmployeeResponse> getAllEmployees(SearchEmployeeRequest employee);
}
