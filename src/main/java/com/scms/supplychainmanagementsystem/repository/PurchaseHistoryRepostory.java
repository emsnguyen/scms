package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.PurchaseHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PurchaseHistoryRepostory extends JpaRepository<PurchaseHistory, Long> {


    @Query(value = "SELECT u FROM PurchaseHistory u where u.purchaseID= :purchaseid  and u.warehouse.warehouseID= :warehouseid")
    PurchaseHistory findByPurchaseIdInWarehouse(@Param("purchaseid") Long purchaseid, @Param("warehouseid") Long warehouseId);

    @Query(value = "SELECT u FROM PurchaseHistory u where u.purchaseID= :purchaseid ")
    PurchaseHistory findByPurchaseId(@Param("purchaseid") Long purchaseid);


    @Modifying
    @Transactional
    @Query(value = "Delete  FROM PurchaseHistory u where u.purchaseID= :purchaseid  and u.warehouse.warehouseID= :warehouseid")
    void deletePurchase(@Param("purchaseid") Long purchaseid, @Param("warehouseid") Long warehouseId);

    @Modifying
    @Transactional
    @Query(value = "Delete  FROM PurchaseHistory u where u.warehouse.warehouseID= :purchaseid ")
    void deletePurchaseAdmin(@Param("purchaseid") Long purchaseid);

    @Query(value = "select u from PurchaseHistory u where u.warehouse.warehouseID =:warehouseId " +
            " order by u.PurchaseDate desc")
    Page<PurchaseHistory> filterInOneWarehouse(@Param("warehouseId") Long warehouseId, Pageable pageable);

    @Query(value = "select u from PurchaseHistory u where (:warehouseId is null or u.warehouse.warehouseID = :warehouseId) " +
            " order by u.PurchaseDate desc")
    Page<PurchaseHistory> filterAllWarehouses(@Param("warehouseId") Long warehouseId, Pageable pageable);
}
