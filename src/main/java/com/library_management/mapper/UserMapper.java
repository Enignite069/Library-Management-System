package com.library_management.mapper;

import com.library_management.dto.request.identity.UserCreationRequest;
import com.library_management.dto.request.identity.UserUpdateRequest;
import com.library_management.dto.response.identity.UserResponse;
import com.library_management.entity.identity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
