package com.project.helpdesk.service;

import com.project.helpdesk.dto.response.UserResponse;
import com.project.helpdesk.entity.User;

public interface UserService {
    UserResponse createUser(String name, String mobilePhoneNo, String division);
    UserResponse updateUser(String id, String name, String mobilePhoneNo, String division);
    User getUserById(String id);
    void deleteUser(String id);
}
