package com.library_management.controller.identity;

import com.library_management.dto.request.identity.RoleRequest;
import com.library_management.dto.response.global.ApiResponse;
import com.library_management.dto.response.identity.RoleResponse;
import com.library_management.service.identity.RoleService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Role Management")
@Hidden //hide controller in swagger ui
@Slf4j
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody @Valid RoleRequest request) {
        log.info("Controller: Creating Permission..........");
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        log.info("Controller: Getting All Roles..........");
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<Void> delete(@PathVariable String role) {
        roleService.delete(role);
        log.info("Controller: Deleting Role..........");
        return ApiResponse.<Void>builder().build();
    }
}
