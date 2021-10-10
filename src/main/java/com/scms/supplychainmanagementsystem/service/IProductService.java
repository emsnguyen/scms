package com.scms.supplychainmanagementsystem.service;

import com.scms.supplychainmanagementsystem.dto.ProductDto;
import com.scms.supplychainmanagementsystem.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    void updateProduct(ProductDto productDto);

    void createProduct(ProductDto productDto);

    void deleteProductByProductId(Long productId);

    ProductDto getProductById(Long productId);

    Page<Product> getAllProducts(String productName, Long categoryId, Long warehouseId, Pageable pageable);

}
