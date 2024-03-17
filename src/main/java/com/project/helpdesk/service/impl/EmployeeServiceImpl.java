package com.project.helpdesk.service.impl;

import com.project.helpdesk.constant.ResponseMessage;
import com.project.helpdesk.dto.request.SearchEmployeeRequest;
import com.project.helpdesk.dto.response.EmployeeResponse;
import com.project.helpdesk.entity.Employee;
import com.project.helpdesk.repository.EmployeeRepository;
import com.project.helpdesk.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public EmployeeResponse createEmployee(String name, String mobilePhoneNo, String division) {
        UUID generatedId = UUID.randomUUID();
        employeeRepository.createEmployee(generatedId.toString(), name, mobilePhoneNo, division);

        Employee newEmployee = new Employee();
        newEmployee.setId(generatedId.toString());
        newEmployee.setName(name);
        newEmployee.setMobilePhone(mobilePhoneNo);
        newEmployee.setDivision(division);

        return convertEmployeeToEmployeeResponse(newEmployee);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public EmployeeResponse updateEmployee(String id, String name, String mobilePhoneNo, String division) {
        findByIdOrThrowNotFound(id);

        employeeRepository.updateEmployee(id, name, mobilePhoneNo, division);

        return null;
    }

    @Override
    public Employee getEmployeeById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public void deleteEmployee(String id) {
        findByIdOrThrowNotFound(id);
        employeeRepository.deleteEmployee(id);
    }

    @Override
    public Page<EmployeeResponse> getAllEmployees(SearchEmployeeRequest employee) {
        return null;
    }

    private Employee findByIdOrThrowNotFound(String id) {
        Employee user = employeeRepository.getEmployeeById(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
        }
        return user;
    }

    private EmployeeResponse convertEmployeeToEmployeeResponse(Employee newEmployee) {
        return EmployeeResponse.builder()
                .id(newEmployee.getId())
                .name(newEmployee.getName())
                .mobilePhone(newEmployee.getMobilePhone())
                .division(newEmployee.getDivision())
//                .userAccountId(newEmployee.getEmployeeAccount().getId() == null ? null : "-")
                .build();
    }
}
