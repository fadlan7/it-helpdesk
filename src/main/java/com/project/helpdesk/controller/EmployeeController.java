package com.project.helpdesk.controller;

import com.project.helpdesk.constant.ApiUrl;
import com.project.helpdesk.constant.ResponseMessage;
import com.project.helpdesk.dto.request.NewEmployeeRequest;
import com.project.helpdesk.dto.request.SearchEmployeeRequest;
import com.project.helpdesk.dto.request.UpdateEmployeeRequest;
import com.project.helpdesk.dto.response.CommonResponse;
import com.project.helpdesk.dto.response.EmployeeResponse;
import com.project.helpdesk.dto.response.GetEmployeeResponse;
import com.project.helpdesk.dto.response.PagingResponse;
import com.project.helpdesk.entity.Employee;
import com.project.helpdesk.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiUrl.EMPLOYEE_URL)
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<CommonResponse<EmployeeResponse>> createNewEmployee(@RequestBody NewEmployeeRequest request) {

        EmployeeResponse newEmployee = employeeService.createEmployee(request.getId());
        CommonResponse<EmployeeResponse> response = CommonResponse.<EmployeeResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_SAVE_DATA)
                .data(newEmployee)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<EmployeeResponse>> updateEmployee(@RequestBody UpdateEmployeeRequest request) {
        EmployeeResponse updatedEmployee = employeeService.updateEmployee(request);

        CommonResponse<EmployeeResponse> response = CommonResponse.<EmployeeResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .data(updatedEmployee)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TECHNICIAN')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<Employee>> getEmployeeById(@PathVariable String id) {
        Employee employee = employeeService.getEmployeeById(id);

        CommonResponse<Employee> response = CommonResponse.<Employee>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(employee)
                .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TECHNICIAN')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<GetEmployeeResponse>>> getAllEmployee(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        SearchEmployeeRequest request = SearchEmployeeRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();
        Page<GetEmployeeResponse> employees = employeeService.getAllEmployees(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(employees.getTotalPages())
                .totalElement(employees.getTotalElements())
                .page(employees.getPageable().getPageNumber() + 1)
                .size(employees.getPageable().getPageSize())
                .hasNext(employees.hasNext())
                .hasPrevious(employees.hasPrevious())
                .build();

        CommonResponse<List<GetEmployeeResponse>> response = CommonResponse.<List<GetEmployeeResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(employees.getContent())
                .paging(pagingResponse)
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
