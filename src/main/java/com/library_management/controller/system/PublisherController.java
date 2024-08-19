package com.library_management.controller.system;

import com.library_management.dto.request.system.PublisherRequest;
import com.library_management.dto.request.system.PublisherUpdateRequest;
import com.library_management.dto.response.global.ApiResponse;
import com.library_management.dto.response.system.PublisherResponse;
import com.library_management.service.system.PublisherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Publisher Management")
public class PublisherController {
    PublisherService publisherService;

    @Operation(
            summary = "Create new publisher"
    )
    @PostMapping
    ApiResponse<PublisherResponse> create(@RequestBody @Valid PublisherRequest request) {
        log.info("Controller: Creating Publisher.........");
        return ApiResponse.<PublisherResponse>builder()
                .result(publisherService.create(request))
                .build();
    }

    @Operation(
            summary = "Get all publishers in database"
    )
    @GetMapping
    ApiResponse<List<PublisherResponse>> getAll() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        log.info("Controller: Getting All Publisher.........");
        return ApiResponse.<List<PublisherResponse>>builder()
                .result(publisherService.getAll())
                .build();
    }

    @Operation(
            summary = "Get publisher by ID"
    )
    @GetMapping("/{publisherId}")
    ApiResponse<PublisherResponse> getById(@PathVariable("publisherId") String publisherId) {
        log.info("Controller: Getting Publisher By Id.........");
        return ApiResponse.<PublisherResponse>builder()
                .result(publisherService.getPublisher(publisherId))
                .build();
    }

    @Operation(
            summary = "Update publisher"
    )
    @PutMapping("/{publisherId}")
    ApiResponse<PublisherResponse> update(@PathVariable String publisherId, @RequestBody PublisherUpdateRequest request) {
        log.info("Controller: Updating Publisher By Id.........");
        return ApiResponse.<PublisherResponse>builder()
                .result(publisherService.updatePublisher(publisherId, request))
                .build();
    }

    @Operation(
            summary = "Delete publisher"
    )
    @DeleteMapping("/{publisherId}")
    ApiResponse<String> delete(@PathVariable String publisherId) {
        publisherService.delete(publisherId);
        log.info("Controller: Deleting Publisher By Id.........");
        return ApiResponse.<String>builder().result("Publisher has been deleted").build();
    }
}
