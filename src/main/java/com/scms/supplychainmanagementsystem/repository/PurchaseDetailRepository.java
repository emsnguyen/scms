package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.Purchase;
import com.scms.supplychainmanagementsystem.entity.PurchaseDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface PurchaseDetailRepository extends JpaRepository<PurchaseDetails , Long> {
    @Query(value = "SELECT u FROM PurchaseDetails u where u.purchaseDetailID= :purchasedetailid  and u.purchase.warehouse= :warehouseid")
    Purchase findByPurchaseDetailId(@Param("purchasedetailid") Long purchaseDetailid, @Param("warehouseid") Long warehouseid);

    @Query(value = "SELECT u FROM PurchaseDetails u where u.purchaseDetailID= :purchasedetailid ")
    Purchase findByPurchaseDetailIdAdmin(@Param("purchasedetailid") Long purchasedetailid);


    @Modifying
    @Transactional
    @Query(value = "Delete  FROM PurchaseDetails u where u.purchaseDetailID= :purchasedetailid  and u.purchase.warehouse= :warehouseid")
    void deletePurchaseDetail(@Param("purchasedetailid") Long purchasedetailid, @Param("warehouseid") Long warehouseid);

    @Modifying
    @Transactional
    @Query(value = "Delete  FROM PurchaseDetails u where u.purchaseDetailID= :purchasedetailid ")
    void deletePurchaseAdmin(@Param("purchasedetailid") Long purchasedetailid);

    @Query(value = "select u from PurchaseDetails u where u.purchase.purchaseID=:purchaseid and u.purchase.warehouse.warehouseID=:warehouseid " +
            " order by u.purchase.createdDate desc")
    Page<Purchase> filterInOneWarehouse(@Param("purchaseid") Long purchaseid,@Param("warehouseid") Long warehouseid, Pageable pageable);

    @Query(value = "select u from PurchaseDetails u where  u.purchase.purchaseID=:purchaseid and (:warehouseId is null or u.purchase.warehouse.warehouseID = :warehouseId) " +
            " order by u.purchase.createdDate desc")
    Page<Purchase> filterAllWarehouses(@Param("purchaseid") Long purchaseid,@Param("warehouseId") Long warehouseId, Pageable pageable);
}
