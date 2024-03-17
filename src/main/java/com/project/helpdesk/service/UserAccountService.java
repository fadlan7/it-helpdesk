package com.project.helpdesk.service;

import com.project.helpdesk.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAccountService extends UserDetailsService {
    UserAccount getByUserId(String id);

    UserAccount getByContext();
}
