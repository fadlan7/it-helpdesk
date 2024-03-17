package com.project.helpdesk.controller;

import com.project.helpdesk.constant.ApiUrl;
import com.project.helpdesk.constant.ResponseMessage;
import com.project.helpdesk.dto.request.NewEmployeeRequest;
import com.project.helpdesk.dto.request.UpdateEmployeeRequest;
import com.project.helpdesk.dto.response.CommonResponse;
import com.project.helpdesk.dto.response.EmployeeResponse;
import com.project.helpdesk.entity.Employee;
import com.project.helpdesk.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiUrl.Employee_URL)
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<CommonResponse<EmployeeResponse>> createNewEmployee(@RequestBody NewEmployeeRequest request) {

        EmployeeResponse newProduct = employeeService.createEmployee(request.getName(), request.getMobilePhone(), request.getDivision());
        CommonResponse<EmployeeResponse> response = CommonResponse.<EmployeeResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_SAVE_DATA)
                .data(newProduct)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<EmployeeResponse>> updateCustomer(@RequestBody UpdateEmployeeRequest request) {
        EmployeeResponse updatedEmployee = employeeService.updateEmployee(request.getId(), request.getName(), request.getMobilePhone(), request.getDivision());

        CommonResponse<EmployeeResponse> response = CommonResponse.<EmployeeResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .data(updatedEmployee)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<Employee>> getEmployeeById(@PathVariable String id) {
        Employee customer = employeeService.getEmployeeById(id);

        CommonResponse<Employee> response = CommonResponse.<Employee>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(customer)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteById(@PathVariable String id) {
        employeeService.deleteEmployee(id);

        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_DELETE_DATA)
                .build();

        return ResponseEntity.ok(response);
    }
}
