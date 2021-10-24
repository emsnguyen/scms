package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.PurchaseDetails;
import com.scms.supplychainmanagementsystem.entity.StockHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
@Repository
public interface StockHistoryRepository extends JpaRepository<StockHistory ,Long> {
    @Query(value = "SELECT u FROM StockHistory u where u.stockHistoryID= :stockHistoryId  and u.product.warehouse.warehouseID= :warehouseid")
    StockHistory findByStockHistoryId(@Param("stockHistoryId") Long stockHistoryId, @Param("warehouseid") Long warehouseid);

    @Query(value = "SELECT u FROM StockHistory u where u.stockHistoryID= :stockHistoryId ")
    StockHistory findByStockHistoryIdAdmin(@Param("stockHistoryId") Long stockHistoryId);


    @Modifying
    @Transactional
    @Query(value = "Delete  FROM StockHistory u where u.stockHistoryID= :stockHistoryId  and u.product.warehouse.warehouseID= :warehouseid")
    void deleteStockHistory(@Param("stockHistoryId") Long stockHistoryId, @Param("warehouseid") Long warehouseid);

    @Modifying
    @Transactional
    @Query(value = "Delete  FROM StockHistory u where u.stockHistoryID= :stockHistoryId ")
    void deleteStockHistoryAdmin(@Param("stockHistoryId") Long stockHistoryId);

    @Query(value = "select u from StockHistory u where u.product.productId=:productId and u.product.warehouse.warehouseID=:warehouseid " +
            " order by u.createdDate desc")
    Page<StockHistory> filterInOneWarehouse(@Param("productId") Long productId, @Param("warehouseid") Long warehouseid, Pageable pageable);

    @Query(value = "select u from StockHistory u where  u.product.productId=:productId and (:warehouseId is null or u.product.warehouse.warehouseID= :warehouseId) " +
            " order by u.createdDate desc")
    Page<StockHistory> filterAllWarehouses(@Param("productId") Long productId,@Param("warehouseId") Long warehouseId, Pageable pageable);
}

