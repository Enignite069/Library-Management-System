package com.library_management.mapper;

import com.library_management.dto.request.system.CategoryRequest;
import com.library_management.dto.request.system.CategoryUpdateRequest;
import com.library_management.dto.response.system.CategoryResponse;
import com.library_management.entity.system.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequest request);

    CategoryResponse toCategoryResponse(Category category);

    void updateCategory(@MappingTarget Category category, CategoryUpdateRequest request);
}
