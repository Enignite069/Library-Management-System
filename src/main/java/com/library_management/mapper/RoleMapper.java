package com.library_management.mapper;

import com.library_management.dto.request.identity.RoleRequest;
import com.library_management.dto.response.identity.RoleResponse;
import com.library_management.entity.identity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
