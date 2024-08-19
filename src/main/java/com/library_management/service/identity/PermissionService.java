package com.library_management.service.identity;

import java.util.List;

import com.library_management.dto.request.identity.PermissionRequest;
import com.library_management.dto.response.identity.PermissionResponse;
import com.library_management.entity.identity.Permission;
import com.library_management.mapper.PermissionMapper;
import com.library_management.repository.identity.PermissionRepository;

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
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        log.info("Service: creating permission...");
        return permissionMapper.toPermissionResponse(permission);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        log.info("Service: Get all permissions");
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String permission) {
        permissionRepository.deleteById(permission);
        log.info("Service: Delete permission");
    }
}
