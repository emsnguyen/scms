package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase ,Long> {

    @Query(value = "SELECT u FROM Purchase u where u.purchaseID= :purchaseid  and u.warehouse.warehouseID= :warehouseid")
    Purchase findByPurchaseIdInWarehouse(@Param("purchaseid") Long purchaseid, @Param("warehouseid") Long warehouseId);

    @Query(value = "SELECT u FROM Purchase u where u.purchaseID= :purchaseid ")
    Purchase findByPurchaseId(@Param("purchaseid") Long purchaseid);


    @Modifying
    @Transactional
    @Query(value = "Delete  FROM Purchase u where u.purchaseID= :purchaseid  and u.warehouse.warehouseID= :warehouseid")
    void deletePurchase(@Param("purchaseid") Long purchaseid, @Param("warehouseid") Long warehouseId);

    @Modifying
    @Transactional
    @Query(value = "Delete  FROM Purchase u where u.warehouse.warehouseID= :purchaseid ")
    void deletePurchaseAdmin(@Param("purchaseid") Long purchaseid);

    @Query(value = "select u from Purchase u where u.warehouse.warehouseID =:warehouseId " +
            " order by u.createdDate desc")
    Page<Purchase> filterInOneWarehouse(@Param("warehouseId") Long warehouseId, Pageable pageable);

    @Query(value = "select u from Purchase u where (:warehouseId is null or u.warehouse.warehouseID = :warehouseId) " +
            " order by u.createdDate desc")
    Page<Purchase> filterAllWarehouses(@Param("warehouseId") Long warehouseId, Pageable pageable);

}
