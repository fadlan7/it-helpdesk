package com.project.helpdesk.service;

import com.project.helpdesk.entity.Role;

public interface RoleService {
    void createUserRoleRelation(String userId, String roleId);
    void createRole(String id, String roleName);
    Role getRoleName(String role);
}
