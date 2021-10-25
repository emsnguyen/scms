package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.Purchase;
import com.scms.supplychainmanagementsystem.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {

    @Query(value = "SELECT u FROM Stock u where u.stockId= :stockId  and u.product.warehouse.warehouseID= :warehouseid")
    Stock findByStockIdInWarehouse(@Param("stockId") Long stockId, @Param("warehouseid") Long warehouseId);

    @Query(value = "SELECT u FROM Stock u where u.stockId= :stockId ")
    Stock findByStockIdId(@Param("stockId") Long stockId);

    @Query(value = "SELECT u FROM Stock u where u.product.productId= :productid ")
    Stock findByProductId(@Param("productid") Long productid);


    @Modifying
    @Transactional
    @Query(value = "Delete  FROM Stock u where u.stockId=:stockId")
    void deleteStockAdmin(@Param("stockId") Long stockId);

    @Query(value = "select u from Stock u where u.product.productId=:productid and u.product.warehouse.warehouseID =:warehouseId ")
    Page<Stock> filterInOneWarehouse(@Param("productid") Long productid,@Param("warehouseId") Long warehouseId, Pageable pageable);

    @Query(value = "select u from Stock u where u.product.productId=:productid and (:warehouseId is null or u.product.warehouse.warehouseID = :warehouseId) ")
    Page<Stock> filterAllWarehouses(@Param("productid") Long productid,@Param("warehouseId") Long warehouseId, Pageable pageable);
}
