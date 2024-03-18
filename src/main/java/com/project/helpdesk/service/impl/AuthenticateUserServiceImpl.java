package com.project.helpdesk.service.impl;

import com.project.helpdesk.constant.ResponseMessage;
import com.project.helpdesk.dto.request.UpdateEmployeeRequest;
import com.project.helpdesk.entity.Employee;
import com.project.helpdesk.entity.UserAccount;
import com.project.helpdesk.service.EmployeeService;
import com.project.helpdesk.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticateUserServiceImpl {
    private final EmployeeService employeeService;
    private final UserAccountService userAccountService;

    public boolean hasSameId(UpdateEmployeeRequest request) {
        Employee currentUser = employeeService.getEmployeeById(request.getId());
        UserAccount userAccount = userAccountService.getByContext();

        if (!userAccount.getId().equals(currentUser.getUserAccount().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ResponseMessage.ERROR_FORBIDDEN);
        }
        return true;
    }
}
