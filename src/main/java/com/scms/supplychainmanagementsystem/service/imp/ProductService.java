package com.scms.supplychainmanagementsystem.service.imp;

import com.scms.supplychainmanagementsystem.common.UserCommon;
import com.scms.supplychainmanagementsystem.dto.ProductDto;
import com.scms.supplychainmanagementsystem.entity.Product;
import com.scms.supplychainmanagementsystem.entity.User;
import com.scms.supplychainmanagementsystem.entity.Warehouse;
import com.scms.supplychainmanagementsystem.exceptions.AppException;
import com.scms.supplychainmanagementsystem.repository.CategoryRepository;
import com.scms.supplychainmanagementsystem.repository.ProductRepository;
import com.scms.supplychainmanagementsystem.repository.WarehouseRepository;
import com.scms.supplychainmanagementsystem.service.IProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final UserCommon userCommon;
    private final WarehouseRepository warehouseRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void updateProduct(ProductDto productDto) {
        log.info("[Start ProductService - updateProduct " + productDto.getProductName() + "]");
        if (checkAccessProduct(productDto.getProductId())) {
            Product product = productRepository.findById(productDto.getProductId())
                    .orElseThrow(() -> new AppException("Product not found"));
            User current = userCommon.getCurrentUser();
//            if (current.getRole().getRoleID() != 1
//                    && !productDto.getWarehouseId().equals(current.getWarehouse().getWarehouseID())) {
//                throw new AppException("Not allow to choose warehouse");
//            }
            if (current.getRole().getRoleID() == 1) {
                product.setWarehouse(warehouseRepository.findById(productDto.getWarehouseId())
                        .orElseThrow(() -> new AppException("Warehouse not found")));
            }
            product.setProductName(productDto.getProductName());
            product.setCategory(categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new AppException("Category not found")));

            product.setIsActive(productDto.getIsActive());
            product.setQuantityUnitOfMeasure(productDto.getQuantityUnitOfMeasure());
            product.setLastModifiedBy(current);

            log.info("[Start Save Product " + productDto.getProductName() + " to database]");
            productRepository.saveAndFlush(product);
            log.info("[End Save Product " + productDto.getProductName() + " to database]");
            log.info("[End ProductService - updateProduct " + productDto.getProductName() + "]");
        } else {
            throw new AppException("Not allow to access this resource");
        }
    }

    @Override
    public void createProduct(ProductDto productDto) {
        log.info("[Start ProductService - createProduct " + productDto.getProductName() + "]");
        User current = userCommon.getCurrentUser();
        Product product = new Product();
        if (productDto.getCategoryId() == null) {
            throw new AppException("Not fill in all required fields");
        }
//        if (current.getRole().getRoleID() != 1
//                && !productDto.getWarehouseId().equals(current.getWarehouse().getWarehouseID())) {
//            throw new AppException("Not allow to choose warehouse");
//        }
        if (current.getRole().getRoleID() == 1) {
            product.setWarehouse(warehouseRepository.findById(productDto.getWarehouseId())
                    .orElseThrow(() -> new AppException("Warehouse not found")));
        } else {
            product.setWarehouse(current.getWarehouse());
        }
        product.setProductName(productDto.getProductName());
        product.setCategory(categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new AppException("Category not found")));
        product.setIsActive(productDto.getIsActive());
        product.setQuantityUnitOfMeasure(productDto.getQuantityUnitOfMeasure());
        product.setCreatedBy(current);
        log.info("[Start Save Product " + productDto.getProductName() + " to database]");
        productRepository.saveAndFlush(product);
        log.info("[End Save Product " + productDto.getProductName() + " to database]");
        log.info("[End ProductService - createProduct " + productDto.getProductName() + "]");
    }

    @Override
    public void deleteProductByProductId(Long productId) {
        log.info("[Start ProductService - deleteProductByProductId = " + productId + "]");
        if (checkAccessProduct(productId)) {
            productRepository.deleteById(productId);
        } else {
            throw new AppException("Not allow to delete this resource");
        }
        log.info("[End ProductService - deleteProductByProductId = " + productId + "]");

    }

    @Override
    public ProductDto getProductById(Long productId) {
        log.info("[Start ProductService - getProductById = " + productId + "]");
        ProductDto productDto = new ProductDto();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException("Product not found"));
        if (checkAccessProduct(productId)) {
            productDto.setProductId(productId);
            productDto.setProductName(product.getProductName());
            productDto.setIsActive(product.getIsActive());
            productDto.setCategoryId(product.getCategory().getCategoryId());
            productDto.setQuantityUnitOfMeasure(product.getQuantityUnitOfMeasure());
            productDto.setWarehouseId(product.getWarehouse().getWarehouseID());
            productDto.setCreatedBy(product.getCreatedBy() != null ? product.getCreatedBy().getUsername() : null);
            productDto.setLastModifiedBy(product.getLastModifiedBy() != null ? product.getLastModifiedBy().getUsername() : null);
        } else {
            throw new AppException("Not allow to access this resource");
        }
        log.info("[End ProductService - getProductById = " + productId + "]");
        return productDto;
    }

    @Override
    public Page<Product> getAllProducts(String productName, Long categoryId, Long warehouseId, Pageable pageable) {
        log.info("[Start ProductService - Get All Products]");
        Page<Product> productPage;
        User current = userCommon.getCurrentUser();
        Warehouse wh = current.getWarehouse();
        if (current.getRole().getRoleID() == 1) {
            productPage = productRepository.filterAllWarehouses(productName, categoryId, warehouseId, pageable);
        } else {
            productPage = productRepository.filterInOneWarehouse(productName, categoryId, wh.getWarehouseID(), pageable);
        }
        log.info("[End ProductService - Get All Products]");
        return productPage;
    }

    @Override
    public boolean checkProductExist(Long productId) {
        return productRepository.existsById(productId);
    }

    @Override
    public void updateProductActive(Long productId, Boolean isActive) {
        log.info("[Start ProductService - Update Product Active " + productId + "]");
        if (!checkAccessProduct(productId)) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Not allow update user activation");
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException("Product not found"));
        product.setIsActive(isActive);
        productRepository.saveAndFlush(product);
        log.info("[End ProductService - Update Product Active " + productId + "]");
    }

    public boolean checkAccessProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException("Product not found"));
        User current = userCommon.getCurrentUser();
        if (current.getRole().getRoleID() == 1) {
            return true;
        }
        if (product.getWarehouse() != null && current.getWarehouse() != null) {
            return product.getWarehouse().getWarehouseID().equals(current.getWarehouse().getWarehouseID());
        }
        return false;
    }
}
