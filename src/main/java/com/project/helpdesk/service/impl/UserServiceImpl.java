package com.project.helpdesk.service.impl;

import com.project.helpdesk.constant.ResponseMessage;
import com.project.helpdesk.dto.response.UserResponse;
import com.project.helpdesk.entity.User;
import com.project.helpdesk.repository.UserRepository;
import com.project.helpdesk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse createUser(String name, String mobilePhoneNo, String division) {
        UUID generatedId = UUID.randomUUID();
        userRepository.createUser(generatedId.toString(), name, mobilePhoneNo, division);

        User newUser = new User();
        newUser.setId(generatedId.toString());
        newUser.setName(name);
        newUser.setMobilePhone(mobilePhoneNo);
        newUser.setDivision(division);

        return convertUserToUserResponse(newUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse updateUser(String id, String name, String mobilePhoneNo, String division) {
        findByIdOrThrowNotFound(id);

        userRepository.updateUser(id, name, mobilePhoneNo, division);

        return null;
    }

    @Override
    public User getUserById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public void deleteUser(String id) {
        findByIdOrThrowNotFound(id);
        userRepository.deleteUser(id);
    }

    private User findByIdOrThrowNotFound(String id) {
        User user = userRepository.getUserById(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
        }
        return user;
    }

    private UserResponse convertUserToUserResponse(User newUser) {
        return UserResponse.builder()
                .id(newUser.getId())
                .name(newUser.getName())
                .mobilePhone(newUser.getMobilePhone())
                .division(newUser.getDivision())
//                .userAccountId(newUser.getUserAccount().getId() == null ? null : "-")
                .build();
    }
}
