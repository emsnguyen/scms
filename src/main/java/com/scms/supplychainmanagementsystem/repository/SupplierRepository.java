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

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    //    Optional<Material> findByMaterialName(String materialName);


    @Query(value = "SELECT * FROM test.supplier where supplier_id= :supplierid and warehouseid= :warehouseid", nativeQuery = true)
    Supplier findBySupplierIdAnhInWarehouse(@Param("supplierid") Long materialId, @Param("warehouseid") Long warehouseId);

    @Query(value = "SELECT * FROM test.supplier where supplier_id= :supplierid", nativeQuery = true)
    Supplier findBySupplierId(@Param("supplierid") Long materialId);


//    @Query(value = "SELECT * FROM test.customer where warehouseid= :warehouseid",nativeQuery = true)
//    Page<Customer> findByWarehouse(@Param("warehouseid") Long warehouseId, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "Delete  FROM test.supplier where supplier_id= :supplierid and warehouseid= :warehouseid", nativeQuery = true)
    void deleteSupplier(@Param("supplierid") Long supplierId, @Param("warehouseid") Long warehouseId);

    @Modifying
    @Transactional
    @Query(value = "Delete  FROM test.supplier where supplier_id= :supplierid ", nativeQuery = true)
    void deleteSupplierAdmin(@Param("supplierid") Long supplierId);

    @Query(value = "select supplier_name FROM test.supplier where supplier_name= :suppliername ", nativeQuery = true)
    boolean existsBySupplierName(@Param("suppliername") String suppliername);

    @Query(value = "select u from Supplier u where u.warehouse.warehouseID =:warehouseId " +
            " and (:suppliername is null or u.SupplierName like %:suppliername%) " +
            " order by u.supplierId desc")
    Page<Supplier> filterInOneWarehouse(@Param("suppliername") String suppliername,
                                        @Param("warehouseId") Long warehouseId, Pageable pageable);

    @Query(value = "select u from Supplier u where (:warehouseId is null or u.warehouse.warehouseID = :warehouseId) " +
            " and (:suppliername is null or u.SupplierName like %:suppliername%) " +
            " order by u.supplierId desc")
    Page<Supplier> filterAllWarehouses(@Param("suppliername") String suppliername,
                                       @Param("warehouseId") Long warehouseId, Pageable pageable);
}
