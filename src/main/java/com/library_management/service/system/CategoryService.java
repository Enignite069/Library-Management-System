package com.library_management.service.system;

import com.library_management.dto.request.system.CategoryRequest;
import com.library_management.dto.request.system.CategoryUpdateRequest;
import com.library_management.dto.response.system.CategoryResponse;
import com.library_management.entity.system.Category;
import com.library_management.exception.AppException;
import com.library_management.exception.ErrorCode;
import com.library_management.mapper.CategoryMapper;
import com.library_management.repository.system.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse create(CategoryRequest request) {
        Category category = categoryMapper.toCategory(request);
        try {
            category = categoryRepository.save(category);
            log.info("Service: Create category");
        } catch (DataIntegrityViolationException exception) {
            throw new AppException(ErrorCode.INVALID_INPUT);
        }
        return categoryMapper.toCategoryResponse(category);
    }

    public List<CategoryResponse> getAll() {
        var category = categoryRepository.findAll();
        log.info("Service: Get all categories");
        return category.stream().map(categoryMapper::toCategoryResponse).toList();
    }

    public CategoryResponse getCategory(String id) {
        log.info("Service: In method get category by id");
        return categoryMapper.toCategoryResponse(
                categoryRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse updateCategory(String categoryId, CategoryUpdateRequest request) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        categoryMapper.updateCategory(category, request);
        log.info("Service: Update category");
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String category) {
        categoryRepository.deleteById(category);
        log.info("Service: Delete category");
    }
}
