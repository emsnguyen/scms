package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.CategoryDto;
import com.scms.supplychainmanagementsystem.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryService {
    void updateCategory(CategoryDto categoryDto);

    void createCategory(CategoryDto categoryDto);

    void deleteCategoryById(Long categoryId);

    CategoryDto getCategoryById(Long categoryId);

    Page<Category> getAllCategories(String categoryName, Long warehouseId, Pageable pageable);
}
