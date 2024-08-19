package com.library_management.controller.system;

import com.library_management.dto.request.system.CategoryRequest;
import com.library_management.dto.request.system.CategoryUpdateRequest;
import com.library_management.dto.response.global.ApiResponse;
import com.library_management.dto.response.system.CategoryResponse;
import com.library_management.service.system.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Category Management")
@Slf4j
public class CategoryController {
    CategoryService categoryService;

    @Operation(
            summary = "Create new category"
    )
    @PostMapping
    ApiResponse<CategoryResponse> create(@RequestBody @Valid CategoryRequest request) {
        log.info("Controller: Creating Category..........");
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.create(request))
                .build();
    }

    @Operation(
            summary = "Get all categories in database"
    )
    @GetMapping
    ApiResponse<List<CategoryResponse>> getAll() {
        log.info("Controller: Getting All Category..........");
        return ApiResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getAll())
                .build();
    }

    @Operation(
            summary = "Get category information by ID"
    )
    @GetMapping("/{category}")
    ApiResponse<CategoryResponse> getById(@PathVariable("category") String category) {
        log.info("Controller: Getting Category By ID..........");
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.getCategory(category))
                .build();
    }

    @Operation(
            summary = "Update category"
    )
    @PutMapping("/{categoryId}")
    ApiResponse<CategoryResponse> update(@PathVariable String categoryId, @RequestBody CategoryUpdateRequest request) {
        log.info("Controller: Updating Category By ID..........");
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.updateCategory(categoryId, request))
                .build();
    }

    @Operation(
            summary = "Delete category"
    )
    @DeleteMapping("/{category}")
    ApiResponse<Void> delete(@PathVariable String category) {
        categoryService.delete(category);
        log.info("Controller: Deleting Category By ID..........");
        return ApiResponse.<Void>builder().build();
    }
}
