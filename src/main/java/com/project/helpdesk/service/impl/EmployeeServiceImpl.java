package com.project.helpdesk.service.impl;

import com.project.helpdesk.constant.ResponseMessage;
import com.project.helpdesk.dto.request.SearchEmployeeRequest;
import com.project.helpdesk.dto.request.UpdateEmployeeRequest;
import com.project.helpdesk.dto.response.EmployeeResponse;
import com.project.helpdesk.dto.response.GetEmployeeResponse;
import com.project.helpdesk.dto.response.UserAccountResponse;
import com.project.helpdesk.entity.Employee;
import com.project.helpdesk.entity.UserAccount;
import com.project.helpdesk.repository.EmployeeRepository;
import com.project.helpdesk.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public EmployeeResponse createEmployee(String userAccountId) {
        UUID generatedId = UUID.randomUUID();
        employeeRepository.createEmployee(generatedId.toString(), userAccountId);

        Employee newEmployee = Employee.builder()
                .id(generatedId.toString())
                .userAccount(UserAccount.builder().id(userAccountId).build())
                .build();

        return convertEmployeeToEmployeeResponse(newEmployee);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public EmployeeResponse updateEmployee(UpdateEmployeeRequest request) {
        findByIdOrThrowNotFound(request.getId());

        employeeRepository.updateEmployee(request.getId(), request.getName(), request.getMobilePhone(), request.getDivision());

        return null;
    }

    @Override
    public Employee getEmployeeById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public Employee getEmployeeByUserAccountId(String userAccountId) {
        return employeeRepository.getEmployeeByUserAccountId(userAccountId);
    }

    @Override
    public void deleteEmployee(String id) {
        findByIdOrThrowNotFound(id);
        employeeRepository.deleteEmployee(id);
    }

    @Override
    public Page<GetEmployeeResponse> getAllEmployees(SearchEmployeeRequest request) {
        if (request.getPage() <= 0) request.setPage(1);

        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize(), sort);

        Page<Employee> employees = employeeRepository.getAllEmployees(pageable);

        List<GetEmployeeResponse> employeeResponses = employees.getContent().stream().map(account -> {
            UserAccountResponse userAccountResponses =
                    Optional.ofNullable(account.getUserAccount())
                            .map(userAccount -> UserAccountResponse.builder()
                                    .username(userAccount.getUsername())
                                    .isEnable(userAccount.getIsEnable())
                                    .roles(userAccount.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                                    .build())
                            .orElse(null);

            return GetEmployeeResponse.builder()
                    .id(account.getId())
                    .name(account.getName())
                    .mobilePhone(account.getMobilePhone())
                    .division(account.getDivision())
                    .userAccount(userAccountResponses)
                    .build();
        }).toList();
        return new PageImpl<>(employeeResponses, pageable, employees.getTotalElements());
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
                .userAccountId(newEmployee.getUserAccount().getId())
                .build();
    }
}
