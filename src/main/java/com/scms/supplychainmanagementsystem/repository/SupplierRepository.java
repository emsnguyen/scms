package com.scms.supplychainmanagementsystem.repository;

import com.scms.supplychainmanagementsystem.entity.Supplier;
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
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    //    Optional<Material> findByMaterialName(String materialName);
    @Query(value = "SELECT u FROM Supplier u where  u.warehouse.warehouseID= :warehouseid")
    List<Supplier> findAllByWarehouse(@Param("warehouseid") Long warehouseId);

    @Query(value = "SELECT u FROM Supplier u where u.supplierId= :supplierid and u.warehouse.warehouseID= :warehouseid")
    Supplier findBySupplierIdAnhInWarehouse(@Param("supplierid") Long materialId, @Param("warehouseid") Long warehouseId);

    @Query(value = "SELECT u FROM Supplier u where u.supplierId= :supplierid")
    Supplier findBySupplierId(@Param("supplierid") Long materialId);


//    @Query(value = "SELECT * FROM test.customer where warehouseid= :warehouseid",nativeQuery = true)
//    Page<Customer> findByWarehouse(@Param("warehouseid") Long warehouseId, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "Delete  FROM Supplier u where u.supplierId= :supplierid and u.warehouse.warehouseID= :warehouseid")
    void deleteSupplier(@Param("supplierid") Long supplierId, @Param("warehouseid") Long warehouseId);

    @Modifying
    @Transactional
    @Query(value = "Delete  FROM Supplier u where u.supplierId= :supplierid ")
    void deleteSupplierAdmin(@Param("supplierid") Long supplierId);

    @Query(value = "select u.SupplierName FROM Supplier u where u.SupplierName= :suppliername ")
    boolean existsBySupplierName(@Param("suppliername") String suppliername);

    @Query(value = "select u from Supplier u where u.warehouse.warehouseID =:warehouseId " +
            " and (:suppliername is null or u.SupplierName like %:suppliername%) " +
            " and (:active is null or u.isActive =:active) " +
            " order by u.supplierId desc")
    Page<Supplier> filterInOneWarehouse(@Param("suppliername") String suppliername,
                                        @Param("active") String isActive,
                                        @Param("warehouseId") Long warehouseId, Pageable pageable);

    @Query(value = "select u from Supplier u where (:warehouseId is null or u.warehouse.warehouseID = :warehouseId) " +
            " and (:suppliername is null or u.SupplierName like %:suppliername%) " +
            " and (:active is null or u.isActive =:active) " +
            " order by u.supplierId desc")
    Page<Supplier> filterAllWarehouses(@Param("suppliername") String suppliername,
                                       @Param("active") String isActive,
                                       @Param("warehouseId") Long warehouseId, Pageable pageable);
}
