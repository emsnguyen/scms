package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.CategoryDto;
import com.scms.supplychainmanagementsystem.entity.Category;
import com.scms.supplychainmanagementsystem.entity.User;
import com.scms.supplychainmanagementsystem.entity.Warehouse;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.CategoryRepository;
import com.scms.supplychainmanagementsystem.repository.WarehouseRepository;
import com.scms.supplychainmanagementsystem.service.ICategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class CategoryService implements ICategoryService {
    private final UserCommon userCommon;
    private final CategoryRepository categoryRepository;
    private final WarehouseRepository warehouseRepository;

    @Override
    public void updateCategory(CategoryDto categoryDto) {
        log.info("[Start CategoryService - Update Category : " + categoryDto.getCategoryName() + "]");
        if (checkAccessCategory(categoryDto.getCategoryId())) {
            Category category = categoryRepository.findById(categoryDto.getCategoryId())
                    .orElseThrow(() -> new AppException("Category not found"));
            category.setCategoryName(categoryDto.getCategoryName());
            User current = userCommon.getCurrentUser();
//            if (current.getRole().getRoleID() != 1
//                    && !categoryDto.getWarehouseId().equals(current.getWarehouse().getWarehouseID())) {
//                throw new AppException("Not allow to choose warehouse");
//            }
            if (current.getRole().getRoleID() == 1) {
                category.setWarehouse(warehouseRepository.getById(categoryDto.getWarehouseId()));
            } else {
                category.setWarehouse(current.getWarehouse());
            }
            log.info("[Start Save Category " + categoryDto.getCategoryName() + " to database]");
            categoryRepository.saveAndFlush(category);
            log.info("[End Save Category " + categoryDto.getCategoryName() + " to database]");
            log.info("[End CategoryService - Update Category : " + categoryDto.getCategoryName() + "]");
        } else {
            throw new AppException("Not allow to access this resource");
        }
    }

    @Override
    public void createCategory(CategoryDto categoryDto) {
        log.info("[Start CategoryService - Create Category : " + categoryDto.getCategoryName() + "]");
        User current = userCommon.getCurrentUser();
        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName());
//        if (categoryDto.getWarehouseId() == null) {
//            throw new AppException("Not fill in all required fields");
//        }
//        if (current.getRole().getRoleID() != 1
//                && !categoryDto.getWarehouseId().equals(current.getWarehouse().getWarehouseID())) {
//            throw new AppException("Not allow to choose warehouse");
//        }
        if (current.getRole().getRoleID() == 1) {
            category.setWarehouse(warehouseRepository.findById(categoryDto.getWarehouseId())
                    .orElseThrow(() -> new AppException("Warehouse not found")));
        } else {
            category.setWarehouse(current.getWarehouse());
        }
        log.info("[Start Save Category " + categoryDto.getCategoryName() + " to database]");
        categoryRepository.saveAndFlush(category);
        log.info("[End Save Category " + categoryDto.getCategoryName() + " to database]");
        log.info("[End CategoryService - Create Category : " + categoryDto.getCategoryName() + "]");
    }

    @Override
    public void deleteCategoryById(Long categoryId) {
        log.info("[Start CategoryService - Delete Category ID = : " + categoryId + "]");
        if (checkAccessCategory(categoryId)) {
            categoryRepository.deleteById(categoryId);
        } else {
            throw new AppException("Not allow to delete this resource");
        }
        log.info("[End CategoryService - Delete Category ID = : " + categoryId + "]");
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        log.info("[Start CategoryService - Get Category ID = : " + categoryId + "]");
        CategoryDto categoryDto = new CategoryDto();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException("Category not found"));
        if (checkAccessCategory(categoryId)) {
            categoryDto.setCategoryId(categoryId);
            categoryDto.setCategoryName(category.getCategoryName());
            categoryDto.setWarehouseId(category.getWarehouse().getWarehouseID());
        } else {
            throw new AppException("Not allow to access this resource");
        }
        log.info("[End CategoryService - Get Category ID = : " + categoryId + "]");
        return categoryDto;
    }

    @Override
    public Page<Category> getAllCategories(String categoryName, Long warehouseId, Pageable pageable) {
        log.info("[Start CategoryService - Get All Categories]");
        Page<Category> categoryPage;
        User current = userCommon.getCurrentUser();
        Warehouse wh = current.getWarehouse();
        if (current.getRole().getRoleID() == 1) {
            categoryPage = categoryRepository.filterAllWarehouses(categoryName, warehouseId, pageable);
        } else {
            categoryPage = categoryRepository.filterInOneWarehouse(categoryName, wh.getWarehouseID(), pageable);
        }
        log.info("[End CategoryService - Get All Categories]");
        return categoryPage;
    }

    public boolean checkAccessCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new AppException("Category not found"));
        User current = userCommon.getCurrentUser();
        if (current.getRole().getRoleID() == 1) {
            return true;
        }
        if (category.getWarehouse() != null && current.getWarehouse() != null) {
            return category.getWarehouse().getWarehouseID().equals(current.getWarehouse().getWarehouseID());
        }
        return false;
    }
}
