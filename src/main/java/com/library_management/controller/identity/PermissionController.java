package com.library_management.controller.identity;

import java.util.List;

import com.library_management.dto.request.identity.PermissionRequest;
import com.library_management.dto.response.global.ApiResponse;
import com.library_management.dto.response.identity.PermissionResponse;
import com.library_management.service.identity.PermissionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Permission Management")
@Slf4j
public class PermissionController {
    PermissionService permissionService;

    @Operation(
            summary = "Create Permission",
            description = "Enter required data to create permission"
    )
    @PostMapping
    ApiResponse<PermissionResponse> create(@RequestBody @Valid PermissionRequest request) {
        log.info("Controller: Creating Permission..........");
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }

    @Operation(
            summary = "Get all permissions",
            description = "Return all permissions in database"
    )
    @GetMapping
    ApiResponse<List<PermissionResponse>> getAll() {
        log.info("Controller: Getting All Permission..........");
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @Operation(
            summary = "Delete permission",
            description = "Enter id of the permission to delete it"
    )
    @DeleteMapping("/{permission}")
    ApiResponse<Void> delete(@PathVariable String permission) {
        permissionService.delete(permission);
        log.info("Controller: Deleting Permission..........");
        return ApiResponse.<Void>builder().build();
    }
}

