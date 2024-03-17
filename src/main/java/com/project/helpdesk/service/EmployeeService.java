package com.project.helpdesk.service;

import com.project.helpdesk.dto.request.SearchEmployeeRequest;
import com.project.helpdesk.dto.request.UpdateEmployeeRequest;
import com.project.helpdesk.dto.response.EmployeeResponse;
import com.project.helpdesk.dto.response.GetEmployeeResponse;
import com.project.helpdesk.entity.Employee;
import com.project.helpdesk.entity.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    EmployeeResponse createEmployee(String userAccountId);

    EmployeeResponse updateEmployee(UpdateEmployeeRequest request);

    Employee getEmployeeById(String id);

    Employee getEmployeeByUserAccountId(String userAccountId);

    void deleteEmployee(String id);

    Page<GetEmployeeResponse> getAllEmployees(SearchEmployeeRequest request);
}
