package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query(value = "select p from Product p where p.warehouse.warehouseID =:warehouseId " +
            " and (:productName is null or p.productName like %:productName%) " +
            " and (:categoryId is null or p.category.categoryId = :categoryId) " +
            " and (:isActive is null or p.isActive = :isActive) " +
            " order by p.productId desc")
    Page<Product> filterInOneWarehouse(@Param("productName") String productName, @Param("categoryId") Long categoryId,
                                       @Param("warehouseId") Long warehouseId, @Param("isActive") Boolean isActive,
                                       Pageable pageable);

    @Query(value = "select p from Product p where (:warehouseId is null or p.warehouse.warehouseID = :warehouseId) " +
            " and (:productName is null or p.productName like %:productName%) " +
            " and (:categoryId is null or p.category.categoryId = :categoryId) " +
            " and (:isActive is null or p.isActive = :isActive) " +
            " order by p.productId desc")
    Page<Product> filterAllWarehouses(@Param("productName") String productName, @Param("categoryId") Long categoryId,
                                      @Param("warehouseId") Long warehouseId, @Param("isActive") Boolean isActive,
                                      Pageable pageable);
}
