package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.PurchaseDetails;
import com.scms.supplychainmanagementsystem.entity.Site;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface SiteRepository {

    @Query(value = "SELECT u FROM PurchaseDetails u where u.purchaseDetailID= :purchasedetailid  and u.purchase.warehouse= :warehouseid")
    PurchaseDetails findByPurchaseDetailId(@Param("purchasedetailid") Long purchaseDetailid, @Param("warehouseid") Long warehouseid);

    @Query(value = "SELECT u FROM PurchaseDetails u where u.purchaseDetailID= :purchasedetailid ")
    PurchaseDetails findByPurchaseDetailIdAdmin(@Param("purchasedetailid") Long purchasedetailid);


    @Modifying
    @Transactional
    @Query(value = "Delete  FROM PurchaseDetails u where u.purchaseDetailID= :purchasedetailid  and u.purchase.warehouse= :warehouseid")
    void deletePurchaseDetail(@Param("purchasedetailid") Long purchasedetailid, @Param("warehouseid") Long warehouseid);

    @Modifying
    @Transactional
    @Query(value = "Delete  FROM PurchaseDetails u where u.purchase.purchaseID= :purchaseid  ")
    void deletePurchaseDetailByPurchaseId(@Param("purchaseid") Long purchaseid);

    @Modifying
    @Transactional
    @Query(value = "Delete  FROM PurchaseDetails u where u.purchaseDetailID= :purchasedetailid ")
    void deletePurchaseAdmin(@Param("purchasedetailid") Long purchasedetailid);

    @Query(value = "select u from Site u where u.warehouse.warehouseID=:warehouseId  ")
    Page<Site> filterInOneWarehouse(@Param("warehouseId") Long warehouseId, Pageable pageable);

    @Query(value = "select u from Site u where  (:warehouseId is null or u.warehouse.warehouseID = :warehouseId) ")
    Page<Site> filterAllWarehouses(@Param("warehouseId") Long warehouseId, Pageable pageable);
}

