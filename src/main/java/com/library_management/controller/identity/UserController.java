package com.library_management.controller.identity;

import com.library_management.dto.request.identity.UserCreationRequest;
import com.library_management.dto.request.identity.UserUpdateRequest;
import com.library_management.dto.response.global.ApiResponse;
import com.library_management.dto.response.identity.UserResponse;
import com.library_management.entity.identity.User;
import com.library_management.excel.UserExcelExporter;
import com.library_management.excel.UserExcelImport;
import com.library_management.service.identity.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "User Management")
public class UserController {
    UserService userService;

    @Operation(
            summary = "Create new user",
            description = "Enter required information to create new user"
    )
    @PostMapping
    ApiResponse<UserResponse> create(@RequestBody @Valid UserCreationRequest request) {
        log.info("Controller: Creating User..........");
        return ApiResponse.<UserResponse>builder()
                .result(userService.createUser(request))
                .build();
    }

    @Operation(
            summary = "Get all users",
            description = "Return all users in database"
    )
    @GetMapping
    ApiResponse<List<UserResponse>> getAll() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        log.info("Username: {}", authentication.getName());
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @Operation(
            summary = "Get user information by ID",
            description = "Enter user id to see their information"
    )
    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getById(@PathVariable("userId") String userId) {
        log.info("Controller: Getting User By ID..........");
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUser(userId))
                .build();
    }

    @Operation(
            summary = "Update user",
            description = "Enter user id to update their information"
    )
    @PutMapping("/{userId}")
    ApiResponse<UserResponse> update(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        log.info("Controller: Updating User By ID..........");
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userId, request))
                .build();
    }

    @Operation(
            summary = "Delete user",
            description = "Enter user id to delete their information"
    )
    @DeleteMapping("/{userId}")
    ApiResponse<String> delete(@PathVariable String userId) {
        userService.deleteUser(userId);
        log.info("Controller: Deleting User By ID..........");
        return ApiResponse.<String>builder().result("User has been deleted").build();
    }

    @Operation(
            summary = "Get own player information",
            description = "Enter token of the user and return the information of that user's token"
    )
    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        log.info("Controller: Getting Own User Information..........");
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @Operation(
            summary = "Export user data to excel"
    )
    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<User> listUsers = userService.listAllForExcel();

        UserExcelExporter excelExporter = new UserExcelExporter(listUsers);

        excelExporter.export(response);
    }

    @Operation(
            summary = "Import user data by excel"
    )
    @PostMapping("/import/excel")
    public ResponseEntity<?> uploadFile(@RequestParam("data") MultipartFile file) {
        String message = "";
        if (UserExcelImport.hasExcelFormat(file)) {
            try {
                userService.saveFromExcel(file);
                log.info("Controller: Saving data.....");
                message = "The Excel file is uploaded: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception exp) {
                message = "The Excel file is not upload: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
        } else {
            message = "Please upload an excel file!";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }
}
