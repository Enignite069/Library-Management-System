package com.library_management.service.identity;

import java.util.HashSet;
import java.util.List;

import com.library_management.dto.request.identity.RoleRequest;
import com.library_management.dto.response.identity.RoleResponse;
import com.library_management.mapper.RoleMapper;
import com.library_management.repository.identity.PermissionRepository;
import com.library_management.repository.identity.RoleRepository;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);
        var permission = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permission));
        role = roleRepository.save(role);
        log.info("Service: creating Role");
        return roleMapper.toRoleResponse(role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<RoleResponse> getAll() {
        log.info("Service: Get all roles");
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String role) {
        log.info("Service: Delete role");
        roleRepository.deleteById(role);
    }
}
