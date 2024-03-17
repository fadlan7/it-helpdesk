package com.project.helpdesk.service;

import com.project.helpdesk.dto.request.AuthRequest;
import com.project.helpdesk.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(AuthRequest request);
}
