package com.library_management.service.identity;

import com.library_management.constant.PredefinedRole;
import com.library_management.dto.request.identity.UserCreationRequest;
import com.library_management.dto.request.identity.UserUpdateRequest;
import com.library_management.dto.response.identity.UserResponse;
import com.library_management.entity.identity.Role;
import com.library_management.entity.identity.User;
import com.library_management.excel.UserExcelImport;
import com.library_management.exception.AppException;
import com.library_management.exception.ErrorCode;
import com.library_management.mapper.UserMapper;
import com.library_management.repository.identity.RoleRepository;
import com.library_management.repository.identity.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserResponse createUser(UserCreationRequest request) {
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        user.setRoles(roles);
        try {
            user = userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        log.info("Service: Create User");
        return userMapper.toUserResponse(user);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        log.info("Service: Get personal info");
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserResponse> getUsers() {
        log.info("Service: In method getUsers");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUser(String id) {
        log.info("Service: In method get user by id");
        return userMapper.toUserResponse(
                userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    //@PostAuthorize("returnObject.username == authentication.name")
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        var roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));
        log.info("Service: Updating user");
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
        log.info("Service: Delete User");
    }

    public List<User> listAllForExcel() {
        log.info("Service: List all data for excel utilities");
        return userRepository.findAll();
    }
    public void saveFromExcel(MultipartFile file) {
        try {
            List<User> userList;
            userList = UserExcelImport.excelToDatabase(file.getInputStream());
            userRepository.saveAll(userList);
            log.info("Service: Save data");
        } catch (IOException ex) {
            throw new RuntimeException("Excel data is failed to store: " + ex.getMessage());
        }
    }
}

