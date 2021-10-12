package com.scms.supplychainmanagementsystem.controller;

import com.scms.supplychainmanagementsystem.dto.ProductDto;
import com.scms.supplychainmanagementsystem.entity.Product;
import com.scms.supplychainmanagementsystem.service.IProductService;
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
@RequestMapping("/api/product")
@AllArgsConstructor
@Slf4j
public class ProductController {
    private final IProductService iProductService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProducts(@RequestParam(required = false) String productName,
                                                              @RequestParam(required = false) Long categoryId,
                                                              @RequestParam(required = false) Long warehouseId,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        log.info("[Start ProductController - Get All Products]");
        List<Product> productList;
        Page<Product> productPage;
        Pageable pageable = PageRequest.of(page, size);
        productPage = iProductService.getAllProducts(productName, categoryId, warehouseId, pageable);

        productList = productPage.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("data", productList);
        response.put("currentPage", productPage.getNumber());
        response.put("totalItems", productPage.getTotalElements());
        response.put("totalPages", productPage.getTotalPages());
        if (!productPage.isEmpty()) {
            response.put("message", HttpStatus.OK);
        } else {
            response.put("message", "EMPTY_RESULT");
        }
        log.info("[End ProductController - Get All Products]");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access. Not required [productId]")
    public ResponseEntity<String> createProduct(@RequestBody ProductDto productDto) {
        log.info("[Start ProductController - createProduct " + productDto.getProductName() + "]");
        iProductService.createProduct(productDto);
        log.info("[End ProductController - createProduct " + productDto.getProductName() + "]");
        return new ResponseEntity<>("Product Created Successfully", CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable Long productId) {
        log.info("[Start ProductController - Get Product By Product ID = " + productId + "]");
        Map<String, Object> result = new HashMap<>();
        ProductDto productDto = iProductService.getProductById(productId);
        result.put("data", productDto);
        result.put("message", OK);
        log.info("[End ProductController - Get Product By Product ID = " + productId + "]");
        return status(HttpStatus.OK).body(result);
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access.")
    public ResponseEntity<String> updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductDto productDto) {
        log.info("[Start ProductController - Update Product By Product ID = " + productId + "]");
        productDto.setProductId(productId);
        iProductService.updateProduct(productDto);
        log.info("[End ProductController - Update Product By Product ID = " + productId + "]");
        return new ResponseEntity<>("Product Updated Successfully", OK);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    @ApiOperation(value = "Requires ADMIN or MANAGER Access")
    public ResponseEntity<String> deleteProductByProductId(@PathVariable Long productId) {
        log.info("[Start ProductController - Delete Product By Product ID = " + productId + "]");
        iProductService.deleteProductByProductId(productId);
        log.info("[End ProductController - Delete Product By Product ID = " + productId + "]");
        return new ResponseEntity<>("Product Deleted Successfully", OK);
    }

}
