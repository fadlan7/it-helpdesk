package com.project.helpdesk.service.impl;


import com.project.helpdesk.entity.Role;
import com.project.helpdesk.repository.RoleRepository;
import com.project.helpdesk.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createUserRoleRelation(String userId, String roleId) {
        roleRepository.createUserRole(userId, roleId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createRole(String id, String roleName) {
        roleRepository.createRole(id, roleName);
    }

    @Transactional(readOnly = true)
    @Override
    public Role getRoleName(String role) {
        return roleRepository.findByRoleName(role);
    }
}