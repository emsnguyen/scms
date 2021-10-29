package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.Inventory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    //    Optional<Material> findByMaterialName(String materialName);


    @Query(value = "SELECT u FROM Inventory  u where u.warehouse.warehouseID = :warehouseid")
    List<Inventory> findAllByWarehouse(@Param("warehouseid") Long warehouseId);

    @Query(value = "SELECT u FROM Inventory  u where u.inventoryId = :inventoryId and u.warehouse.warehouseID= :warehouseid")
    Inventory findByinventoryIdAnhInWarehouse(@Param("inventoryId") Long inventoryId, @Param("warehouseid") Long warehouseId);


//    @Query(value = "SELECT * FROM test.customer where warehouseid= :warehouseid",nativeQuery = true)
//    Page<Customer> findByWarehouse(@Param("warehouseid") Long warehouseId, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "Delete  FROM Inventory u where u.inventoryId= :inventoryId and u.warehouse.warehouseID= :warehouseid")
    void deleteinventory(@Param("inventoryId") Long inventoryId, @Param("warehouseid") Long warehouseId);

    @Modifying
    @Transactional
    @Query(value = "Delete  FROM Inventory u where u.inventoryId= :inventoryId ")
    void deleteinventoryAdmin(@Param("inventoryId") Long inventoryId);


//    @Query(value = "select material_name FROM test.material where material_name= :materialName ",nativeQuery = true)
//    boolean existsByMaterialName(@Param("materialName")String materialName);

    @Query(value = "select u from Inventory u where u.warehouse.warehouseID =:warehouseId " +
            " and (:productName is null or u.product.productName like %:productName%) " +
            " order by u.createdDate desc")
    Page<Inventory> filterInOneWarehouse(@Param("warehouseId") Long warehouseId,
                                         @Param("productName") String productName,Pageable pageable);

    @Query(value = "select u from Inventory u where (:warehouseId is null or u.warehouse.warehouseID = :warehouseId) " +
            " and (:productName is null or u.product.productName like %:productName%) " +
            " order by u.createdDate desc")
    Page<Inventory> filterAllWarehouses(@Param("warehouseId") Long warehouseId,
                                        @Param("productName") String productName,Pageable pageable);
}
