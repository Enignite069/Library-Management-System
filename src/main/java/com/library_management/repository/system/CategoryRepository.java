package com.library_management.repository.system;

import com.library_management.entity.system.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findCategoryById(String id);
}
