package com.project.helpdesk.controller;

import com.project.helpdesk.constant.ApiUrl;
import com.project.helpdesk.constant.ResponseMessage;
import com.project.helpdesk.dto.request.NewUserRequest;
import com.project.helpdesk.dto.request.UpdateUserRequest;
import com.project.helpdesk.dto.response.CommonResponse;
import com.project.helpdesk.dto.response.UserResponse;
import com.project.helpdesk.entity.User;
import com.project.helpdesk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiUrl.USER_URL)
public class UserController {
    private final UserService userService;

//    @PostMapping
//    public void createUser(String name, String mobilePhoneNo, String division) {
//        userService.createUser(name, mobilePhoneNo, division);
//    }

    @PostMapping
    public ResponseEntity<CommonResponse<UserResponse>> createNewUser(@RequestBody NewUserRequest request) {

        UserResponse newProduct = userService.createUser(request.getName(), request.getMobilePhone(), request.getDivision());
        CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_SAVE_DATA)
                .data(newProduct)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<UserResponse>> updateCustomer(@RequestBody UpdateUserRequest request) {
        UserResponse updatedUser = userService.updateUser(request.getId(), request.getName(), request.getMobilePhone(), request.getDivision());

        CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .data(updatedUser)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<User>> getUserById(@PathVariable String id) {
        User customer = userService.getUserById(id);

        CommonResponse<User> response = CommonResponse.<User>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(customer)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteById(@PathVariable String id) {
        userService.deleteUser(id);

        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_DELETE_DATA)
                .build();

        return ResponseEntity.ok(response);
    }
}
