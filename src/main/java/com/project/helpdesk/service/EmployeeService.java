package com.project.helpdesk.service;

import com.project.helpdesk.dto.request.SearchEmployeeRequest;
import com.project.helpdesk.dto.request.UpdateEmployeeRequest;
import com.project.helpdesk.dto.response.EmployeeResponse;
import com.project.helpdesk.entity.Employee;
import com.project.helpdesk.entity.UserAccount;
import org.springframework.data.domain.Page;

public interface EmployeeService {
    EmployeeResponse createEmployee(String userAccountId);

    EmployeeResponse updateEmployee(UpdateEmployeeRequest request);

    Employee getEmployeeById(String id);

    void deleteEmployee(String id);

    Page<EmployeeResponse> getAllEmployees(SearchEmployeeRequest employee);
}
