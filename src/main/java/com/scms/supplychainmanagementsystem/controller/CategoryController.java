package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.CategoryDto;
import com.scms.supplychainmanagementsystem.entity.Category;
import com.scms.supplychainmanagementsystem.service.ICategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
@Slf4j
public class CategoryController {
    private final ICategoryService iCategoryService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> createCategory(@RequestBody CategoryDto categoryDto) {
        log.info("[Start CategoryController -  createCategory " + categoryDto.getCategoryName() + "]");
        iCategoryService.createCategory(categoryDto);
        log.info("[End CategoryController -  createCategory " + categoryDto.getCategoryName() + "]");
        return new ResponseEntity<>("User Account Created Successfully", CREATED);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllCategories(@RequestParam(required = false) String categoryName,
                                                                @RequestParam(required = false) Long warehouseId,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        log.info("[Start CategoryController -  getAllCategories]");
        List<Category> categoryList;
        Page<Category> categoryPage;
        Pageable pageable = PageRequest.of(page, size);
        categoryPage = iCategoryService.getAllCategories(categoryName, warehouseId, pageable);

        categoryList = categoryPage.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("data", categoryList);
        response.put("currentPage", categoryPage.getNumber());
        response.put("totalItems", categoryPage.getTotalElements());
        response.put("totalPages", categoryPage.getTotalPages());
        if (!categoryPage.isEmpty()) {
            response.put("message", HttpStatus.OK);
        } else {
            response.put("message", "EMPTY_RESULT");
        }
        log.info("[End CategoryController -  getAllCategories]");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/{categoryId}")
    public ResponseEntity<Map<String, Object>> getCategoryById(@PathVariable Long categoryId) {
        log.info("[Start CategoryController -  getCategoryById]");
        Map<String, Object> result = new HashMap<>();
        CategoryDto categoryDto = iCategoryService.getCategoryById(categoryId);
        result.put("data", categoryDto);
        result.put("message", OK);
        log.info("[End CategoryController -  getCategoryById]");
        return status(HttpStatus.OK).body(result);
    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access. Disable field [userId, username,createdBy, createdDate,lastModifiedBy,lastModifiedDate]")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryId, @Valid @RequestBody CategoryDto categoryDto) {
        log.info("[Start CategoryController - updateCategory " + categoryDto.getCategoryName() + "]");
        categoryDto.setCategoryId(categoryId);
        iCategoryService.updateCategory(categoryDto);
        log.info("[End CategoryController - updateCategory " + categoryDto.getCategoryName() + "]");
        return new ResponseEntity<>("Category Updated Successfully", OK);
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Long categoryId) {
        log.info("[Start CategoryController - deleteCategoryById = " + categoryId + "]");
        iCategoryService.deleteCategoryById(categoryId);
        log.info("[End CategoryController - deleteCategoryById " + categoryId + "]");
        return new ResponseEntity<>("Category Deleted Successfully", OK);
    }

}
