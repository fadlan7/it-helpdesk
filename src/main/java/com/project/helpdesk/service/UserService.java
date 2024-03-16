package com.project.helpdesk.service;

import com.project.helpdesk.dto.request.SearchUserRequest;
import com.project.helpdesk.dto.response.UserResponse;
import com.project.helpdesk.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {
    UserResponse createUser(String name, String mobilePhoneNo, String division);

    UserResponse updateUser(String id, String name, String mobilePhoneNo, String division);

    User getUserById(String id);

    void deleteUser(String id);

    Page<UserResponse> getAll(SearchUserRequest customer);
}
