package com.project.helpdesk.service.impl;

import com.project.helpdesk.constant.UserRole;
import com.project.helpdesk.dto.request.AuthRequest;
import com.project.helpdesk.dto.response.LoginResponse;
import com.project.helpdesk.dto.response.RegisterResponse;
import com.project.helpdesk.entity.Employee;
import com.project.helpdesk.entity.Role;
import com.project.helpdesk.entity.UserAccount;
import com.project.helpdesk.repository.UserAccountRepository;
import com.project.helpdesk.service.AuthService;
import com.project.helpdesk.service.EmployeeService;
import com.project.helpdesk.service.JwtService;
import com.project.helpdesk.service.RoleService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserAccountRepository userAccountRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeService employeeService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Value("${it_helpdesk.username.superadmin}")
    private String superAdminUsername;
    @Value("${it_helpdesk.password.superadmin}")
    private String superAdminPassword;

    @Transactional(rollbackFor = Exception.class)
    @PostConstruct
    public void initRole() {
        List<String> roleNames = List.of(
                UserRole.ROLE_SUPER_ADMIN.name(),
                UserRole.ROLE_TECHNICIAN.name(),
                UserRole.ROLE_EMPLOYEE.name()
        );

        for (String roleName : roleNames) {
            Role role = roleService.getRoleName(roleName);
            UUID generatedId = UUID.randomUUID();
            if (role == null) {
                roleService.createRole(generatedId.toString(), roleName);
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @PostConstruct
    public void initSuperAdmin() {
        Optional<UserAccount> currentUser = userAccountRepository.findByUsername(superAdminUsername);
        if (currentUser.isPresent()) return;
        UUID generatedId = UUID.randomUUID();

        UserAccount account = UserAccount.builder()
                .username(superAdminUsername)
                .password(passwordEncoder.encode(superAdminPassword))
                .isEnable(true)
                .build();

        userAccountRepository.createUserAccount(generatedId.toString(), account.getUsername(), account.getPassword(), account.getIsEnable());

        final String userId = generatedId.toString();

        List<String> roleNames = List.of(
                UserRole.ROLE_SUPER_ADMIN.name(),
                UserRole.ROLE_TECHNICIAN.name(),
                UserRole.ROLE_EMPLOYEE.name()
        );

        for (String roleName : roleNames) {
            Role role = roleService.getRoleName(roleName);
            if (role != null) {
                roleService.createUserRoleRelation(userId, role.getId());
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse register(AuthRequest request) {

        Role role = roleService.getRoleName(UserRole.ROLE_EMPLOYEE.name());
        String hashPassword = passwordEncoder.encode(request.getPassword());
        UUID generatedId = UUID.randomUUID();

        UserAccount account = UserAccount.builder()
                .username(request.getUsername())
                .password(hashPassword)
                .role(List.of(role))
                .isEnable(true)
                .build();

        userAccountRepository.createUserAccount(generatedId.toString(), account.getUsername(), account.getPassword(), account.getIsEnable());

        Employee employee = Employee.builder()
                .userAccount(account)
                .build();
        employeeService.createEmployee(employee.getId());

        roleService.createUserRoleRelation(generatedId.toString(), role.getId());

        List<String> roles = account.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return RegisterResponse.builder()
                .username(account.getUsername())
                .roles(roles)
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse registerTechnician(AuthRequest request) {

        String hashPassword = passwordEncoder.encode(request.getPassword());
        UUID generatedId = UUID.randomUUID();

        UserAccount account = UserAccount.builder()
                .username(request.getUsername())
                .password(hashPassword)
                .role(List.of(
                        roleService.getRoleName(UserRole.ROLE_TECHNICIAN.name()),
                        roleService.getRoleName(UserRole.ROLE_EMPLOYEE.name())
                ))
                .isEnable(true)
                .build();

        userAccountRepository.createUserAccount(generatedId.toString(), account.getUsername(), account.getPassword(), account.getIsEnable());

        Employee employee = Employee.builder()
                .userAccount(account)
                .build();
        employeeService.createEmployee(employee.getId());


        final String userId = generatedId.toString();

        List<String> roleNames = List.of(
                UserRole.ROLE_TECHNICIAN.name(),
                UserRole.ROLE_EMPLOYEE.name()
        );

        for (String roleName : roleNames) {
            Role role = roleService.getRoleName(roleName);
            if (role != null) {
                roleService.createUserRoleRelation(userId, role.getId());
            }
        }

        List<String> roles = account.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        return RegisterResponse.builder()
                .username(account.getUsername())
                .roles(roles)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public LoginResponse login(AuthRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        Authentication authenticate = authenticationManager.authenticate(authentication);
        UserAccount userAccount = (UserAccount) authenticate.getPrincipal();

        String token = jwtService.generateToken(userAccount);

        return LoginResponse.builder()
                .username(userAccount.getUsername())
                .roles(userAccount.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .token(token)
                .build();
    }
}
