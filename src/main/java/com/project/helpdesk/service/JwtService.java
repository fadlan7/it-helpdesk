package com.project.helpdesk.service;

import com.project.helpdesk.dto.response.JwtClaims;
import com.project.helpdesk.entity.UserAccount;

public interface JwtService {
    String generateToken(UserAccount userAccount);
    boolean verifyJwtToken(String bearerToken);
    JwtClaims getClaimsByToken(String bearerToken);
}

