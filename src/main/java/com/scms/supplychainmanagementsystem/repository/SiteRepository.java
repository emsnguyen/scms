package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.PurchaseDetails;
import com.scms.supplychainmanagementsystem.entity.Site;
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
public interface SiteRepository extends JpaRepository<Site, Long> {

    @Query(value = "SELECT u FROM Site u where u.siteId= :siteId  and u.warehouse.warehouseID= :warehouseid")
    Site findBySiteId(@Param("siteId") Long siteId, @Param("warehouseid") Long warehouseid);

    @Modifying
    @Transactional
    @Query(value = "Delete  FROM Site u where u.siteId= :siteId and u.warehouse.warehouseID =:warehouseId  ")
    void deleteSite(@Param("siteId") Long siteId,@Param("warehouseId") Long warehouseId);

    @Modifying
    @Transactional
    @Query(value = "Delete  FROM Site u where u.siteId= :siteId ")
    void deleteSiteAdmin(@Param("siteId") Long siteId);

    @Query(value = "select u from Site u where u.warehouse.warehouseID=:warehouseId  ")
    Page<Site> filterInOneWarehouse(@Param("warehouseId") Long warehouseId, Pageable pageable);

    @Query(value = "select u from Site u where  (:warehouseId is null or u.warehouse.warehouseID = :warehouseId) ")
    Page<Site> filterAllWarehouses(@Param("warehouseId") Long warehouseId, Pageable pageable);
}

