package com.library_management.mapper;

import com.library_management.dto.request.identity.PermissionRequest;
import com.library_management.dto.response.identity.PermissionResponse;
import com.library_management.entity.identity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
