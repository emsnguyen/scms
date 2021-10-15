package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.Material;
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


    @Query(value = "SELECT * FROM test.purchase_history where purchaseid= :purchaseid  and warehouseid= :warehouseid",nativeQuery = true)
    PurchaseHistory findByPurchaseIdInWarehouse(@Param("purchaseid") Long purchaseid , @Param("warehouseid") Long warehouseId);

    @Query(value = "SELECT * FROM test.purchase_history where purchaseid= :purchaseid ",nativeQuery = true)
    PurchaseHistory findByPurchaseId(@Param("purchaseid") Long purchaseid );


    @Modifying
    @Transactional
    @Query(value = "Delete  FROM test.purchase_history where purchaseid= :purchaseid  and warehouseid= :warehouseid",nativeQuery = true)
    void deletePurchase(@Param("purchaseid") Long purchaseid , @Param("warehouseid") Long warehouseId);

    @Modifying
    @Transactional
    @Query(value = "Delete  FROM test.purchase_history where purchaseid= :purchaseid ",nativeQuery = true)
    void deletePurchaseAdmin(@Param("purchaseid") Long purchaseid );

    @Query(value = "select u from Material u where u.warehouse.warehouseID =:warehouseId " +
            " and (:materialname is null or u.MaterialName like %:materialname%) " +
            " order by u.createdDate desc")
    Page<PurchaseHistory> filterInOneWarehouse(@Param("materialname") String materialname,
                                        @Param("warehouseId") Long warehouseId, Pageable pageable);

    @Query(value = "select u from Material u where (:warehouseId is null or u.warehouse.warehouseID = :warehouseId) " +
            " and (:materialname is null or u.MaterialName like %:materialname%) " +
            " order by u.createdDate desc")
    Page<PurchaseHistory> filterAllWarehouses(@Param("materialname") String materialname,
                                       @Param("warehouseId") Long warehouseId, Pageable pageable);
}
